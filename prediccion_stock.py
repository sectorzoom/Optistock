import requests
import pandas as pd
from datetime import datetime, timedelta
from sklearn.linear_model import LinearRegression

# --- CONFIGURACIÓN ---
base_url = "http://localhost:8080"
resp = requests.get(f"{base_url}/api/productos")

if resp.status_code != 200:
    print("❌ No se pudo obtener productos:", resp.status_code)
    print(resp.text)  # Esto muestra si hay un error HTML
    exit()

productos = resp.json()


for producto in productos:
    producto_id = producto['id']
    print(f"\n🔍 Procesando producto {producto_id} - {producto['nombre']}")

    try:
        url_salidas = f"{base_url}/api/movimientos/producto/{producto_id}/salidas"
        response = requests.get(url_salidas)
        data = response.json()

        if not data:
            print("ℹ️ No hay salidas registradas. Se omite.")
            continue

        # --- Procesamiento de datos ---
        df = pd.DataFrame(data)
        df['fecha'] = pd.to_datetime(df['fecha'])
        df = df.groupby('fecha')['cantidad'].sum().reset_index()
        df['dias'] = (df['fecha'] - df['fecha'].min()).dt.days
        df['acumulado'] = df['cantidad'].cumsum()

        # --- Entrenamiento de modelo ---
        X = df[['dias']]
        y = df['acumulado']
        modelo = LinearRegression().fit(X, y)

        stock_actual = producto['stockActual']
        stock_minimo = producto['stockMinimo']
        consumo = df['acumulado'].iloc[-1] / df['dias'].iloc[-1] if df['dias'].iloc[-1] > 0 else 0.1

        dias_hasta_rotura = (stock_actual - stock_minimo) / consumo
        fecha_recomendada = datetime.now() + timedelta(days=dias_hasta_rotura)

        # --- Crear predicción ---
        prediccion = {
            "producto": { "id": producto_id },
            "fechaRecomendada": fecha_recomendada.strftime("%Y-%m-%d"),
            "consumoDiarioEstimado": round(consumo, 2),
            "fechaGeneracion": datetime.now().strftime("%Y-%m-%d")
        }

        # --- Enviar al backend ---
        guardar_url = f"{base_url}/api/predicciones"
        respuesta = requests.post(guardar_url, json=prediccion)

        if respuesta.status_code == 200:
            print(f"✅ Predicción guardada: {prediccion['fechaRecomendada']} (consumo: {consumo:.2f})")
        else:
            print("❌ Error al guardar predicción:", respuesta.text)

    except Exception as e:
        print(f"❌ Error procesando producto {producto_id}: {e}")

import os
import sys
import requests
import pandas as pd
import json
from datetime import datetime, timedelta
from sklearn.linear_model import LinearRegression

# === Parámetros desde Java ===
producto_id = int(sys.argv[1])

# Siempre localhost:8080, incluso en "produccion"
BASE_URL = os.environ.get("BASE_URL", "http://localhost:8080")

headers = {}

# === Obtener movimientos de salida ===
movimientos_url = f"{BASE_URL}/api/movimientos/producto/{producto_id}/salidas"
resp = requests.get(movimientos_url, headers=headers)

if resp.status_code != 200:
    print(json.dumps({"error": f"Error obteniendo movimientos: {resp.status_code}"}))
    sys.exit(1)

data = resp.json()
if not data:
    print(json.dumps({"error": "No hay salidas registradas para este producto"}))
    sys.exit(0)

df = pd.DataFrame(data)
df['fecha'] = pd.to_datetime(df['fecha'])
df = df.groupby('fecha')['cantidad'].sum().reset_index()
df['dias'] = (df['fecha'] - df['fecha'].min()).dt.days
df['acumulado'] = df['cantidad'].cumsum()

# === Modelo regresión lineal ===
X = df[['dias']]
y = df['acumulado']
modelo = LinearRegression().fit(X, y)

# === Obtener stock actual y mínimo del producto ===
producto_url = f"{BASE_URL}/api/productos/{producto_id}"
producto = requests.get(producto_url, headers=headers).json()

stock_actual = producto['stockActual']
stock_minimo = producto['stockMinimo']
consumo_diario = df['acumulado'].iloc[-1] / df['dias'].iloc[-1] if df['dias'].iloc[-1] > 0 else 0.1

dias_hasta_minimo = (stock_actual - stock_minimo) / consumo_diario
fecha_recomendada = datetime.now() + timedelta(days=dias_hasta_minimo)

# === Generar JSON de respuesta
respuesta = {
    "producto": f"{producto['nombre']} (ID {producto_id})",
    "stock_actual": stock_actual,
    "stock_minimo": stock_minimo,
    "consumo_diario_estimado": round(consumo_diario, 2),
    "fecha_recomendada": fecha_recomendada.strftime("%Y-%m-%d"),
    "dias_estimados_para_agotar_stock": int(round(dias_hasta_minimo)),
    "recomendacion": (
        f"Recomendado hacer pedido en los próximos {int(round(dias_hasta_minimo))} días"
        if dias_hasta_minimo < 7 else "Stock suficiente por ahora"
    )
}

print(json.dumps(respuesta))

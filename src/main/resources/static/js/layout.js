
function cargarLayout() {
    const navbar = `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container-fluid">
        <a class="navbar-brand" href="index.html">Optistock</a>
        <div class="d-flex ms-auto align-items-center">
          <span id="usuario-nombre" class="text-white me-3"></span>
          <button onclick="logout()" class="btn btn-outline-light btn-sm">Cerrar sesión</button>
        </div>
      </div>
    </nav>
  `;

    const footer = `
    <footer class="bg-dark text-white text-center py-3 mt-5">
      <div>Optistock – Control inteligente de stock</div>
      <div>David – 2º DAW – ${new Date().getFullYear()}</div>
    </footer>
  `;

    document.body.insertAdjacentHTML('afterbegin', navbar);
    document.body.insertAdjacentHTML('beforeend', footer);

    const usuario = JSON.parse(localStorage.getItem("usuario"));
    if (!usuario) {
        window.location.href = "login.html";
    } else {
        document.getElementById("usuario-nombre").textContent = "👋 Hola, " + usuario.nombre;
    }
}

function logout() {
    localStorage.removeItem("usuario");
    window.location.href = "login.html";
}

window.addEventListener('DOMContentLoaded', cargarLayout);
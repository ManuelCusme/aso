package Servicios;

import Dao.TiendaDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/TiendaServlet")
public class TiendaServlet extends HttpServlet {
    private final TiendaDAO dao = new TiendaDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "categorias";

        Object res = null;
        try {
            switch (accion) {
                case "categorias": // A1. Listar categorías con sus productos
                    res = dao.listarCategoriasConProductos();
                    break;
                case "productosPorCategoria": // A3. Productos por nombre de categoría
                    res = dao.listarProductosPorNombreCategoria(request.getParameter("nombreCat"));
                    break;
                case "buscarProducto": // B2. Buscar producto por su nombre
                    res = dao.buscarProductoPorNombre(request.getParameter("nombre"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        responder(response, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        boolean ok = false;

        try {
            switch (accion) {
                case "crearCategoria": // A2. Crear categoría
                    ok = dao.crearCategoria(request.getParameter("nombre"));
                    break;
                case "crearProducto": // B1. Crear producto con categoría validada
                    String nombre = request.getParameter("nombre");
                    double precio = Double.parseDouble(request.getParameter("precio"));
                    int idCat = Integer.parseInt(request.getParameter("idCategoria"));
                    ok = dao.crearProducto(nombre, precio, idCat);
                    break;
                case "eliminarProducto": // B3. Eliminar producto
                    int id = Integer.parseInt(request.getParameter("id"));
                    ok = dao.eliminarProducto(id);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        responder(response, Collections.singletonMap("status", ok ? "ok" : "error"));
    }

    private void responder(HttpServletResponse response, Object datos) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(datos));
    }
}
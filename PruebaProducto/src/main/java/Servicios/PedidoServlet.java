package Servicios;

import Dao.PedidoDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/PedidoServlet")
public class PedidoServlet extends HttpServlet {
    private final PedidoDAO dao = new PedidoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listarClientes";
        
        Object resultado = null;
        try {
            switch (accion) {
                case "listarClientes":
                    resultado = dao.listarClientes();
                    break;
                case "buscarCliente":
                    resultado = dao.buscarCliente(request.getParameter("cedula"));
                    break;
                case "listarProductos":
                    resultado = dao.listarProductos();
                    break;
                case "listarPedidos":
                    resultado = dao.listarPedidosPorCliente(request.getParameter("cedula"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        responder(response, resultado);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String cedula = request.getParameter("cedula");
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        
        boolean ok = false;
        try {
            ok = dao.registrarPedido(cedula, idProducto);
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
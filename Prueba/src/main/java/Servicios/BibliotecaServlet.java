package Servicios;

import Dao.BibliotecaDAO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/BibliotecaServlet")
public class BibliotecaServlet extends HttpServlet {
    private final BibliotecaDAO dao = new BibliotecaDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listarAutores";

        Object res = null;
        try {
            switch (accion) {
                case "listarAutores":
                    res = dao.listarAutores();
                    break;
                case "librosPorAutor":
                    res = dao.listarLibrosPorAutorNombre(request.getParameter("nombreAutor"));
                    break;
                case "listarLibros":
                    res = dao.listarLibros();
                    break;
                case "buscarLibro":
                    res = dao.buscarLibroPorTitulo(request.getParameter("titulo"));
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
                case "crearAutor":
                    ok = dao.crearAutor(request.getParameter("nombre"), request.getParameter("correo"));
                    break;
                case "actualizarAutor":
                    int idAut = Integer.parseInt(request.getParameter("id"));
                    ok = dao.actualizarAutor(idAut, request.getParameter("nombre"), request.getParameter("correo"));
                    break;
                case "crearLibro":
                    String tit = request.getParameter("titulo");
                    int anio = Integer.parseInt(request.getParameter("anio"));
                    int autorId = Integer.parseInt(request.getParameter("autorId"));
                    ok = dao.crearLibro(tit, anio, autorId);
                    break;
                case "actualizarLibro":
                    int idLib = Integer.parseInt(request.getParameter("id"));
                    String titAct = request.getParameter("titulo");
                    int anioAct = Integer.parseInt(request.getParameter("anio"));
                    int autorIdAct = Integer.parseInt(request.getParameter("autorId"));
                    ok = dao.actualizarLibro(idLib, titAct, anioAct, autorIdAct);
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
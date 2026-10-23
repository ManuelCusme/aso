package Dao;

import Conexion.conexion;
import Servicios.Autor;
import Servicios.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDAO {
    
    // GET /api/autores -> Listar todos los autores
    public List<Autor> listarAutores() throws Exception {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Autor(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo")));
            }
        }
        return lista;
    }

    // POST /api/autores -> Crear un nuevo autor
    public boolean crearAutor(String nombre, String correo) throws Exception {
        String sql = "INSERT INTO autores (nombre, correo) VALUES (?, ?)";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            return ps.executeUpdate() > 0;
        }
    }

    // PUT /api/autores/{id} -> Actualizar un autor existente
    public boolean actualizarAutor(int id, String nombre, String correo) throws Exception {
        String sql = "UPDATE autores SET nombre = ?, correo = ? WHERE id = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }

    // GET /api/Autores/{nombre} -> Muestra los libros de dicho autor
    public List<Libro> listarLibrosPorAutorNombre(String nombreAutor) throws Exception {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.*, a.nombre AS autorNombre FROM libros l JOIN autores a ON l.autor_id = a.id WHERE a.nombre = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreAutor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro l = new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getInt("anio_publicacion"), rs.getInt("autor_id"));
                    l.setNombreAutor(rs.getString("autorNombre"));
                    lista.add(l);
                }
            }
        }
        return lista;
    }

    // GET /api/libros -> Listar todos los libros
    public List<Libro> listarLibros() throws Exception {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.*, a.nombre AS autorNombre FROM libros l JOIN autores a ON l.autor_id = a.id";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Libro l = new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getInt("anio_publicacion"), rs.getInt("autor_id"));
                l.setNombreAutor(rs.getString("autorNombre"));
                lista.add(l);
            }
        }
        return lista;
    }

    // GET /api/libros/{titulo} -> Mostrar un libro especifico
    public Libro buscarLibroPorTitulo(String titulo) throws Exception {
        String sql = "SELECT l.*, a.nombre AS autorNombre FROM libros l JOIN autores a ON l.autor_id = a.id WHERE l.titulo = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Libro l = new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getInt("anio_publicacion"), rs.getInt("autor_id"));
                    l.setNombreAutor(rs.getString("autorNombre"));
                    return l;
                }
            }
        }
        return null;
    }

    // POST /api/libros -> Crear libro asociado a autor
    public boolean crearLibro(String titulo, int anio, int autorId) throws Exception {
        String sql = "INSERT INTO libros (titulo, anio_publicacion, autor_id) VALUES (?, ?, ?)";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setInt(2, anio);
            ps.setInt(3, autorId);
            return ps.executeUpdate() > 0;
        }
    }

    // PUT /api/libros/{id} -> Actualizar libro existente
    public boolean actualizarLibro(int id, String titulo, int anio, int autorId) throws Exception {
        String sql = "UPDATE libros SET titulo = ?, anio_publicacion = ?, autor_id = ? WHERE id = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setInt(2, anio);
            ps.setInt(3, autorId);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        }
    }
}
package Dao;

import Conexion.conexion;
import Servicios.Categoria;
import Servicios.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TiendaDAO {

    // A1. Listar categorías con sus respectivos productos
    public List<Categoria> listarCategoriasConProductos() throws Exception {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                c.setProductos(listarProductosPorCategoriaId(c.getId()));
                categorias.add(c);
            }
        }
        return categorias;
    }

    // A2. Crear categoría
    public boolean crearCategoria(String nombre) throws Exception {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        }
    }

    // A3. Productos por nombre de categoría
    public List<Producto> listarProductosPorNombreCategoria(String nombreCat) throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM productos p JOIN categorias c ON p.id_categoria = c.id WHERE c.nombre = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreCat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("id_categoria")));
                }
            }
        }
        return lista;
    }

    // B1. Crear producto (Validando que la categoría exista)
    public boolean crearProducto(String nombre, double precio, int idCategoria) throws Exception {
        if (!existeCategoria(idCategoria)) return false; // Validación de categoría previa
        
        String sql = "INSERT INTO productos (nombre, precio, id_categoria) VALUES (?, ?, ?)";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, idCategoria);
            return ps.executeUpdate() > 0;
        }
    }

    // B2. Buscar producto por nombre
    public Producto buscarProductoPorNombre(String nombre) throws Exception {
        String sql = "SELECT p.*, c.nombre AS catNombre FROM productos p JOIN categorias c ON p.id_categoria = c.id WHERE p.nombre = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("id_categoria"));
                    p.setNombreCategoria(rs.getString("catNombre"));
                    return p;
                }
            }
        }
        return null;
    }

    // B3. Eliminar producto por ID
    public boolean eliminarProducto(int id) throws Exception {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Métodos auxiliares simples
    private boolean existeCategoria(int id) throws Exception {
        String sql = "SELECT id FROM categorias WHERE id = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private List<Producto> listarProductosPorCategoriaId(int idCat) throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE id_categoria = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("id_categoria")));
                }
            }
        }
        return lista;
    }
}
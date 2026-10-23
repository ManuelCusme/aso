package Dao;

import Conexion.conexion;
import Servicios.Cliente;
import Servicios.Pedido;
import Servicios.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<Cliente> listarClientes() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(rs.getString("cedula"), rs.getString("nombre"), rs.getString("apellido")));
            }
        }
        return lista;
    }

    public Cliente buscarCliente(String cedula) throws Exception {
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(rs.getString("cedula"), rs.getString("nombre"), rs.getString("apellido"));
                }
            }
        }
        return null;
    }

    public List<Producto> listarProductos() throws Exception {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }

    public boolean registrarPedido(String cedula, int idProducto) throws Exception {
        String sql = "INSERT INTO pedidos (cedula_cliente, id_producto) VALUES (?, ?)";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Pedido> listarPedidosPorCliente(String cedula) throws Exception {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.id, c.cedula, CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
                     "pr.nombre AS producto, p.fecha " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.cedula_cliente = c.cedula " +
                     "JOIN productos pr ON p.id_producto = pr.id " +
                     "WHERE c.cedula = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Pedido(
                        rs.getInt("id"),
                        rs.getString("cedula"),
                        rs.getString("cliente"),
                        rs.getString("producto"),
                        rs.getString("fecha")
                    ));
                }
            }
        }
        return lista;
    }
}
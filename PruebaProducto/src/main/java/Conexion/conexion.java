/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;

/**
 *
 * @author Personal
 */

public class conexion {
    private final static String URL="jdbc:mysql://localhost:3307/empresa_pedidos";
    private final static String USUARIO="root";
    private final static String PASSWOR="";
    public static Connection getConexion(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USUARIO,PASSWOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
/*
CREATE DATABASE IF NOT EXISTS empresa_pedidos;
USE empresa_pedidos;

CREATE TABLE IF NOT EXISTS clientes (
    cedula VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula_cliente VARCHAR(10),
    id_producto INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cedula_cliente) REFERENCES clientes(cedula),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

-- Datos iniciales quemados obligatorios
INSERT INTO productos (nombre) VALUES ('Jabón'), ('Shampo'), ('Pasta Dental');
INSERT INTO clientes (cedula, nombre, apellido) VALUES ('1351598857', 'Manuel', 'Cusme');
*/
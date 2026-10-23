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
    private static final String URL="jdbc:mysql://localhost:3307/biblioteca_db";
    private static final String USUARIO="root";
    private static final String PASSWORD="";
    public static Connection getConexion(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
/*
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

CREATE TABLE IF NOT EXISTS autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    anio_publicacion INT NOT NULL,
    autor_id INT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES autores(id) ON DELETE CASCADE
);

-- Datos iniciales de prueba obligatorios
INSERT INTO autores (nombre, correo) VALUES 
('Gabriel', 'gabriel@gmail.com'),
('Luis', 'luis@gmail.com');

INSERT INTO libros (titulo, anio_publicacion, autor_id) VALUES 
('Guerreros', 1900, 1),
('Ciudad', 2000, 2);
*/

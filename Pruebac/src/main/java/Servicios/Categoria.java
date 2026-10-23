package Servicios;

import java.util.List;

public class Categoria {
    private int id;
    private String nombre;
    private List<Producto> productos; // Para el GET /categorias que incluye lista de productos

    public Categoria() {}
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
}
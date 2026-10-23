package Servicios;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int idCategoria;
    private String nombreCategoria;

    public Producto() {}
    public Producto(int id, String nombre, double precio, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getIdCategoria() { return idCategoria; }
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
}
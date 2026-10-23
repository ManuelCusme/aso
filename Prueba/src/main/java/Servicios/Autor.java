package Servicios;

public class Autor {
    private int id;
    private String nombre;
    private String correo;

    public Autor() {}
    public Autor(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
}
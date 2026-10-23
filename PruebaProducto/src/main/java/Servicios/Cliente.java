package Servicios;

public class Cliente {
    private String cedula;
    private String nombre;
    private String apellido;

    public Cliente() {}
    public Cliente(String cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
}
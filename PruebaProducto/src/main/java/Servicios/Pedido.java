package Servicios;

public class Pedido {
    private int id;
    private String cedulaCliente;
    private String nombreCliente;
    private String nombreProducto;
    private String fecha;

    public Pedido() {}
    public Pedido(int id, String cedulaCliente, String nombreCliente, String nombreProducto, String fecha) {
        this.id = id;
        this.cedulaCliente = cedulaCliente;
        this.nombreCliente = nombreCliente;
        this.nombreProducto = nombreProducto;
        this.fecha = fecha;
    }
}
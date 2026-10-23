package Servicios;

public class Libro {
    private int id;
    private String titulo;
    private int anioPublicacion;
    private int autorId;
    private String nombreAutor;
    public Libro() {}
    public Libro(int id, String titulo, int anioPublicacion, int autorId) {
        this.id = id;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.autorId = autorId;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public int getAutorId() { return autorId; }
    public String getNombreAutor() { return nombreAutor; }
    public void setNombreAutor(String nombreAutor) { this.nombreAutor = nombreAutor; }
}
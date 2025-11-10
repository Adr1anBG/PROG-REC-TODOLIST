import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una tarea en el sistema de gestión de tareas.
 * Implementa Serializable para facilitar el almacenamiento.
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private LocalDate fecha;
    
    /**
     * Enumeración que define los niveles de prioridad de una tarea
     */
    public enum Prioridad {
        BAJA("Baja", "\u001B[32m"),      // Verde
        MEDIA("Media", "\u001B[33m"),     // Amarillo
        ALTA("Alta", "\u001B[31m");       // Rojo
        
        private final String nombre;
        private final String codigoColor;
        
        Prioridad(String nombre, String codigoColor) {
            this.nombre = nombre;
            this.codigoColor = codigoColor;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public String getCodigoColor() {
            return codigoColor;
        }
    }
    
    /**
     * Constructor completo de la tarea
     */
    public Task(String titulo, String descripcion, Prioridad prioridad, LocalDate fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fecha = fecha != null ? fecha : LocalDate.now();
    }
    
    /**
     * Constructor con fecha por defecto (hoy)
     */
    public Task(String titulo, String descripcion, Prioridad prioridad) {
        this(titulo, descripcion, prioridad, LocalDate.now());
    }
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Prioridad getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Convierte la tarea a formato de texto para almacenamiento
     */
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return titulo + "|" + descripcion + "|" + prioridad.name() + "|" + fecha.format(formatter);
    }
    
    /**
     * Crea una tarea desde una línea de texto del archivo
     */
    public static Task fromFileFormat(String linea) {
        String[] partes = linea.split("\\|");
        if (partes.length == 4) {
            String titulo = partes[0];
            String descripcion = partes[1];
            Prioridad prioridad = Prioridad.valueOf(partes[2]);
            LocalDate fecha = LocalDate.parse(partes[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return new Task(titulo, descripcion, prioridad, fecha);
        }
        return null;
    }
    
    /**
     * Representación en consola con formato y color
     */
    public String toStringColoreado() {
        String reset = "\u001B[0m";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        return prioridad.getCodigoColor() + 
               "┌─────────────────────────────────────────────┐\n" +
               "│ Título: " + String.format("%-33s", titulo) + "│\n" +
               "│ Descripción: " + String.format("%-28s", 
                   (descripcion.length() > 28 ? descripcion.substring(0, 25) + "..." : descripcion)) + "│\n" +
               "│ Prioridad: " + String.format("%-30s", prioridad.getNombre()) + "│\n" +
               "│ Fecha: " + String.format("%-34s", fecha.format(formatter)) + "│\n" +
               "└─────────────────────────────────────────────┘" +
               reset;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("[%s] %s - %s (%s)", 
            prioridad.getNombre(), titulo, descripcion, fecha.format(formatter));
    }
}
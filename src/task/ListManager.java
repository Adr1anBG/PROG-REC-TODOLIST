import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class ListManager<T extends Task> {
    private LinkedList<T> tareas;
    private final String archivoTareas;

    public ListManager(String nombreArchivo) {
        this.archivoTareas = nombreArchivo;
        this.tareas = new LinkedList<>();
        cargarTareas();
    }

    private void guardarTareas() {
        try (FileOutputStream fos = new FileOutputStream(archivoTareas);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter writer = new BufferedWriter(osw)) {

            for (T tarea : tareas) {
                writer.write(tarea.toFileFormat());
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

    public void agregarTarea(T tarea) {
        tareas.add(tarea);
        guardarTareas();
        System.out.println("Tarea agregada exitosamente.");
    }

    public boolean eliminarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            tareas.remove(indice);
            guardarTareas();
            return true;
        }
        return false;
    }

    public void listarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║  No hay tareas pendientes. ¡Todo listo!   ║");
            System.out.println("╚════════════════════════════════════════════╝\n");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        LISTA DE TAREAS PENDIENTES          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        Iterator<T> iterator = tareas.iterator();
        int contador = 0;

        while (iterator.hasNext()) {
            T tarea = iterator.next();
            System.out.println("Tarea #" + (contador + 1) + ":");
            System.out.println(tarea.toStringColoreado());
            System.out.println();
            contador++;
        }

        System.out.println("Total de tareas: " + tareas.size());
    }

    public int getTotalTareas() {
        return tareas.size();
    }

    public boolean hayTareas() {
        return !tareas.isEmpty();
    }

    private void cargarTareas() {
        File archivo = new File(archivoTareas);

        if (!archivo.exists()) {
            System.out.println("Archivo de tareas no encontrado. Se creará uno nuevo.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader reader = new BufferedReader(isr)) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Task tarea = Task.fromFileFormat(linea);
                    if (tarea != null) {
                        tareas.add((T) tarea);
                    }
                }
            }

            System.out.println("Se cargaron " + tareas.size() + " tareas desde el archivo.");

        } catch (IOException e) {
            System.err.println("Error al cargar las tareas: " + e.getMessage());
        }
    }

    public void agregarTareaSwing() {
        System.out.println("\nAbriendo ventana para agregar tarea...");

        final Task[] tareaCreada = new Task[1];
        final CountDownLatch latch = new CountDownLatch(1);

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    TaskDialog dialog = new TaskDialog(null);
                    dialog.setVisible(true);

                    if (dialog.isTareaCreada()) {
                        tareaCreada[0] = dialog.getTarea();
                    }
                } catch (Exception e) {
                    System.err.println("Error al crear el diálogo: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        });

        try {
            latch.await();

            if (tareaCreada[0] != null) {
                this.agregarTarea((T) tareaCreada[0]);
                System.out.println("\nTarea agregada exitosamente:");
                System.out.println(tareaCreada[0].toStringColoreado());
            } else {
                System.out.println("\nOperación cancelada.");
            }
        } catch (InterruptedException e) {
            System.err.println("Error al esperar el cierre del diálogo: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public int eliminarTareaGestionada(int indice) {
        if (indice < 0 || indice >= tareas.size()) {
            return -1;
        }
        tareas.remove(indice);
        guardarTareas();
        return 0;
    }
}

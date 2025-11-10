import java.util.Scanner;

public class ToDoListApp {
    private static final String ARCHIVO_TAREAS = "tareas.txt";
    private static ListManager<Task> manager;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema");
        }

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     GESTOR DE TAREAS - TO DO LIST          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        manager = new ListManager<>(ARCHIVO_TAREAS);
        scanner = new Scanner(System.in);

        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    manager.agregarTareaSwing();
                    break;
                case 2:
                    eliminarTarea();
                    break;
                case 3:
                    listarTareas();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\n¡Hasta pronto! 👋");
                    break;
                default:
                    System.out.println("\n✗ Opción inválida. Intente nuevamente.\n");
            }

            if (!salir) {
                esperarEnter();
            }
        }

        scanner.close();
        System.exit(0);
    }

    private static void mostrarMenu() {
        System.out.println("\n┌────────────────────────────────────────────┐");
        System.out.println("│              MENÚ PRINCIPAL                │");
        System.out.println("├────────────────────────────────────────────┤");
        System.out.println("│ 1. Agregar tarea                           |");
        System.out.println("│ 2. Eliminar tarea                          |");
        System.out.println("│ 3. Listar todas las tareas                 |");
        System.out.println("│ 0. Salir                                   |");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("\nSeleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void eliminarTarea() {
        if (!manager.hayTareas()) {
            System.out.println("\n No hay tareas para eliminar.");
            return;
        }

        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("         ELIMINAR TAREA");
        System.out.println("═══════════════════════════════════════════\n");

        manager.listarTareas();

        System.out.print("\nIngrese el número de tarea a eliminar (0 para cancelar): ");
        try {
            int indice = Integer.parseInt(scanner.nextLine().trim());

            if (indice == 0) {
                System.out.println("Operación cancelada.");
                return;
            }

            if (indice > 0 && indice <= manager.getTotalTareas()) {
                System.out.print("¿Está seguro de eliminar esta tarea? (S/N): ");
                String confirmacion = scanner.nextLine().trim().toLowerCase();

                if (confirmacion.equals("s") || confirmacion.equals("si")) {
                    int resultado = manager.eliminarTareaGestionada(indice - 1);

                    if (resultado == 0) {
                        System.out.println("Tarea eliminada exitosamente.");
                    } else {
                        System.out.println("Índice inválido.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                System.out.println("Número de tarea inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private static void listarTareas() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("         LISTADO DE TAREAS");
        System.out.println("═══════════════════════════════════════════");
        manager.listarTareas();
    }

    private static void esperarEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}

# Manual de Usuario – To Do List (Consola + Swing)

Este manual describe el uso de la aplicación **To Do List**, compuesta por las clases `ToDoListApp` (interfaz de usuario) y `ListManager` (gestión de tareas). Aquí encontrarás instrucciones completas para ejecutar el programa, agregar tareas, eliminarlas, listarlas y comprender las salidas que genera.

---

## ✅ Descripción General

La aplicación permite gestionar una lista de tareas mediante **consola**, con apoyo de ventanas gráficas Swing para crear nuevas tareas. Implementa operaciones:

* Agregar tareas (ventana emergente Swing)
* Listar tareas en consola
* Eliminar tareas
* Guardado automático en archivo `tareas.txt`
* Carga automática desde el archivo al iniciar

La clase `ListManager` maneja **toda la lógica de negocio**, mientras que `ToDoListApp` solo gestiona la interacción con el usuario.

---

## ✅ Ejecución del Programa


Al iniciar, verás algo similar a:

```
╔════════════════════════════════════════════╗
║     GESTOR DE TAREAS - TO DO LIST          ║
╚════════════════════════════════════════════╝

Archivo de tareas no encontrado. Se creará uno nuevo.

## ✅ Menú Principal

El sistema presenta un menú interactivo:

```
┌────────────────────────────────────────────┐
│              MENÚ PRINCIPAL                │
├────────────────────────────────────────────┤
│ 1. Agregar tarea                           |
│ 2. Eliminar tarea                          |
│ 3. Listar todas las tareas                 |
│ 0. Salir                                   |
└────────────────────────────────────────────┘
Seleccione una opción:
```

# ✅ Funcionalidades

## 1️⃣ Agregar tarea

Selecciona la opción **1**.

El programa abrirá una **ventana Swing** para introducir los datos de la tarea.

En consola verás:

```
Abriendo ventana para agregar tarea...
```

Una vez completada la tarea en la ventana, se mostrará:

```
Tarea agregada exitosamente:
[colores y formato de la tarea]

---

## 2️⃣ Eliminar tarea

Opción **2** del menú.

Si no hay tareas:

```
No hay tareas para eliminar.
```

Si sí hay tareas, se mostrarán:

```
Tarea #1:
[detalle de la tarea]

Tarea #2:
...

Total de tareas: X
```

Después el programa pide:

```
Ingrese el número de tarea a eliminar (0 para cancelar):
```

Luego solicita confirmación:

```
¿Está seguro de eliminar esta tarea? (S/N):
```

Si confirmas:

```
Tarea eliminada exitosamente.
```

De lo contrario:

```
Operación cancelada.

## 3️⃣ Listar tareas

Opción **3**.

Salida típica:

```
════════════════════════════════════════════
         LISTADO DE TAREAS
════════════════════════════════════════════
Tarea #1:
[colores y descripción]

Tarea #2:
[colores y descripción]

Total de tareas: X
```

Si no hay tareas:

```
╔════════════════════════════════════════════╗
║  No hay tareas pendientes. ¡Todo listo!   ║
╚════════════════════════════════════════════╝
---

## ✅ Archivo de persistencia

El archivo `tareas.txt` se crea automáticamente en el directorio del programa.

Cada línea corresponde a una tarea en formato serializado mediante `Task.toFileFormat()`.

Ejemplo aproximado:

```
2024-01-10|Comprar leche|Alta
2024-01-11|Enviar informe|Media
```

---

# ✅ Clases Principales

## 📌 `ListManager<T extends Task>`

Encargada de **toda la lógica**:

* Guardar tareas
* Cargar tareas
* Eliminar con validación interna
* Agregar tareas
* Gestionar ventana Swing para agregar
* Listar tareas con formato

Métodos relevantes:

* `agregarTarea(T tarea)`
* `agregarTareaSwing()`
* `eliminarTareaGestionada(int indice)`
* `listarTareas()`
* `hayTareas()`
* `getTotalTareas()`

---

## 📌 `ToDoListApp`

Interfaz de usuario por consola.

Responsabilidades:

* Mostrar el menú
* Leer opciones
* Solicitar confirmaciones
* Delegar en `ListManager` la lógica de negocio
* Controlar el flujo del programa

---

# ✅ Flujo del Programa (Resumen)

1. Inicia la app ➜ carga archivo de tareas
2. Se muestra el menú principal
3. Usuario selecciona opción
4. `ToDoListApp` captura entrada
5. `ListManager` ejecuta la lógica
6. Se pide Enter para continuar
# Instalación y Ejecución — Sistema de Gestión Hospitalaria

Este documento explica cómo preparar el entorno, instalar y ejecutar la aplicación (Sistema de Gestión Hospitalaria).

---

##  Requisitos del Sistema

### Requisitos generales

| Requisito | Detalle |
|---|---|
| Java JDK | 8 o superior |
| IDE recomendado | Apache NetBeans 8.2 o superior (compatible con proyectos Java SE) |
| Sistema operativo | Windows, macOS o Linux |

### Dependencias

El proyecto **no requiere** gestores de dependencias externos como Maven o Gradle.

Utiliza únicamente bibliotecas estándar de Java:

- `java.util`
- `java.io`
- `javax.swing`
- `java.awt`

Por lo tanto, **no es necesario instalar una base de datos ni librerías externas**.

---

## Opción 1: Ejecutar desde NetBeans

1. Descargar o clonar el repositorio.
2. Extraer la carpeta del proyecto si se descargó como `.zip`.
3. Abrir **Apache NetBeans**.
4. Ir a:
   ```
   File → Open Project...
   ```
5. Seleccionar la carpeta:
   ```
   JavaApplication1
   ```
6. Abrir el proyecto.
7. Ejecutar mediante:
   - **Run Project **, o
   - presionando **F6**.

Al iniciar el programa se crea una instancia de `GestorHospital`, que carga los datos almacenados previamente.

La aplicación iniciará **simultáneamente**:

- La interfaz gráfica Swing.
- El menú interactivo de consola.

> **Nota:** Para utilizar la interfaz de consola es necesario ejecutar el proyecto desde un entorno que permita visualizar la salida y entrada estándar (por ejemplo, la consola integrada de NetBeans).

---

## Opción 2: Ejecutar el archivo JAR

El proyecto incluye un archivo ejecutable en la carpeta `dist`:

```
dist/GestionTurnos.jar
```

Con Java instalado, puede ejecutarse mediante:

```bash
java -jar dist/GestionTurnos.jar
```

También puede ejecutarse haciendo **doble clic** sobre el archivo `.jar`, siempre que el sistema operativo tenga Java correctamente asociado a ese tipo de archivo.

---

##  Persistencia de Datos

El sistema utiliza **serialización de objetos de Java** para almacenar la información de manera local.

- **Archivo utilizado:** `datos_hospital.dat`
- **Contenido:** el `Map` principal de enfermeras y sus respectivos turnos.

### Carga de datos

Al iniciar el programa, `GestorHospital` intenta cargar automáticamente `datos_hospital.dat`:

- Si el archivo existe y es válido → se recuperan los datos almacenados.
- Si el archivo no existe → el sistema crea datos iniciales de ejemplo.

Esto permite utilizar el sistema inmediatamente después de la instalación, sin configuración adicional.

### Guardado de datos

Los cambios se guardan **automáticamente** después de operaciones como:

- Registrar una enfermera.
- Modificar una enfermera.
- Eliminar una enfermera.
- Asignar un turno.
- Modificar un turno.
- Eliminar un turno.

De esta forma, la información permanece disponible después de cerrar y volver a ejecutar la aplicación.

---

##  Estructura del Proyecto

```
JavaApplication1/
│
├── src/
│   ├── controlador/
│   │   └── GestorHospital.java
│   │
│   ├── modelo/
│   │   ├── Administrador.java
│   │   ├── Enfermera.java
│   │   ├── EnfermeraNoEncontradaException.java
│   │   ├── EstadoTurno.java
│   │   ├── TipoTurno.java
│   │   ├── Trabajador.java
│   │   ├── Turno.java
│   │   └── TurnoException.java
│   │
│   ├── vista/
│   │   ├── MenuConsola.java
│   │   └── VentanaPrincipal.java
│   │
│   └── javaapplication1/
│       └── JavaApplication1.java
│
├── dist/
│   └── GestionTurnos.jar
│
├── datos_hospital.dat
├── build.xml
├── manifest.mf
└── README.md
```

---

##  Checklist rápido de instalación

- [ ] Java JDK 8+ instalado y disponible en el `PATH`.
- [ ] Proyecto descomprimido en una carpeta local.
- [ ] (Opcional) NetBeans 8.2+ instalado si se ejecutará desde el IDE.
- [ ] Ejecutar vía NetBeans (`Run Project` / F6) **o** vía terminal (`java -jar dist/GestionTurnos.jar`).
- [ ] Verificar que se abren ambas interfaces: la ventana Swing y la consola.

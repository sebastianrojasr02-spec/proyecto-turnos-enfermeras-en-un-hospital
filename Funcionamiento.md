# ️ Funcionamiento del Sistema — Sistema de Gestión Hospitalaria

Este documento describe cómo funciona internamente la aplicación: su arquitectura, entidades, operaciones disponibles y flujo de ejecución.

---

## 1. Arquitectura General

El proyecto separa sus responsabilidades en tres capas principales:

```
Modelo
   │
   │ Datos y entidades
   ▼
Controlador
   │
   │ Lógica de gestión
   ▼
Vista
   │
   ├── Interfaz gráfica Swing
   └── Interfaz de consola
```

### Modelo

Las clases del paquete `modelo` representan las entidades utilizadas por el sistema:

| Clase | Descripción |
|---|---|
| `Trabajador` | Clase abstracta base para los trabajadores |
| `Enfermera` | Representa a una enfermera y sus turnos asignados |
| `Administrador` | Representa un administrador del sistema |
| `Turno` | Representa un turno de trabajo |
| `TipoTurno` (enum) | `MANANA`, `TARDE`, `NOCHE` |
| `EstadoTurno` (enum) | `PENDIENTE`, `CONFIRMADO`, `CANCELADO`, `COMPLETADO` |
| Excepciones personalizadas | Relacionadas con enfermeras y turnos (`EnfermeraNoEncontradaException`, `TurnoException`) |

Las clases principales implementan `Serializable` para permitir la persistencia mediante serialización.

### Controlador: `GestorHospital`

Concentra toda la lógica principal del sistema. La colección central es:

```java
Map<String, Enfermera>
```

El **RUT** de la enfermera se usa como clave del mapa, permitiendo búsquedas directas por identificador.

Principales operaciones:

- `agregarEnfermera()`
- `buscarEnfermera()`
- `modificarEnfermera()`
- `eliminarEnfermera()`
- `buscarTurnoDeEnfermera()`
- `modificarTurno()`
- `eliminarTurno()`
- `buscarPorEspecialidad()`
- `buscarEnfermerasDisponibles()`
- `guardarDatos()`
- `cargarDatos()`

### Vista

Dos interfaces conviven al ejecutar la aplicación:

- **Gráfica (Swing):** `VentanaPrincipal.java`
- **Consola:** `MenuConsola.java`

---

## ️ Gestión de Enfermeras

Cada enfermera tiene:

- RUT (identificador único).
- Nombre.
- Especialidad.
- Turnos asignados.

| Operación | Descripción |
|---|---|
| Registrar | Incorpora una nueva enfermera. El RUT no puede repetirse |
| Listar | Muestra enfermeras con especialidad y cantidad de turnos |
| Buscar | Localiza una enfermera por su RUT |
| Modificar | Actualiza nombre y/o especialidad |
| Eliminar | Elimina una enfermera del sistema por RUT |

---

##  Gestión de Turnos

Cada enfermera puede tener una lista de turnos. Un turno contiene:

- Identificador.
- Fecha.
- Tipo de turno (`MANANA`, `TARDE`, `NOCHE`).
- Estado (`PENDIENTE`, `CONFIRMADO`, `CANCELADO`, `COMPLETADO`).
- Hora de inicio y término.
- Sector.
- Observaciones.

El sistema permite:

- Asignar turnos (se crean con estado inicial **PENDIENTE** al asignarse desde la consola).
- Buscar turnos por enfermera e identificador.
- Modificar fecha, tipo y estado.
- Eliminar turnos.

---

##  Consultas y Disponibilidad

### Buscar por especialidad

Recorre las enfermeras registradas y devuelve aquellas cuya especialidad coincide con la buscada (ej. `Urgencias`).

### Buscar enfermeras disponibles

Recibe dos parámetros:

- Especialidad.
- Fecha.

El sistema revisa los turnos de cada enfermera de esa especialidad. Si una enfermera **no tiene un turno asignado** en la fecha solicitada, se considera **disponible**.

---

## ️ Interfaz Gráfica (Swing)

La ventana principal se organiza en pestañas:

| Pestaña | Funciones |
|---|---|
| **Enfermeras** | Agregar, modificar, eliminar, buscar y listar (mostradas en tabla) |
| **Turnos** | Administrar los turnos asociados a cada enfermera |
| **Consultas** | Realizar consultas sobre las enfermeras registradas |
| **Disponibilidad** | Buscar enfermeras disponibles según especialidad y fecha |

---

##  Interfaz de Consola

`MenuConsola` ofrece un menú numérico con todas las operaciones principales:

```
1. Registrar Enfermera
2. Listar Enfermeras
3. Buscar Enfermera
4. Modificar Enfermera
5. Eliminar Enfermera
6. Asignar Turno
7. Buscar Turno
8. Modificar Turno
9. Eliminar Turno
10. Buscar por Especialidad
11. Buscar Enfermeras Disponibles
0. Salir
```

Esto permite usar todas las funciones del sistema sin depender de la interfaz gráfica.

---

## 🔄 Flujo General de Ejecución

```
Inicio
  │
  ▼
Crear GestorHospital
  │
  ▼
Intentar cargar datos_hospital.dat
  │
  ├── Archivo existe ──► Cargar información
  │
  └── Archivo no existe ──► Crear datos iniciales
                              │
                              ▼
                        Guardar datos
  │
  ▼
Iniciar interfaz gráfica
  │
  └── Iniciar menú de consola
  │
  ▼
Usuario realiza operaciones
  │
  ├── Enfermeras
  ├── Turnos
  ├── Consultas
  └── Disponibilidad
  │
  ▼
Guardar cambios
  │
  ▼
Fin
```

---

## Resumen Técnico

El sistema se basa en:

- **Java SE** como plataforma de desarrollo.
- **Java Swing** para la interfaz gráfica.
- **Colecciones** (`Map`, `List`) para administrar los datos en memoria.
- **Serialización de objetos** para la persistencia local.
- **Separación en capas**: modelo, controlador y vista.
- **Operaciones CRUD** completas para enfermeras y turnos.
- **Consultas** por especialidad y disponibilidad.
- **Manejo de tipos y estados de turno** mediante enums.

El sistema funciona de forma completamente local, sin requerir servicios externos, bases de datos ni librerías adicionales.

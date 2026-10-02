# Gestión de turnos de enfermeras en un hospital

Aplicación Java para registrar y administrar enfermeras y sus turnos de trabajo. El sistema dispone de dos interfaces de usuario: consola y Swing.

## Arquitectura

El proyecto separa las responsabilidades en cinco capas principales:

- `modelo`: entidades y comportamiento del dominio.
- `servicio`: reglas de negocio y operaciones de alto nivel.
- `controlador`: adaptación de datos entre las vistas y el servicio.
- `vista`: interacción con la persona usuaria.
- `persistencia`: almacenamiento en CSV mediante una interfaz de repositorio.

`GestorHospital` recibe un `RepositorioHospital`, por lo que la lógica de negocio no queda atada a `PersistenciaCSV`.

## Funcionalidades

- CRUD de enfermeras.
- CRUD de turnos.
- Búsqueda por especialidad.
- Consulta de enfermeras disponibles por especialidad y fecha.
- Consulta de carga horaria de turnos activos.
- Validación de entradas y excepciones de dominio.
- Persistencia CSV al inicio y al cierre.
- Interfaz de consola y ventana Swing sobre el mismo servicio de negocio.

## Ejecución

El proyecto utiliza Apache Ant/NetBeans. La clase principal es `javaapplication1.JavaApplication1`.

Al iniciar se elige entre consola y ventana.

## Persistencia

Los datos se almacenan en `datos_hospital.csv`. El gestor trabaja en memoria durante la ejecución y el repositorio se utiliza en momentos definidos de carga y guardado.

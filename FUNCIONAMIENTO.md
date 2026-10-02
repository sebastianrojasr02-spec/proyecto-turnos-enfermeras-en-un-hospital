# Funcionamiento del sistema

1. `JavaApplication1` crea un `GestorHospital` con `PersistenciaCSV` como implementación por defecto del repositorio.
2. El gestor carga los datos existentes. Cuando no hay información, crea dos enfermeras y turnos iniciales de demostración.
3. El usuario selecciona consola o ventana.
4. La vista obtiene los datos y envía las acciones al controlador correspondiente.
5. El controlador convierte entradas de texto a enums y captura las excepciones de negocio.
6. `GestorHospital` ejecuta las reglas de negocio y opera sobre el `Map<String, Enfermera>`.
7. Cada `Enfermera` encapsula su `List<Turno>` y expone operaciones de dominio para agregar, buscar y eliminar turnos, además de calcular su carga horaria y disponibilidad.
8. `PersistenciaCSV` sólo transforma el estado de los objetos a CSV y viceversa; no muestra mensajes al usuario.
9. Al salir de consola o cerrar la ventana se persisten los datos.

## Regla de disponibilidad

Una enfermera está disponible para una especialidad y fecha cuando su especialidad coincide y no posee un turno cuyo estado sea distinto de `CANCELADO` para esa fecha.

## Carga horaria

La carga horaria suma la duración de los turnos activos. Se contemplan turnos nocturnos que atraviesan medianoche, por ejemplo 22:00 a 08:00 equivale a 10 horas.

## Horarios estándar por tipo de turno

Para que la carga horaria sea coherente también en los turnos creados desde consola o Swing, cada `TipoTurno` define su horario estándar: MANANA 08:00-16:00, TARDE 14:00-22:00 y NOCHE 22:00-08:00. Al modificar el tipo de un turno, su horario se actualiza automáticamente. Los registros antiguos guardados como 00:00-00:00 se normalizan al cargarse desde CSV.

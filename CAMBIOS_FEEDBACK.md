# Cambios realizados a partir del feedback EP1-EP12

## EP1 - Análisis
Se amplía la argumentación del diseño: elección de Map y List, encapsulamiento, separación por capas, persistencia batch, enums, excepciones y reglas de disponibilidad. El informe explica también las alternativas evitadas y las consecuencias de cada decisión.

## EP2 - UML
Se reemplaza el diagrama desactualizado por un UML coherente con la implementación actual. Se agrega además un diagrama de arquitectura por paquetes.

## EP3 - Modularización y documentación
- `GestorHospital` pasa al paquete `servicio`.
- `RepositorioHospital` desacopla el servicio de `PersistenciaCSV`.
- `ConversorTurnos` centraliza la conversión de entradas a enums.
- Las operaciones de búsqueda de turnos y disponibilidad se acercan al objeto `Enfermera`.
- La presentación de todos los turnos utiliza una consulta agrupada entregada por el servicio, evitando que las vistas conozcan la estructura interna principal.
- Se agregan Javadoc y se limpian clases y responsabilidades.

## EP4 - Diseño
El informe deja de usar código y números de línea como evidencia principal. Las secciones describen decisiones, relaciones, flujo de datos y responsabilidades, apoyándose en UML, diagramas de flujo y capturas.

## EP5 - Sobrecarga
Se documenta el propósito conceptual de las sobrecargas: aceptar tanto objetos ya construidos como datos simples, sin duplicar la intención de la operación.

## EP6 - Herencia, sobreescritura y polimorfismo
Se reemplaza `obtenerIdentificacion()` como demostración principal de sobreescritura por una funcionalidad de dominio real: `calcularCargaHoraria()`. `Enfermera` calcula las horas de sus turnos activos y `Administrador` devuelve cero porque no recibe turnos de enfermería.

## EP7-EP9 - Funcionalidad y explicación
Se agregan capturas de las interfaces, resultados de consultas, carga horaria y flujo de disponibilidad. El texto explica qué hace cada funcionalidad, qué problema resuelve y cómo se relaciona con el diseño.

## EP10 - Interfaces
Se documentan por separado las interfaces de consola y Swing. En Swing se incluye la funcionalidad de mostrar todos los turnos y la consulta de carga horaria.

## EP11 - Excepciones
Se separan las excepciones en un paquete propio y se explica el criterio de uso: enfermera inexistente frente a conflicto específico de turno. Se documenta el flujo vista -> controlador -> servicio -> excepción -> mensaje.

## EP12 - Git
Se mantiene la referencia a los 20 commits mencionados en el feedback de evaluación.

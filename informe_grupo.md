% Informe de grupo DAGSS Alpha

# Descripción general del proyecto
El proyecto a desarollar es un sistema de gestión sanitario, también conocido como "Receta Electrónica".
El objetivo del proyecto es facilitar la gestión en el ámbito sanitario a través de las TICS.
La idea es elaborar un proyecto similar al sistema utilizado actualmente en el SERGAS.

El sistema será usado por los siguientes actores:

* Farmacias
* Médicos
* Administradores

Las capacidades de cada uno de estos actores se detallan a continuación:

## Farmacia
Una farmacia será responsable de la entrega de los médicamentos emitidos en un tratamiento
elaborado por un médico colegiado.
El paciente asistirá físicamente a la farmacia más oportuna. En el futuro se considerara la entrega
a través de servicios de mensajería.

## Médicos
Los médicos son responsables de la emisión de tratamientos a pacientes respecto a una cita previamente establecida.
El sistema facilita el día a día de estos usuarios gracias a un sistema de control de citas que agiliza el 
proceso de atención al paciente.

## Administradores
Los administradores son usuarios se encargan de añadir nuevas farmacias y médicos al sistema.

# Funcionalidades cubiertas por el grupo
Al grupo se le han asignado dos bloques de tareas: las relacionadas con el médico y las relacionadas con la farmacia.
Las especificación de las tareas cubiertas es la siguiente:

1.   Como usuario "farmacia" podrá loguearse en el sistema desde una URL específica con el identificador de farmacia y su contraseña
2.  Como usuario "farmacia" una vez logueado introduciendo el nº de tarjeta sanitaria de un "paciente" se accederá a los distintos "planes de recetas"/"tratamientos" que este tenga en vigor, visualizando las recetas disponibles desde la fecha actual. Para cada "receta" se mostará su información (medicamento, médico que la generó), su estado ("disponible para suministro" o "no disponible") y en el caso de las "disponibles" sus "fechas de validez"
3.  Como usuario "farmacia" desde una receta del "plan de recetas" de un paciente se podrá seleccionar una "receta" con estado "disponible para suministro" para anotarla como suministrada. En ningún caso se podrá "suministrar" una "receta" fuera de sus "fechas de suminsitro".
4.  Como usuario "farmacia" una vez loagueado podrá modificar sus credenciales de acceso (contraseña) y los datos básicos (dirección, nombre de la farmacia, etc) de su farmacia
5.  Como usuario "médico" podrá loguearse en el sistema desde una URL específica con el identificador de facultativo y su contraseña
6.  Como usuario "médico" una vez logueado podrá acceder a su "agenda" para el día en curso, donde se mostrará la información de las "citas" previstas, con indicación del paciente, hora prevista y estado de las mismas ("realizada", "pendiente", "anulada", etc)
7.  Como usuario "médico" se podrá seleccionar una de las "citas" en estado "pendiente" para atenderla, accediendo al formulario de atención al paciente.
8.  Como usuario "médico" desde el formulario de atención al paciente se podrá confeccionar un "tratamiento" para el "paciente" concreto, consistente en un conjunto de "prescripciones" de "medicamentos" incluidos en el sistema.
9.  Como usuario "médico", para la "prescripción" de un "medicamento" como parte de un "tratamiento" se seleccionará mediante un buscador el "medicamento" concreto y se vinculará a ese tratamiento una "dosis" y un periodo de medicación (con fechas de principio y fin)
10. Como usuario "médico" se podrá eliminar o editar un "tratamiento" previamente confeccionado para el "paciente" concreto
10. El sistema, como resultado de la confección de un "tratamiento" por parte de un "médico", creará el "plan de recetas" para el correspondiente "paciente". Ese plan de recetas incluirá la lista de futuras "recetas", junto con sus fechas de validez, que necesitará el paciente en función de la duración de la "prescripción", la "dosis" y la "capacidad" del envase en que se distribuye el "medicamento".
11. Como usuario "médico", tanto si se ha realizado una prescripción como sí no, se podrá finalizar una cita, anotándola como "realizada"
12. Como usuario "médico", desde el formulario de atención al paciente, se podrá anotar como "no realizada" una cita a la que no se presente un paciente.
13. Como usuario "médico" una vez logueado podrá modificar sus credenciales de acceso (contraseña) y sus datos básicos (nombres, contacto, etc). En ningún caso podrá modificar datos de su "agenda"

## Descripción de los elementos que conforman la implementación realizada
A continuación se detallan los elementos que han sido usados para implementar el sistema: vistas JSF, objetos de respaldo y EJBs.

## Implementación de las funcionalidades de la farmacia
Para la farmcia se ha partido del esqueleto proporcionado y se han añadido/modificado los siguientes elementos:

* 

## Implementation de las funcionalidades del médico:



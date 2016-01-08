% Informe de grupo DAGSS Alpha
% Marcos Carneiro Paz; Hugo González Labrador; Jagoda Sawicka
% 8 de Enero de 2016

# Datos técnicos:
El proyecto es alojado en GitHub:

*https://github.com/dagss2015/DAGSS2015_alpha*

En releases se encuentra la versión 1.0.0:

*https://github.com/dagss2015/DAGSS2015_alpha/releases/tag/v1.0.0*

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
Para la farmacia se ha partido del esqueleto proporcionado y se han añadido/modificado los siguientes archivos:

```
 src/main/java/es/uvigo/esei/dagss/controladores/farmacia/FarmaciaControlador.java
 src/main/java/es/uvigo/esei/dagss/dominio/daos/FarmaciaDAO.java
 src/main/webapp/farmacia/plantillas/menuFarmacia.xhtml
 src/main/webapp/farmacia/privado/index.xhtml
 src/main/webapp/farmacia/privado/ver_paciente.xhtml
```

La funcionalidad cubierta en cada elementos es la siguiente:

* FarmaciaControlador.java: Es el EJB encargado de la lógica de la farmacia. Esto es,  buscar pacientes y administrar medicamentos válidos.
* FarmaciaDAO.java: Es el encargado de la peristencia de las entidades de tipo Farmacia usando JPA como motor de persistencia.
* menuFarmacia.xhtml, index.xhtml, ver_paciente.xhtml: es la UI. Se ha implementado con el framework JSF usando Faceletes como sistema de plantillas y PrimeFaces como librería de componentes.





## Implementation de las funcionalidades del médico:
Para la lógica del médico se ha partido del esqueleto proporcionado y se han añadido/modificado los siguientes archivos:

```
src/main/java/es/uvigo/esei/dagss/controladores/medico/MedicoControlador.java
src/main/webapp/medico/plantillas/menuMedico.xhtml
src/main/webapp/medico/privado/detallesCita.xhtml
src/main/webapp/medico/privado/fragmentoDialogoEditar.xhtml
src/main/webapp/medico/privado/index.xhtml

```
La funcionalidad cubierta en cada elementos es la siguiente:

* MedicoControlador.java: Es el EJB encargado de la lógica del médico. Esto es, atender citas y gestionar tratamientos. Se apoya en en el EJB de Tratamientos.
* menuMédico.xhtml, index.xhtml, detallesCita.xhtml, fragmentoDialogoEditar.xhtml: es la UI. Se ha implementado con el framework JSF usando Faceletes como sistema de plantillas y PrimeFaces como librería de componentes.



## Elementos de apoyo.

```
src/main/java/es/uvigo/esei/dagss/controladores/tratamiento/TratamientoControlador.java
src/main/java/es/uvigo/esei/dagss/dominio/daos/CitaDAO.java
src/main/java/es/uvigo/esei/dagss/dominio/daos/MedicamentoDAO.java
src/main/java/es/uvigo/esei/dagss/dominio/daos/PrescripcionDAO.java
src/main/java/es/uvigo/esei/dagss/dominio/daos/RecetaDAO.java
```

Estos elementos se han creado para implementar la lógica tanto de la farmacia y del médico sin 
acoplar ambas partes. De esta forma se mejora la cohesión y se disminuye el acoplamiento.

# Reparto de tareas/funcionalidades entre los miembros del grupo (si corresponde)

El proyecto ha sido desarrollado íntegramento con todos los miembros del grupo presentes en la implementación.

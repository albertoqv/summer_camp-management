<p align="center">
  <img src="docs/logo.png" alt="Summer Camp Manager" width="400">
</p>

Proyecto de equipo — asignatura de la carrera, Universidad de Córdoba.

Aplicación web para la gestión de campamentos de verano: inscripciones, asignación de monitores y actividades organizadas por nivel educativo, con atención especial a los asistentes que la requieren.

![Java](https://img.shields.io/badge/-Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![JUnit](https://img.shields.io/badge/-JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Servlets](https://img.shields.io/badge/-Servlets%20%2F%20JSP-E76F00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/-MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/-Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)

## Arquitectura

El proyecto sigue Arquitectura Limpia por capas, con una separación estricta entre:

- **`business/`** — la lógica de negocio: DTOs, managers, factories (por ejemplo, `EarlyRegisterInscriptionFactory` / `LateRegisterInscriptionFactory` según la fecha de inscripción) y excepciones de dominio propias para cada regla de negocio.
- **`data/`** — el acceso a datos, con **dos implementaciones intercambiables** de cada DAO: una en base de datos (MySQL) y otra en memoria. Esto permite testear toda la lógica de negocio sin depender de una base de datos real.
- **`display/`** — dos interfaces sobre la misma lógica de negocio: una por consola (`display/cli/`) y otra web (`display/web/`, con Servlets y JSP siguiendo el patrón MVC).

## Funcionalidades

- Inscripciones con precio y posibilidad de baja, con control de aforo por campamento.
- Asignación de monitores —generales y de educación especial— a actividades y campamentos organizados por nivel educativo.
- Aviso de asistentes que requieren atención especial, y validación de que cada campamento cuenta con el monitor especializado necesario.
- Acceso a la aplicación por roles de usuario.

## Mi parte en el proyecto

Trabajé sobre todo en la **suite de tests** del proyecto (más de 3.000 líneas): pruebas unitarias con JUnit 5 sobre las entidades de dominio, las factories de inscripción y los managers de negocio, apoyándome en las implementaciones en memoria de los DAOs para poder testear la lógica sin necesidad de una base de datos real. También contribuí, en menor medida, al diseño de la base de datos.

## Nota sobre esta versión pública

`config.properties` contiene credenciales de ejemplo, no las reales — usa `config.properties.example` como plantilla y rellena tus propios datos de conexión a MySQL.

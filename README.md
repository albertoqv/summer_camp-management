<p align="center">
  <img src="docs/logo.png" alt="Summer Camp Manager" width="480">
</p>

<p align="center">
  Aplicación de gestión de campamentos de verano — proyecto de equipo, Programación Web, Universidad de Córdoba.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/-Java-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/-JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5">
  <img src="https://img.shields.io/badge/-Servlets%20%2F%20JSP-E76F00?style=for-the-badge&logo=java&logoColor=white" alt="Servlets / JSP">
  <img src="https://img.shields.io/badge/-MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/-Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black" alt="Apache Tomcat">
</p>

## El problema

Un campamento de verano tiene más reglas de negocio de las que parece a simple vista: hay que controlar el aforo de cada campamento, repartir a los asistentes por nivel educativo (preescolar, primaria, adolescentes), asignar monitores a las actividades con un mínimo de monitores por actividad y, cuando un asistente necesita atención especial, garantizar que el campamento cuenta con un monitor de educación especial disponible. A eso se suma la gestión de inscripciones completas o parciales, con un precio distinto y unos plazos de alta/baja que cambian según la inscripción se haga en periodo de *early* o *late registration* respecto a la fecha de inicio del campamento.

**Summer Camp Manager** nace como respuesta a ese enunciado: una aplicación que centraliza el alta de campamentos, actividades y monitores, gestiona las inscripciones con sus reglas de precio y plazos, y avisa cuando una regla de negocio no se cumple (una actividad sin monitores suficientes, un campamento sin el monitor especializado que necesita, un asistente que ya está inscrito, etc.) en lugar de dejar que ese error pase desapercibido.

## Arquitectura

El proyecto sigue **Arquitectura Limpia por capas**, con una separación estricta entre:

- **`business/`** — la lógica de negocio: DTOs, managers (`AssistantsManager`, `CampsManager`, `InscriptionManager`, `UsersManager`), factories de inscripción (`EarlyRegisterInscriptionFactory` / `LateRegisterInscriptionFactory`, según la fecha de inscripción respecto al inicio del campamento) y más de 25 excepciones de dominio propias, una por cada regla de negocio incumplida.
 **`data/`** — el acceso a datos, con **dos implementaciones intercambiables** de cada DAO: una en base de datos (MySQL, con las consultas parametrizadas en `sql.properties`) y otra en memoria (`data/memory/`). Esto permite testear toda la lógica de negocio sin depender de una base de datos real.
 **`display/`** — dos interfaces sobre la misma lógica de negocio: una por consola (`display/cli/`, con menús de texto) y otra web (`display/web/`, con Servlets y JSP siguiendo el patrón MVC, más filtros para control de acceso y de sesión).

```
web/src/main/java
├── business/    → DTOs, managers, factories, excepciones de dominio
├── data/        → DAOs (MySQL + en memoria) y criterios de consulta
└── display/     → interfaz de consola (cli) e interfaz web (web: servlets, JSP, filtros)
web/tests/       → suite de tests JUnit 5, organizada en domain/ y managers/
```

## Funcionalidades

- Alta de campamentos, actividades y monitores, con actividades organizadas por nivel educativo y franja horaria (mañana/tarde).
- Inscripciones completas o parciales, con cálculo de precio y control de plazos de inscripción anticipada/tardía y de baja.
- Control de aforo por campamento y de monitores mínimos necesarios por actividad.
- Aviso de asistentes que requieren atención especial y validación de que el campamento cuenta con el monitor de educación especial correspondiente.
- Acceso a la aplicación por roles de usuario (administrador / asistente), con filtros de sesión y control de acceso en la capa web.

## Capturas de pantalla

*(Pendiente de añadir — ver la sección "Cómo ejecutarlo" más abajo para desplegar la aplicación y capturar las vistas reales. Una vez tengas las imágenes, colócalas en `docs/screenshots/` y sustituye estas líneas por, por ejemplo:)*

```markdown
![Login](docs/screenshots/login.png)
![Gestión de campamentos](docs/screenshots/camps.png)
![Inscripciones](docs/screenshots/inscriptions.png)
```

## Cómo ejecutarlo

El proyecto está montado como un **Dynamic Web Project de Eclipse** (no usa Maven ni Gradle), así que la vía más directa para levantarlo es Eclipse + Tomcat + MySQL:

1. **Requisitos**: JDK 16+, Eclipse IDE for Enterprise Java Developers (con Web Tools Platform), Apache Tomcat 8.5 o superior, y un servidor MySQL accesible.
2. **Base de datos**: crea un esquema en MySQL y ejecuta `script.sql` (raíz del repo) para generar las tablas (`Activity`, `Assistant`, `Camp`, `CampActivity`, `Inscription`, `Monitor`, `MonitorActivity`, `User`, `AssistantUser`).
3. **Credenciales**: edita `web/src/main/webapp/WEB-INF/web.xml` y sustituye los parámetros `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` por los de tu propia base de datos. ⚠️ **Importante**: ahora mismo ese archivo trae unas credenciales de ejemplo de un servidor de la universidad — revísalo y reemplázalo antes de ejecutar el proyecto o de dejar el repositorio en público (ver nota de seguridad más abajo).
4. **Importar el proyecto**: en Eclipse, `File > Import > Existing Projects into Workspace`, seleccionando la carpeta `web/`. Debería reconocerse como Dynamic Web Project (ya trae `.project` y `.classpath`).
5. **Servidor**: registra Apache Tomcat 8.5+ en Eclipse (`Window > Preferences > Server > Runtime Environments`) y asócialo al proyecto.
6. **Ejecutar**: clic derecho sobre el proyecto → `Run As > Run on Server`. Tomcat desplegará la aplicación (por defecto en algo como `http://localhost:8080/web/`, según el nombre de despliegue que use Eclipse).
7. Desde ahí, regístrate como usuario desde la vista de login y navega por la gestión de campamentos, actividades, monitores e inscripciones — ese es el momento de hacer las capturas de pantalla.

El driver JDBC de MySQL ya está incluido en `web/lib/mysql-connector.jar` y en `WEB-INF/lib/`, no hace falta añadirlo aparte.

### Tests

La suite de tests (JUnit 5, en `web/tests/`) se ejecuta desde Eclipse sin necesidad de base de datos, porque usa las implementaciones en memoria de los DAOs: clic derecho sobre la carpeta `tests` → `Run As > JUnit Test`.

## Mi parte en el proyecto

Dentro de un proyecto de equipo en el que todos pasamos por las distintas capas de la aplicación, mi trabajo se concentró sobre todo en la **suite de tests** (JUnit 5, unas 3.000 líneas repartidas en 11 clases y en torno a 90 casos de prueba): tests unitarios de las entidades de dominio, de las factories de inscripción y de los managers de negocio, apoyándome en las implementaciones en memoria de los DAOs para poder validar toda la lógica —incluyendo que cada regla de negocio lanzara la excepción de dominio correcta— sin depender de una base de datos real. Antes de escribir cada test tocaba pensar bien los casos límite de cada regla (aforo justo al máximo, inscripción en el último día del plazo, campamento sin monitor especializado...), así que buena parte del esfuerzo estuvo en plantear esos escenarios, no solo en codificarlos.

## Nota de seguridad sobre esta versión pública

`web/src/main/webapp/WEB-INF/web.xml` contiene actualmente credenciales de conexión a un servidor MySQL real (no un placeholder). Antes de publicar o compartir este repositorio, sustitúyelas por valores de ejemplo (puedes usar el mismo `config.properties.example` como referencia de formato) y, si esas credenciales siguen activas, considera cambiarlas.

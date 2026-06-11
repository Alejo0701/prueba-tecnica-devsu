README API Testing - Demoblaze

Resumen:
Este repositorio incluye pruebas de API para Demoblaze usando Karate y JUnit 5.
Las pruebas validan signup/login y escenarios de usuario existente / credenciales incorrectas.

Estructura principal:
- devsu/pom.xml: proyecto Maven con dependencias de Karate.
- devsu/src/test/java/api/ApiTestRunner.java: runner de Karate para JUnit 5.
- devsu/src/test/resources/auth.feature: escenarios API de Demoblaze.
- devsu/target/karate-reports/: reportes generados por Karate.

Requisitos:
- Java 17
- Maven 3.x
- Conexión a internet para alcanzar https://api.demoblaze.com

Ejecución:
1. Ir a la carpeta del proyecto Maven:
   cd devsu
2. Ejecutar el test de API:
   mvn clean test -Dtest=api.ApiTestRunner

Qué valida:
- Registro de un usuario nuevo exitoso.
- Registro de un usuario existente con mensaje de error.
- Login con datos correctos y retorno de token.
- Login con contraseña incorrecta.
- Login con usuario inexistente.

Resultados:
- Karate genera reportes HTML en `devsu/target/karate-reports/`.
- El resultado principal se puede revisar en `karate-summary.html` dentro de esa carpeta.

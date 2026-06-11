README - Prueba Automatización E2E Demoblaze

Resumen:
Este repositorio contiene una automatización E2E para el sitio https://www.demoblaze.com/.
La solución usa Selenium WebDriver, TestNG y Extent Reports para ejecutar la compra completa y generar evidencia.

Estructura principal:
- devsu/pom.xml: proyecto Maven y dependencias.
- devsu/src/main/java/pages/: page objects para Home, Cart y Checkout.
- devsu/src/test/java/tests/: prueba E2E y configuración base de WebDriver y reportes.
- devsu/reports/: reportes generados por Extent Reports.
- devsu/reports/screenshots/: capturas de pantalla tomadas al finalizar cada test.

Requisitos:
- Java 17
- Maven 3.x
- Chrome instalado y compatible con el driver de Selenium

Ejecución:
1. Ir a la carpeta del proyecto Maven:
   cd devsu
2. Ejecutar el test E2E:
   mvn clean test -Dtest=E2EPurchaseTest

Resultados:
- Reporte HTML en `devsu/reports/extent-report.html`
- Capturas en `devsu/reports/screenshots/`

Notas:
- El test E2E automatiza la selección de productos, el carrito y el checkout.
- La base del proyecto está diseñada para usar TestNG y extensiones de reporte.

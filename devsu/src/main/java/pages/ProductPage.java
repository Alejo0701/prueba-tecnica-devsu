package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By addToCartButton = By.cssSelector("a[onclick*='addToCart']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Agrega el producto actual al carrito, acepta el alert, vuelve a la página principal
     * y retorna una nueva instancia de HomePage para continuar el flujo.
     * @param homeUrl URL de la página principal
     * @return una instancia de HomePage (ya en la página principal)
     */
    public HomePage addToCartAndGoToHome(String homeUrl) {
        // Hacer clic en "Add to cart"
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

        // Manejar el alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Pequeña pausa para asegurar que el alert se cierra
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Navegar directamente a la página principal
        driver.get(homeUrl);

        // Retornar la página principal lista para más acciones
        return new HomePage(driver);
    }
}
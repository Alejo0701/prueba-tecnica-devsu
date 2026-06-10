package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String URL = "https://www.demoblaze.com/";

    private By productLinks = By.cssSelector(".card-title a");
    private By cartButton = By.id("cartur");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(URL);
    }

    public String getHomeUrl() {
        return URL;
    }

    /**
     * Retorna todos los nombres de productos disponibles en la página principal.
     */
    public List<String> getAllProductNames() {
        return driver.findElements(productLinks).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public ProductPage clickOnProduct(String productName) {
        driver.findElements(productLinks).stream()
                .filter(elem -> elem.getText().equals(productName))
                .findFirst()
                .ifPresent(WebElement::click);
        return new ProductPage(driver);
    }

    public void goToCart() {
        driver.findElement(cartButton).click();
    }
}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String URL = "https://www.demoblaze.com/";

    private By productLinks = By.cssSelector(".card-title a");
    private By cartButton = By.id("cartur");
    private By nextButton = By.id("next2");
    private By categoryPhones = By.linkText("Phones");
    private By categoryLaptops = By.linkText("Laptops");
    private By categoryMonitors = By.linkText("Monitors");

    // Mapa estático: producto -> categoría
    private static final Map<String, String> PRODUCT_CATEGORY = new LinkedHashMap<>();
    static {
        // Phones
        PRODUCT_CATEGORY.put("Samsung galaxy s6", "Phones");
        PRODUCT_CATEGORY.put("Nokia lumia 1520", "Phones");
        PRODUCT_CATEGORY.put("Nexus 6", "Phones");
        PRODUCT_CATEGORY.put("Samsung galaxy s7", "Phones");
        PRODUCT_CATEGORY.put("Iphone 6 32gb", "Phones");
        PRODUCT_CATEGORY.put("HTC One M9", "Phones");
        PRODUCT_CATEGORY.put("Sony xperia z5", "Phones");
        // Laptops
        PRODUCT_CATEGORY.put("MacBook Pro", "Laptops");
        PRODUCT_CATEGORY.put("MacBook air", "Laptops");
        PRODUCT_CATEGORY.put("Dell i7 8gb", "Laptops");
        PRODUCT_CATEGORY.put("2017 Dell 15.6 Inch", "Laptops");
        PRODUCT_CATEGORY.put("Sony vaio i5", "Laptops");
        PRODUCT_CATEGORY.put("Sony vaio i7", "Laptops");
        // Monitors
        PRODUCT_CATEGORY.put("Apple monitor 24", "Monitors");
        PRODUCT_CATEGORY.put("ASUS Full HD", "Monitors");
    }

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
     * Retorna todos los productos (claves del mapa estático).
     */
    public List<String> getAllProductNames() {
        return new ArrayList<>(PRODUCT_CATEGORY.keySet());
    }

    /**
     * Navega a la categoría del producto y avanza páginas si es necesario hasta encontrarlo.
     */
    public ProductPage clickOnProduct(String productName) {
        String category = PRODUCT_CATEGORY.get(productName);
        if (category == null) {
            throw new IllegalArgumentException("Producto no encontrado: " + productName);
        }

        // 1. Navegar a la categoría
        clickCategory(category);
        wait.until(ExpectedConditions.presenceOfElementLocated(productLinks));

        // 2. Si el producto no está visible en la página actual, avanzar páginas
        while (!isProductVisibleOnCurrentPage(productName) && isNextButtonEnabled()) {
            clickNextAndWait();
        }

        // 3. Si después de recorrer aún no se encuentra, lanzar error
        if (!isProductVisibleOnCurrentPage(productName)) {
            throw new RuntimeException("Producto no encontrado después de recorrer páginas: " + productName);
        }

        // 4. Hacer clic en el producto
        driver.findElements(productLinks).stream()
                .filter(elem -> elem.getText().equals(productName))
                .findFirst()
                .ifPresent(WebElement::click);

        return new ProductPage(driver);
    }

    private boolean isProductVisibleOnCurrentPage(String productName) {
        return driver.findElements(productLinks).stream()
                .anyMatch(elem -> elem.getText().equals(productName));
    }

    private void clickCategory(String category) {
        By locator;
        switch (category) {
            case "Phones":   locator = categoryPhones; break;
            case "Laptops":  locator = categoryLaptops; break;
            case "Monitors": locator = categoryMonitors; break;
            default: throw new IllegalArgumentException("Categoría inválida: " + category);
        }
        driver.findElement(locator).click();
        // Pequeña pausa para que cargue la categoría
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private boolean isNextButtonEnabled() {
        WebElement next = driver.findElement(nextButton);
        return !next.getAttribute("class").contains("disabled");
    }

    private void clickNextAndWait() {
        driver.findElement(nextButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(productLinks));
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void goToCart() {
        driver.findElement(cartButton).click();
    }
}
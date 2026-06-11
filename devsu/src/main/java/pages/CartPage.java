package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Selector por XPath (para contar filas)
    private By productRows = By.xpath("//tbody[@id='tbodyid']/tr");

    // 2. Selector por CSS (para obtener nombres de productos)
    private By productTitleCells = By.cssSelector("#tbodyid tr td:nth-child(2)");

    // 3. Selector por XPath con función normalize-space() (botón "Place Order")
    private By placeOrderButton = By.xpath("//button[normalize-space()='Place Order']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Uso de XPath
    public int getCartItemsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productRows));
        return driver.findElements(productRows).size();
    }

    // Uso de CSS selector
    public List<String> getProductNames() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitleCells));
        List<WebElement> titleCells = driver.findElements(productTitleCells);
        return titleCells.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Uso de XPath con función
    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
    }
}
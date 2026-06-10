package tests;

import com.aventstack.extentreports.Status;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class E2EPurchaseTest extends BaseTest {

    @Test
    public void testCompletePurchaseFlowWithTwoRandomProducts() {
        test.log(Status.INFO, "=== INICIO TEST E2E ===");
        HomePage home = new HomePage(driver);
        home.open();
        test.log(Status.INFO, "Home page abierta");

        String homeUrl = home.getHomeUrl();
        List<String> allProducts = home.getAllProductNames();
        test.log(Status.INFO, "Productos disponibles: " + allProducts.size());

        // Seleccionar 2 aleatorios
        Random rand = new Random();
        Set<String> selectedSet = new HashSet<>();
        while (selectedSet.size() < 2) {
            selectedSet.add(allProducts.get(rand.nextInt(allProducts.size())));
        }
        List<String> selectedProducts = new ArrayList<>(selectedSet);
        test.log(Status.INFO, "Productos seleccionados: " + selectedProducts);

        // Agregar productos
        for (String product : selectedProducts) {
            test.log(Status.INFO, "Agregando: " + product);
            home = home.clickOnProduct(product).addToCartAndGoToHome(homeUrl);
        }

        home.goToCart();
        test.log(Status.INFO, "Navegando al carrito");

        CartPage cart = new CartPage(driver);
        int items = cart.getCartItemsCount();
        Assert.assertEquals(items, 2, "Cantidad incorrecta");
        test.log(Status.PASS, "Cantidad de productos: " + items);

        List<String> cartProducts = cart.getProductNames();
        Collections.sort(cartProducts);
        Collections.sort(selectedProducts);
        Assert.assertEquals(cartProducts, selectedProducts, "Los productos no coinciden");
        test.log(Status.PASS, "Productos verificados: " + cartProducts);

        cart.proceedToCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillForm("Juan Perez", "Colombia", "Medellin",
                "4111111111111111", "12", "2028");
        test.log(Status.INFO, "Formulario completado");

        checkout.completePurchase();
        String msg = checkout.getSuccessMessage();
        Assert.assertTrue(msg.contains("Thank you for your purchase!"));
        test.log(Status.PASS, "Compra exitosa: " + msg);

        checkout.closeSuccessAlert();
        test.log(Status.INFO, "=== TEST FINALIZADO ===");
    }
}
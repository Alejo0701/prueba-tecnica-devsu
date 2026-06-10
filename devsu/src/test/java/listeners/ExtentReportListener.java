package listeners;

import com.aventstack.extentreports.Status;
import tests.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        // Crear el test en el reporte con el nombre del método
        BaseTest.test = BaseTest.extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseTest.test.log(Status.PASS, "Test ejecutado exitosamente");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest.test.log(Status.FAIL, "Fallo en el test: " + result.getThrowable().getMessage());
        // Opcional: agregar captura de pantalla
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        BaseTest.test.log(Status.SKIP, "Test omitido: " + result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        BaseTest.extent.flush();
    }
}
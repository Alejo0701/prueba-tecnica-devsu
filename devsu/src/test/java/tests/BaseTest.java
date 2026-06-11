package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class BaseTest {
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "/reports/screenshots";

    protected WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeClass
    public static void setupReport() {
        String screenshotsDir = System.getProperty("user.dir") + "/reports/screenshots";
        File dir = new File(screenshotsDir);
        if (dir.exists()) {
            try {
                FileUtils.cleanDirectory(dir);
                System.out.println("Carpeta de capturas limpiada: " + screenshotsDir);
            } catch (IOException e) {
                System.err.println("No se pudo limpiar la carpeta de capturas: " + e.getMessage());
            }
        } else {
            dir.mkdirs();
        }
        String reportPath = System.getProperty("user.dir") + "/reports/extent-report.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("DemoBlaze Automation Report");
        sparkReporter.config().setDocumentTitle("E2E Test Results");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.createTest(method.getDeclaringClass().getSimpleName() + "." + method.getName());
        // WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--headless"); // descomentar para headless
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
            if (screenshotPath != null) {
                if (result.isSuccess()) {
                    test.info("Captura de pantalla final", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } else {
                    test.fail("Fallo del test", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            }
            driver.quit();
        }
        if (extent != null) {
            extent.flush();
        }
    }

    protected String captureScreenshot(String screenshotName) {
        if (driver == null) {
            return null;
        }

        String sanitized = screenshotName.replaceAll("[^a-zA-Z0-9-_]", "_");
        String screenshotPath = SCREENSHOT_DIR + "/" + sanitized + "_" + System.currentTimeMillis() + ".png";

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = Paths.get(screenshotPath);
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            return screenshotPath;
        } catch (IOException e) {
            if (test != null) {
                test.warning("Error al generar captura de pantalla: " + e.getMessage());
            }
            return null;
        }
    }
}
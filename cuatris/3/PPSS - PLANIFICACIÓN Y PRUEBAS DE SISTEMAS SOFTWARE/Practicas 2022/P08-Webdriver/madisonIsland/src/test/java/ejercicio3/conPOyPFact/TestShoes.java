package ejercicio3.conPOyPFact;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;


public class TestShoes {
    public WebDriver driver;
    public MyAccountPage accPage;




    @BeforeEach
    public void setUp() {
        Cookies.storeCookiesToFile("Ejercicio1@ppss.com", "Ejercicio1");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(Boolean.parseBoolean(System.getProperty("chromeHeadless")));

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();

        Cookies.loadCookiesFromFile(driver);

        driver.get("http://demo-store.seleniumacademy.com/customer/account/");
        accPage = PageFactory.initElements(driver, MyAccountPage.class);
    }
    @AfterEach
    public void finish() {
        driver.close();
    }

    @Test
    public void compareShoes() {
        Assertions.assertEquals("My Account", accPage.getTile());
        ShoesPage shoesPage = accPage.accessShoesPage();
        Assertions.assertEquals("Shoes - Accessories", shoesPage.getTile());
        shoesPage.selectShoeToCompare(5);
        shoesPage.selectShoeToCompare(6);
        ProductComparisonPage compPage = shoesPage.submitCompare();
        Assertions.assertEquals("Products Comparison List - Magento Commerce", compPage.getTile());
        shoesPage = compPage.close();
        Assertions.assertEquals("Shoes - Accessories", shoesPage.getTile());
        shoesPage.ClearComparison();
        Assertions.assertEquals("The comparison list was cleared.", shoesPage.getMessageSpanValue());
    }
}


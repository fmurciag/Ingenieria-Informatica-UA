package ejercicio2.conPO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestLogin2 {
    private WebDriver driver;
    private HomePage homePage;
    private CustomerLoginPage customerLoginPage;
    private MyAccountPage myAccountPage;
    @BeforeEach
    public void start(){
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        homePage=new HomePage(driver);
    }
    @AfterEach
    public void cerrarNavegador(){
        driver.close();
    }
    @Test
    public void test_Login_Correct(){
        Assertions.assertEquals("Madison Island",homePage.getPageName());
        customerLoginPage=homePage.loginPage();
        Assertions.assertEquals("Customer Login",customerLoginPage.getPageName());
        myAccountPage=customerLoginPage.loginOK("Ejercicio1@ppss.com","Ejercicio1");
        Assertions.assertEquals("My Account",myAccountPage.getPageName());
    }
    @Test
    public void test_Login_Incorrect(){
        Assertions.assertEquals("Madison Island",homePage.getPageName());
        customerLoginPage=homePage.loginPage();
        Assertions.assertEquals("Customer Login",customerLoginPage.getPageName());
        Assertions.assertEquals("Invalid login or password.",customerLoginPage.loginFail("Ejercicio1@ppss.com","aaaaaahhhhhgggggg"));
    }
}

package Ejercicio1.sinPageObject;

import net.jodah.failsafe.internal.util.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class TestCreateAccount {

    private WebDriver driver;
    @BeforeEach
    public void start(){
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://demo-store.seleniumacademy.com");
    }

    @AfterEach
    public void cerrarNavegador(){
        driver.close();
    }

    @Tag("OnlyOnce")
    @Test
    public void createAccount(){

        //1 Veri camos que el tulo de la página de inicio es el correcto
        Assertions.assertEquals("Madison Island",driver.getTitle());

        //2 Seleccionamos Account, y a continuación seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();//acount
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();//login

        //3 Veri camos que el tulo de la página es el correcto
        Assertions.assertEquals("Customer Login",driver.getTitle());

        //4. Seleccionamos el botón "Create Account"
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        //5. Verificamos que estamos en la página correcta
        Assertions.assertEquals("Create New Customer Account",driver.getTitle());

        //6. Rellenamos los campos con los datos de la cuenta y enviamos formulario
        driver.findElement(By.name("firstname")).sendKeys("Ejercicio1");
        driver.findElement(By.cssSelector("input[name='lastname'")).sendKeys("Ejercicio1");
        driver.findElement(By.xpath("//*[@id=\"email_address\"]")).sendKeys("Ejercicio1@ppss.com");
        driver.findElement(By.id("password")).sendKeys("Ejercicio1");
        driver.findElement(By.cssSelector("button[title='Register']")).click();

        //7 Veri camos que nos aparece el mensaje "This is required eld" debajo del campo que hemos dejado vacío
        Assertions.assertEquals("This is a required field.",driver.findElement(By.xpath("//*[@id=\"advice-required-entry-confirmation\"]")).getText());

        //8 Rellenamos el campo que nos falta y volvemos a enviar los datos del formulario.
        driver.findElement(By.id("confirmation")).sendKeys("Ejercicio1");
        driver.findElement(By.cssSelector("form#form-validate")).submit();

        //9 Veri camos que estamos en la página correcta usando su titulo
        Assertions.assertEquals("My Account",driver.getTitle());




    }
}

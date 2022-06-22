package Ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class TestLogin {
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
    @Test
    public void loginOK(){

        //1. Verifcamos que el tulo de la página de inicio es el correcto
        Assertions.assertEquals("Madison Island",driver.getTitle());

        //2. Seleccionamos Account, y a continuacion seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();//acount
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();//login

        //3. Veri camos que el tulo de la página es el correcto("Customer Login")
        Assertions.assertEquals("Customer Login",driver.getTitle());

        //4. Rellenamos el campo email con el email de la cuenta que hemos creado en el driver createAccount() y enviamos el formulario
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("Ejercicio1@ppss.com");
        driver.findElement(By.cssSelector("form#login-form")).submit();

        //5. Veri camos que nos aparece el mensaje "This is arequired eld" debajo del campo que hemos dejado vacío
        Assertions.assertEquals("This is a required field.",driver.findElement(By.cssSelector("div#advice-required-entry-pass")).getText());
        driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("Ejercicio1");
        driver.findElement(By.cssSelector("form#login-form")).submit();

        //7. Veri camos que estamos en la página correcta usando su tulo ("My Account").
        Assertions.assertEquals("My Account",driver.getTitle());
    }

    @Test
    public void loginFailed(){
        //1. Verifcamos que el tulo de la página de inicio es el correcto
        Assertions.assertEquals("Madison Island",driver.getTitle());

        //2. Seleccionamos Account, y a continuacion seleccionamos el hiperenlace Login
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();//acount
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();//login

        //3. Veri camos que el tulo de la página es el correcto("Customer Login")
        Assertions.assertEquals("Customer Login",driver.getTitle());

        //4. Rellenamos el campo email con el email de la cuenta que hemos creado en el driver createAccount() y enviamos el formulario
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("Ejercicio1@ppss.com");
        driver.findElement(By.cssSelector("form#login-form")).submit();
        driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("aaaaaahhhhhgggggg");
        driver.findElement(By.cssSelector("form#login-form")).submit();

        //Veri camos que nos aparece el mensaje "Invalid login or password
        Assertions.assertEquals("Invalid login or password.",driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li/ul/li/span")).getText());

    }

}

package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CustomerLoginPage {
    private WebDriver driver;
    private WebElement nombre;
    private WebElement contraseña;
    private WebElement enviar;
    private  WebElement error;

    public CustomerLoginPage(WebDriver d) {
        this.driver = d;
        nombre=driver.findElement(By.xpath("//*[@id=\"email\"]"));
        contraseña=driver.findElement(By.xpath("//*[@id=\"pass\"]"));
        enviar=driver.findElement(By.cssSelector("form#login-form"));

    }
    public MyAccountPage loginOK(String user,String pass){
        nombre.sendKeys(user);
        contraseña.sendKeys(pass);
        enviar.submit();
        return new MyAccountPage(driver);
    }
    public String loginFail(String user,String pass){
        nombre.sendKeys(user);
        contraseña.sendKeys(pass);
        enviar.submit();
        error=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/div[2]/div/div/div[2]/ul/li/ul/li/span"));
        return error.getText();
    }
    public String getPageName(){
        return driver.getTitle();
    }
}

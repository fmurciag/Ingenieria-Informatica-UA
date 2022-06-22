package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private WebDriver driver;
    private WebElement acount;
    private WebElement login;
    public HomePage(WebDriver d){
        driver=d;
        driver.get("http://demo-store.seleniumacademy.com");
        acount=driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a"));
    }
    public CustomerLoginPage loginPage(){
        acount.click();
        login= driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a"));
        login.click();
        return new CustomerLoginPage(driver);
    }
    public String getPageName(){
        return driver.getTitle();
    }
}

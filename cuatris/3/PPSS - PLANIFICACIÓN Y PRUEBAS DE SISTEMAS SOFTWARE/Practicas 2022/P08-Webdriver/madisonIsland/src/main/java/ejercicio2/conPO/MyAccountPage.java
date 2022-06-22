package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyAccountPage {
    private WebDriver driver;


    public MyAccountPage(WebDriver d) {
        this.driver = d;
    }
    public String getPageName(){
        return driver.getTitle();
    }
}

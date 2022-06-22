package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {
    WebDriver driver;

    @FindBy(linkText = "ACCESORIES")
    WebElement accesories;
    @FindBy(linkText = "Shoes")
    WebElement shoes;



    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public ShoesPage accessShoesPage() {
        Actions builder = new Actions(driver);
        builder.moveToElement(accesories);
        builder.perform();
        shoes.click();

        return PageFactory.initElements(driver, ShoesPage.class);
    }

    public String getTile(){
        return driver.getTitle();
    }
}

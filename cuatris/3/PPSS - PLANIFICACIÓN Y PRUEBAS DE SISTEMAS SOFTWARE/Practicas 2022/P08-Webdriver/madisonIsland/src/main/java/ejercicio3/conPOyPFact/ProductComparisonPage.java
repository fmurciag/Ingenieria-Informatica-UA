package ejercicio3.conPOyPFact;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductComparisonPage {
    public WebDriver driver;
    public String myHandleId;
    public String myHandleIdFrom;

    @FindBy(css = "Button[title='Close Window']")
    WebElement buttonClose;

    public ProductComparisonPage(WebDriver driver) {
        this.driver = driver;
    }

    public ShoesPage close(){
        buttonClose.click();
        driver.switchTo().window(myHandleIdFrom);
        return PageFactory.initElements(driver,ShoesPage.class);
    }

    public String getTile(){
        return driver.getTitle();
    }

}
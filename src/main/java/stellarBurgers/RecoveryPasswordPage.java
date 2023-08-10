package stellarBurgers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoveryPasswordPage {
    WebDriver driver;
    private final By logInButton=By.linkText("Войти");
    public RecoveryPasswordPage(WebDriver driver){
        this.driver=driver;
    }
    @Step("Нажатие на кнопку Войти")
    public void clickLogInButton(){
        driver.findElement(logInButton).click();
    }


}

package stellarBurgers;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Getter
public class UserInformationPage {
    private WebDriver driver;
    private final By profile=By.xpath(".//a[@href='/account/profile']");
    private final By exitButton=By.xpath(".//ul[@class='Account_list__3KQQf mb-20']//button[text()='Выход']");
    private final By loginValue=By.xpath(".//div[@class='input__container']//label[text()='Логин']/parent::div/input");
    public UserInformationPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Ждём пока загрузится окно с личной информацией")
    public void waitForLoadingPersonalInformation(){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(profile));

    }
    @Step("Получение value поля логин")
    public String getLoginValue(){
        return  driver.findElement(loginValue).getAttribute("value");
    }
    @Step("Нажатие на кнопку ВЫХОД")
    public void clickExitButton(){
        driver.findElement(exitButton).click();
    }
}

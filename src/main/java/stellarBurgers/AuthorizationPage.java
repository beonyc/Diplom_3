package stellarBurgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
@Getter
public class AuthorizationPage {
    WebDriver driver;
    private final By bottomText=By.xpath(".//div//p[contains(*,'Зарегистрироваться')]");
    private final By registrationButton = By.linkText("Зарегистрироваться");
    private final By recoveryPasswordButton = By.linkText("Восстановить пароль");
    private final By passwordErrorMessage = By.xpath(".//p[text()='Некорректный пароль']");
    private final By logInButton = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//button[text()='Войти']");
    private final By email=By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//label[text()='Email']/parent::*//input");
    private final By password=By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//label[text()='Пароль']/parent::*//input");

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Вход в аккаунт")
    public void logInToAccount(String emailValue,String passwordlValue){
        setEmail(emailValue);
        setPassword(passwordlValue);
        clickLogInButton();
    }

    @Step("Заполнения поля email")
    public void setEmail(String emailValue){
        driver.findElement(email).sendKeys(emailValue);
    }
    @Step("Заполнения поля password")
    public void setPassword(String passwordlValue){
        driver.findElement(password).sendKeys(passwordlValue);
    }
    @Step("Нажатие на кнопку Войти")
    public void clickLogInButton(){
        driver.findElement(logInButton).click();
    }
    @Step("Нажатие на кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }
    @Step("Нажатие на кнопку Восстановить пароль")
    public void clickRecoveryPasswordButton() {
        driver.findElement(recoveryPasswordButton).click();
    }

    @Step("Ждём пока появится кнопка Войти в окне авторизации ")
    @Description("В основном метод используется для проверки, что мы находимся на странице авторизации")
    public void waitForAuthorizationFormIsVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(bottomText));
    }

}

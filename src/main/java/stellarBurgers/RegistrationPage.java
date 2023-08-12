package stellarBurgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By nameInput = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//label[text()='Имя']/parent::*/input");
    private final By emailInput = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//label[text()='Email']/parent::*/input");
    private final By passwordInput = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//label[text()='Пароль']/parent::*/input");
    private final By registrationButton = By.xpath(".//form[@class='Auth_form__3qKeq mb-20']//button[text()='Зарегистрироваться']");
    private final By logInButton = By.linkText("Войти");

    @Step("Заполнениe поля Имя")
    public void setNameValue(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }
    @Step("Заполнениe поля email")
    public void setEmailValue(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }
    @Step("Заполнениe поля password")
    public void setPasswordInput(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }
    @Step("Нажатие на кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }
    @Step("Нажатие на кнопку Войти")
    public void clickLogInButton() {
        driver.findElement(logInButton).click();
    }
    @Step("Полный процесс регистрации")
    @Description("Заполнениe поля Имя, email, password и на кнопку Зарегистрироваться ")
    public void registerNewUser(String name, String email, String password) {
        setNameValue(name);
        setEmailValue(email);
        setPasswordInput(password);
        clickRegistrationButton();
    }


}

package org.example;

import configuration.WebDriverBrowsers;
import settings.User;
import api.methods.UserMethods;
import settings.Components;
import stellarBurgers.AuthorizationPage;
import stellarBurgers.MainPage;
import stellarBurgers.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;


@DisplayName("Создание нового пользователя")
public class CreateNewUserTests {
    private boolean isDeleteUser = true;
    private WebDriver driver;
    private MainPage mainPage;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private Components components;
    private User user;
    private UserMethods userApiMethods = new UserMethods();
    private WebDriverBrowsers webDriverBrowsers=new WebDriverBrowsers();


    @Before
    public void setUp() {
        driver= webDriverBrowsers.get();

        mainPage = new MainPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        registrationPage = new RegistrationPage(driver);
        components = new Components();
        user = new User(components.getEmail(), components.getPassword());

    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Создание нового пользователя со всеми валидными данными и проверка что после успешной регистрации мы попадаем на страницу с URL:https://stellarburgers.nomoreparties.site/login ")
    public void createNewUserWithValidDataTest() {
        mainPage.clickPersonalAccountButton();
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        authorizationPage.waitForAuthorizationFormIsVisible();

        Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
    }

    @Test
    @DisplayName("Создание нового пользователя с неправильным паролем")
    @Description("Создание нового пользователя с невалидным паролем длиной 5 символов и проверка что появилась ошибка \"Некорректный пароль\"")
    public void createNewUserWithPasswordLessThan6CharactersTest() {
        isDeleteUser = false;
        mainPage.clickPersonalAccountButton();
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getInvalidPasswordWith5Characters());

        Assert.assertTrue(driver.findElement(authorizationPage.getPasswordErrorMessage()).isDisplayed());
    }

    @Test
    @DisplayName("Создание нового пользователя с паролем в 6 символов")
    @Description("Создание нового пользователя с паролем в 6 символов для проверки граничных значений" +
            "и что после успешной регистрации мы попадаем на страницу с URL:https://stellarburgers.nomoreparties.site/login")
    public void createNewUserWithPasswordLengthEquals6CharactersTest() {
        mainPage.clickPersonalAccountButton();
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPasswordWithLength6());
        authorizationPage.waitForAuthorizationFormIsVisible();
        
        user.setPassword(components.getPasswordWithLength6());

        Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
    }

    @After
    @Description("Проверка что пользователь успешно удалился")
    public void tearDown() {
        driver.quit();
        if (isDeleteUser) {
            userApiMethods.deleteUser(user)
                    .then()
                    .statusCode(SC_ACCEPTED)
                    .and()
                    .assertThat().body("message", equalTo("User successfully removed"));
        }

    }

}
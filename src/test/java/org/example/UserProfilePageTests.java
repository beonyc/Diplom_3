package org.example;

import api.methods.UserMethods;
import configuration.WebDriverBrowsers;
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
import settings.Components;
import settings.User;
import stellarBurgers.AuthorizationPage;
import stellarBurgers.MainPage;
import stellarBurgers.RegistrationPage;
import stellarBurgers.UserInformationPage;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Функциональность страницы профиля пользователя")
public class UserProfilePageTests {
    WebDriver driver;
    private UserMethods userApiMethods = new UserMethods();
    private RegistrationPage registrationPage;
    private MainPage mainPage;
    private AuthorizationPage authorizationPage;
    private Components components;
    private UserInformationPage userInformationPage;

    private User user;
    private WebDriverBrowsers webDriverBrowsers=new WebDriverBrowsers();


    @Before
    public void setUp() {
        driver= webDriverBrowsers.get();

        registrationPage = new RegistrationPage(driver);
        userInformationPage = new UserInformationPage(driver);
        mainPage = new MainPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        components = new Components();
        user = new User(components.getEmail(), components.getPassword());
    }
    @Test
    @DisplayName("Вход из аккаунта по кнопке «Выход» на странице профиля")
    @Description("Проверка, что при выходе из аккаунта мы перемещаемся на страницу авторизации")
    public void checkIfLogInButtonOnManePageWorksTest() {
        mainPage.clickLogInButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();
        //Авторизация
        mainPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickPersonalAccountButton();
        userInformationPage.waitForLoadingPersonalInformation();

        userInformationPage.clickExitButton();
        authorizationPage.waitForAuthorizationFormIsVisible();

        //Проверка, что после успешного выхода, мы перемещаемся на страницу авторизации
        Assert.assertTrue(driver.getCurrentUrl().endsWith("login"));


    }

    @After
    @Description("Проверка что пользователь успешно удалился")
    public void tearDown() {
        driver.quit();
        userApiMethods.deleteUser(user)
                .then()
                .statusCode(SC_ACCEPTED)
                .and()
                .assertThat().body("message", equalTo("User successfully removed"));


    }
}

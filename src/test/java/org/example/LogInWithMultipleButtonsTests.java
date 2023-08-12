package org.example;

import api.methods.UserMethods;
import configuration.WebDriverBrowsers;
import org.junit.After;
import settings.Components;
import settings.User;
import stellarBurgers.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Вход в аккаунт разными способами")
public class LogInWithMultipleButtonsTests {
    private WebDriver driver;
    private MainPage mainPage;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private RecoveryPasswordPage recoveryPasswordPage;
    private Components components;
    private User user;
    private UserMethods userApiMethods = new UserMethods();
    private UserInformationPage userInformationPage;
    private WebDriverBrowsers webDriverBrowsers=new WebDriverBrowsers();


    @Before
    public void setUp() {
        driver= webDriverBrowsers.get();

        mainPage = new MainPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        registrationPage = new RegistrationPage(driver);
        recoveryPasswordPage = new RecoveryPasswordPage(driver);
        userInformationPage=new UserInformationPage(driver);
        components = new Components();
        user = new User(components.getEmail(), components.getPassword());

    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    @Description("Проверка, что при входе при нажатии на конку «Войти в аккаунт» на главной странице, нас переносит на страницу авторизации без ошибок")
    public void checkIfLogInButtonOnManePageWorksTest() {
        mainPage.clickLogInButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();

        mainPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickPersonalAccountButton();
        userInformationPage.waitForLoadingPersonalInformation();
        //Проверка, что при нажатии на Личный кабинет, мы переходим на адрес заканчивающийся на /account/profile, что подтверждает, что мы успешно вошли в аккаунт
        //У неавторизованного пользователя адрес заканчивался бы на /login
        Assert.assertTrue(driver.getCurrentUrl().endsWith("account/profile"));


    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    @Description("Проверка, что при входе при нажатии на конку «Личный кабинет» на главной странице, нас переносит на страницу авторизации без ошибок")
    public void checkIfPersonalAccountButtonOnManePageWorksTest() {
        mainPage.clickPersonalAccountButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();

        mainPage.clickPersonalAccountButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickPersonalAccountButton();
        userInformationPage.waitForLoadingPersonalInformation();
        //Проверка, что при нажатии на Личный кабинет, мы переходим на адрес заканчивающийся на /account/profile, что подтверждает, что мы успешно вошли в аккаунт
        //У неавторизованного пользователя адрес заканчивался бы на /login
        Assert.assertTrue(driver.getCurrentUrl().endsWith("account/profile"));

    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка, что при нажатии на конку «Войти» на странице регистрации, нас переносит на страницу авторизации без ошибок")
    public void checkIfLogInButtonOnRegistrationPageWorksTest() {

        mainPage.clickPersonalAccountButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();


        mainPage.clickPersonalAccountButton();
        authorizationPage.clickRegistrationButton();

        registrationPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickPersonalAccountButton();
        userInformationPage.waitForLoadingPersonalInformation();
        //Проверка, что при нажатии на Личный кабинет, мы переходим на адрес заканчивающийся на /account/profile, что подтверждает, что мы успешно вошли в аккаунт
        //У неавторизованного пользователя адрес заканчивался бы на /login
        Assert.assertTrue(driver.getCurrentUrl().endsWith("account/profile"));

    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка, что при нажатии на конку «Войти» на странице восстановления пароля, нас переносит на страницу авторизации без ошибок")
    public void checkIfLogInButtonOnPasswordRecoveryPageWorksTest() {
        mainPage.clickPersonalAccountButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();

        mainPage.clickPersonalAccountButton();
        authorizationPage.clickRecoveryPasswordButton();
        recoveryPasswordPage.clickLogInButton();

        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickPersonalAccountButton();
        userInformationPage.waitForLoadingPersonalInformation();
        //Проверка, что при нажатии на Личный кабинет, мы переходим на адрес заканчивающийся на /account/profile, что подтверждает, что мы успешно вошли в аккаунт
        //У неавторизованного пользователя адрес заканчивался бы на /login
        Assert.assertTrue(driver.getCurrentUrl().endsWith("account/profile"));

    }

    @After
    @Description("Проверка что пользователь успешно удалился")
    public void tearDown() {
        driver.quit();
        userApiMethods.deleteUser(user);}

}

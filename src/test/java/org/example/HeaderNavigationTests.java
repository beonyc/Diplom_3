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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import settings.Components;
import settings.User;
import stellarBurgers.*;
import org.openqa.selenium.WebDriver;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Перемещение по шапке сайта")
public class HeaderNavigationTests {
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
    @DisplayName("Переход по клику на «Личный кабинет».")
    @Description("Метод проверяет, появится ли личная информация о пользователе, после нажатия на кнопку «Личный кабинет»")
    public void checkIfPersonalAccountButtonWorksCorrect() {
        mainPage.clickLogInButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();
        //Авторизация
        mainPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();
        //Нажатие на «Личный кабинет»
        mainPage.clickPersonalAccountButton();
        userInformationPage.waitForLoadingPersonalInformation();
        //проверка, что при нажатии на «Личный кабинет» мы попали на страницу с информацией пользователя,
        // проверка логина, под которым мы авторизовались, с логином, который находится на странице
        String expected= components.getEmail();
        String actual=userInformationPage.getLoginValue();
        Assert.assertEquals(expected,actual);

    }
    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    @Description("Метод проверяет, изменится ли название класса конструктора на active, после нажатия на кнопку конструктор")
    public void checkIfConstructorButtonWorksCorrect() {
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
        //Нажатие на кнопку конструктора
        mainPage.clickConstructorButton();
        //Проверка, что в классе конструктора появилось есть слово active
        Assert.assertTrue(mainPage.getConstructorButtonClassName().contains("active"));


    }
    @Test
    @DisplayName("Нажатие на кнопку логотипа из личного кабинета")
    @Description("Метод проверяет, изменится ли название класса логотипа на active, после нажатия на кнопку логотипа сайта")
    public void checkIfLogoButtonWorksCorrect() {
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
        //Нажатие на логотип сайта
        mainPage.goToMainPage();
        //Проверка, что в классе логотипа появилось есть слово active
        Assert.assertTrue(mainPage.getLogoButtonLinkClassName().contains("active"));


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

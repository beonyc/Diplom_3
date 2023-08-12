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
import org.openqa.selenium.By;
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

@DisplayName("Перемещение по категориям в области сборки бургера")
public class MainPageBodyNavigationTests {
    WebDriver driver;
    private UserMethods userApiMethods = new UserMethods();
    private RegistrationPage registrationPage;
    private MainPage mainPage;
    private AuthorizationPage authorizationPage;
    private Components components;
    private UserInformationPage userInformationPage;
    private final String ACTIVE_BUTTON_CLASS_NAME="tab_tab_type_current__2BEPc";

    private User user;
    private WebDriverBrowsers webDriverBrowsers = new WebDriverBrowsers();

    @Before
    public void setUp() {
        driver = webDriverBrowsers.get();

        registrationPage = new RegistrationPage(driver);
        userInformationPage = new UserInformationPage(driver);
        mainPage = new MainPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        components = new Components();
        user = new User(components.getEmail(), components.getPassword());
    }

    @Test
    @DisplayName("Нажатие на кнопку \"Булки\"")
    @Description("Проверка, что при нажатии на кнопку \"Булки\", выберется кнопка \"Булки\"")
    public void checkIfBunButtonWorksTest() {
        mainPage.clickLogInButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();
        //Авторизация
        mainPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        //Так как кнопка Булки у нас выбрана автоматически, нужно перейти на нее заново
        mainPage.clickSouseButton();
        mainPage.clickBunButton();
        // Если кнопка выбрана, то в классе добавляется название "tab_tab_type_current__2BEPc", поэтому мы проверяем, есть ли в классе это название
        Assert.assertTrue(mainPage.getBunButtonClassName().contains(ACTIVE_BUTTON_CLASS_NAME));
    }

    @Test
    @DisplayName("Нажатие на кнопку \"Соусы\"")
    @Description("Проверка, что при нажатии на кнопку \"Соусы\", выберется кнопка \"Соусы\"")
    public void checkIfSouseButtonWorksTest() {
        mainPage.clickLogInButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();
        //Авторизация
        mainPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickSouseButton();

        // Если кнопка выбрана, то в классе добавляется название "tab_tab_type_current__2BEPc", поэтому мы проверяем, есть ли в классе это название
        Assert.assertTrue(mainPage.getSouseButtonClassName().contains(ACTIVE_BUTTON_CLASS_NAME));
    }

    @Test
    @DisplayName("Нажатие на кнопку \"Начинки\"")
    @Description("Проверка, что при нажатии на кнопку \"Начинки\", выберется кнопка \"Начинки\"")
    public void checkIfFillingButtonWorksTest() {
        mainPage.clickLogInButton();
        //Для того чтобы войти, сначала нужно создать пользователя
        authorizationPage.clickRegistrationButton();
        registrationPage.registerNewUser(components.getName(), components.getEmail(), components.getPassword());
        mainPage.goToMainPage();
        //Авторизация
        mainPage.clickLogInButton();
        authorizationPage.logInToAccount(components.getEmail(), components.getPassword());
        mainPage.waitForLoadingMainPage();

        mainPage.clickFillingButton();

        // Если кнопка выбрана, то в классе добавляется название "tab_tab_type_current__2BEPc", поэтому мы проверяем, есть ли в классе это название
        Assert.assertTrue(mainPage.getFillingButtonClassName().contains(ACTIVE_BUTTON_CLASS_NAME));
    }

    @After
    @Description("Проверка что пользователь успешно удалился")
    public void tearDown() {
        driver.quit();
        userApiMethods.deleteUser(user);}
}

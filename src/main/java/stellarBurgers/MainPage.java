package stellarBurgers;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainPage {
    private WebDriver driver;

    //Локатор кнопки "Личный кабинет"
    private final By personalAccountButton = By.xpath(".//header[@class='AppHeader_header__X9aJA pb-4 pt-4']//p[text()='Личный Кабинет']/parent::a");
    //Локатор кнопки "Войти в аккаунт"
    private final By logInButton = By.xpath(".//div[@class='BurgerConstructor_basket__container__2fUl3 mt-10']//button[text()='Войти в аккаунт']");
    private final By logoButton = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");
    private final By logoButtonLinkClassName = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']/a");
    private final By constructorButton = By.xpath(".//nav//a[contains(@class,'AppHeader_header__link__3D_hX')]//p[text()='Конструктор']");
    private final By constructorButtonLinkClassName = By.xpath(".//nav//a[contains(@class,'AppHeader_header__link__3D_hX')]//p[text()='Конструктор']/parent::a");
    private final By makeBurgerArea = By.xpath(".//section[@class='BurgerConstructor_basket__29Cd7 mt-25 ']");
    private final By bunButton=By.xpath(".//span[text()='Булки']");
    private final By bunButtonClassName=By.xpath(".//span[text()='Булки']/parent::div");
    private final By souseButton=By.xpath(".//span[text()='Соусы']");
    private final By souseButtonClassName=By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingButton=By.xpath(".//span[text()='Начинки']");
    private final By fillingButtonClassName=By.xpath(".//span[text()='Начинки']/parent::div");


    @Step("Нажатие на кнопку \"Булки\" на главной странице")
    public void clickBunButton() {
        driver.findElement(bunButton).click();
    }
    @Step("Нажатие на кнопку \"Соусы\" на главной странице")
    public void clickSouseButton() {
        driver.findElement(souseButton).click();
    }
    @Step("Нажатие на кнопку \"Начинки\" на главной странице")
    public void clickFillingButton() {
        driver.findElement(fillingButton).click();
    }
    @Step("Получение класса кнопки \"Булки\"")
    public String getBunButtonClassName(){
        return driver.findElement(bunButtonClassName).getAttribute("class");
    }
    @Step("Получение класса кнопки \"Соусы\"")
    public String getSouseButtonClassName(){
        return driver.findElement(souseButtonClassName).getAttribute("class");
    }
    @Step("Получение класса кнопки \"Начинки\"")
    public String getFillingButtonClassName(){
        return driver.findElement(fillingButtonClassName).getAttribute("class");
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoadingMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(makeBurgerArea));
    }

    @Step("Нажатие на лого сайта в шапке сайта для перемещения на главную страницу")
    public void goToMainPage() {
        driver.findElement(logoButton).click();
    }

    @Step("Нажатие на кнопку \"Личный Кабинет\" на главной странице")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Нажатие на кнопку \"Войти в аккаунт\" на главной странице")
    public void clickLogInButton() {
        driver.findElement(logInButton).click();
    }

    @Step("Нажатие на кнопку \"Конструктор\" на главной странице")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Получение название класса конструктора")
    public String getConstructorButtonClassName() {
        return driver.findElement(constructorButtonLinkClassName).getAttribute("class");
    }
    @Step("Получение название класса логотипа")
    public String getLogoButtonLinkClassName() {
        return driver.findElement(logoButtonLinkClassName).getAttribute("class");
    }


}

package configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverBrowsers {
    private WebDriver driver;
    private ChromeOptions options;
    private String browserName = "yandex";
    private String baseUrl = "https://stellarburgers.nomoreparties.site/";

    public WebDriver get() {
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                options.addArguments("--headless", "--no-sandbox");
                driver = new ChromeDriver();
                driver.get(baseUrl);

                break;
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "C:/Users/yrik0/chromedriver_win32/chromedriver.exe");
                options = new ChromeOptions();
                options.setBinary("C:/Users/yrik0/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
                options.addArguments("--headless", "--no-sandbox");
                driver = new ChromeDriver(options);
                driver.get(baseUrl);
                break;
            default:
                throw new RuntimeException("Browser is not detected");
        }
        return driver;
    }
}

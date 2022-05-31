import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.log.Log;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Chapter7_ConsoleLogs {

    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().browserVersion("84.0.4147.30").setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    @Test
    public void displayChromeVersions(){
        System.out.println(WebDriverManager.chromedriver().getDriverVersions());
    }

    @Test
    public void viewBrowserConsoleLogs(){
        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("----------");
            System.out.println("Level: " + logEntry.getLevel());
            System.out.println("Text: " + logEntry.getText());
            System.out.println("Broken url: " + logEntry.getUrl());
        });

        driver.get("https://the-internet.herokuapp.com/broken_images");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

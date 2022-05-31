import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.ConnectionType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

public class Chapter7_NetworkConditions {
    private ChromeDriver driver;
    private DevTools devTools;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        devTools = driver.getDevTools();
    }

    @Test
    public void enableSlowRexJonesII(){
        devTools.createSession();
        devTools.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
        devTools.send(Network.emulateNetworkConditions(
                false,
                150,
                2500,
                2000,
                Optional.of(ConnectionType.CELLULAR3G)
        ));
        driver.get("https://RexJones2.com");
        System.out.println("Enable slow network: " + driver.getTitle());
    }

    @Test
    public void disableSlowRexJonesII(){
        driver.get("https://RexJones2.com");
        System.out.println("Disable slow network: " + driver.getTitle());
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}

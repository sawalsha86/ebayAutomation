package TestBaseClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

public class TestBase {
    public static WebDriver driver;
    public static Properties prop;


    public TestBase() throws IOException {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(("C:\\Users\\ssawalakhe\\Downloads\\TestAutomationTest\\src\\main\\java\\Configuration\\Config.properties"));
            prop.load(ip);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void initialization(){
        String browserName = prop.getProperty("browser");
        if(browserName.equals("Chrome")){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\ssawalakhe\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(20, java.util.concurrent.TimeUnit.SECONDS); //Page load timeout
        driver.manage().timeouts().implicitlyWait(20, java.util.concurrent.TimeUnit.SECONDS);
        driver.get(prop.getProperty("url")); 




    }





    }




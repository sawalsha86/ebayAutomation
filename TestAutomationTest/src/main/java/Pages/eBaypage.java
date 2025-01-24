package Pages;

import TestBaseClass.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.io.IOException;
import java.util.Set;

public class eBaypage extends TestBase{


    @FindBy(xpath = "//input[@title='Search']")
    WebElement  searchBox;

    @FindBy(xpath="//button[@value='Search']")
    WebElement searchButton;

    @FindBy(xpath="//ul[@class='srp-results srp-list clearfix']//div[@class='s-item__info clearfix']//a[1]//span[@aria-level='3']")
    WebElement selectfirstbook;

    @FindBy(xpath="//span[contains(text(),'Add to cart')]")
    WebElement addtoCart;

    public eBaypage() throws IOException {
        super();
        PageFactory.initElements(driver, this);  //Initialize PageFactory
    }


    public void searchBook() {
        searchBox.sendKeys("book");
        searchButton.click();

    }

    public void addBook() {
        selectfirstbook.click();
        // Get the current window handle (parent tab)
        String parentWindow = driver.getWindowHandle();

        // Get all window handles
        Set<String> allWindows = driver.getWindowHandles();

        // Switch to the new tab
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Add to cart')]")));
        addtoCart.click();

    }

    public String verifycartnumber(){
        WebElement cart = driver.findElement(By.xpath("//span[@class='gh-cart__icon']"));
        String cartnum= cart.getText();
        return cartnum;
    }

    public String apiCall(){
        //1. Verify	Send the GET request for code 200
        RestAssured.baseURI = "https://api.coindesk.com/v1/bpi/currentprice.json";

        Response response = RestAssured
                .given()
                .get();
        String resp=  response.getStatusCode()+"";
        System.out.println("API response: "+resp);


        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        // 2. a. Verify there are 3 BPIs: USD, GBP, EUR
        JSONObject bpi = jsonResponse.getJSONObject("bpi");
        Assert.assertTrue(bpi.has("USD"));
        Assert.assertTrue(bpi.has("GBP"));
        Assert.assertTrue(bpi.has("EUR"));

        System.out.println("BPI contains USD, GBP, and EUR.");



        // 2. b. Verify GBP 'description' equals 'British Pound Sterling'
        JSONObject gbp = bpi.getJSONObject("GBP");
        String gbpDescription = gbp.getString("description");
        Assert.assertEquals("British Pound Sterling", gbpDescription);

        System.out.println("GBP description verified: " + gbpDescription);
        return resp;



    }




}

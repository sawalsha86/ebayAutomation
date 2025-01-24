package TestCases;
import Pages.eBaypage;
import TestBaseClass.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class eBaypageTest extends TestBase{

    eBaypage loginPage;
    public eBaypageTest() throws IOException {
        super();
    }

    @BeforeMethod
    public void setUp() throws IOException {
        initialization();
        loginPage = new eBaypage();
    }

    @Test(priority = 1)
    public void verifyCartItemNumberTest() throws Exception {

        loginPage.searchBook();

        Thread.sleep(5000);
        loginPage.addBook();
        Thread.sleep(15000);
        String resultcartnum= loginPage.verifycartnumber();
        Assert.assertEquals("1",resultcartnum);
        System.out.println("Cart number is: "+resultcartnum);
    }

    @Test(priority = 2)
        public void verifyapi() throws Exception {
        String resultresponse = loginPage.apiCall();
        Assert.assertEquals("200", resultresponse);


    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    }











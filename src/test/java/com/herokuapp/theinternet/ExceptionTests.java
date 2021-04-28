package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class ExceptionTests {

  private WebDriver driver;

  @BeforeMethod(alwaysRun = true)
  @Parameters({"browser"})
  private void setup(@Optional("chrome") String browser){
    // create driver
    switch (browser){
      case "chrome":
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        break;
      case "firefox":
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        break;
      default:
         System.out.println("Do not know how to start? "+browser+", starting chrome instead");
         System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
         driver = new ChromeDriver();
    }

    // maximize browser window
    driver.manage().window().maximize();

//    implicit wait
//    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }


  @AfterMethod(alwaysRun = true)
  private void tearDown() {
    //close browser
    driver.quit();
  }

  @Test(priority=1, groups = { "notvisible", "smokeTests" })
  public void notVisibleTest() {
    // open test page
    String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
    driver.get(url);

    // click login button
    WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
    startButton.click();
    // verifications:
    // finish message
    WebElement finishMessage = driver.findElement(By.xpath("//div[@id='finish']/h4"));

    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.visibilityOf(finishMessage));

    Assert.assertTrue(finishMessage.getText().contains("Hello World!"));



  }
  @Test(priority=3,groups = { "notvisible", "smokeTests" })
  public void notSuchElementTest() {
    // open test page
    String url = "http://the-internet.herokuapp.com/dynamic_loading/2";
    driver.get(url);

    // click login button
    WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
    startButton.click();
    // verifications:
    // finish message

    WebDriverWait wait = new WebDriverWait(driver, 10);
    Assert.assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@id='finish']/h4"),"Hello World!")));


  }
  @Test(priority=2,groups = { "notvisible", "smokeTests" })
  public void timeoutTest() {
    // open test page
    String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
    driver.get(url);

    // click login button
    WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
    startButton.click();
    // verifications:
    // finish message
    WebElement finishMessage = driver.findElement(By.xpath("//div[@id='finish']/h4"));

    WebDriverWait wait = new WebDriverWait(driver, 2);
    try {
      wait.until(ExpectedConditions.visibilityOf(finishMessage));
    } catch (TimeoutException e) {
      System.out.println("Exceptcion catched: "+e.getMessage());
      sleep(5000);
    }

    Assert.assertTrue(finishMessage.getText().contains("Hello World!"));



  }


  @Test
  public void staleElement(){
    driver.get("http://the-internet.herokuapp.com/dynamic_controls");

    WebElement checkbox = driver.findElement(By.id("checkbox"));
   WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
    WebDriverWait wait = new WebDriverWait(driver, 10);
    removeButton.click();
//    wait.until(ExpectedConditions.invisibilityOf(checkbox));
//    Assert.assertFalse(checkbox.isDisplayed());
//     Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkbox)));
    Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkbox)));

    WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
    addButton.click();
    checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
    Assert.assertTrue(checkbox.isDisplayed());


  }
  private void sleep(long t) {
    try {
      Thread.sleep(t);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

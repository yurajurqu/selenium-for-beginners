package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

  @Test(priority=1,groups = { "positiveTests", "smokeTests" })
  public void positiveLoginTest() {
    System.out.println("Starting loginTest");
    // create driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    WebDriver driver = new ChromeDriver();
    // open test page
    String url = "http://the-internet.herokuapp.com/login";
    driver.get(url);
    System.out.println("Page is opened.");

    // sleep for 3 seconds
    // sleep(3000);

    // maximize browser window
    driver.manage().window().maximize();
    // enter username
    WebElement username = driver.findElement(By.id("username"));
    username.sendKeys("tomsmith");
    // enter password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("SuperSecretPassword!");
    // click login button
    WebElement loginButton = driver.findElement(By.cssSelector("button"));
    loginButton.click();
    // verifications:
    // new url
    Assert.assertEquals(driver.getCurrentUrl(), "http://the-internet.herokuapp.com/secure");
    // logout button is visible
    WebElement logoutButton = driver.findElement(By.xpath("//a[@href='/logout']"));
    Assert.assertTrue(logoutButton.isDisplayed());
    // successfull login message
    WebElement successMessage = driver.findElement(By.xpath("//div[contains(text(),'You logged into a secure area!')]"));
    String expectedMessage="You logged into a secure area!";
    Assert.assertTrue(successMessage.getText().contains(expectedMessage));
    
    //close browser
    driver.quit();
  }

  @Test(priority=2,groups = { "negativeTests", "smokeTests" })
  @Parameters({"username","password","expectedMessage"})
  public void negativeLoginTest(String user, String pass, String expectedMessage) {
    System.out.println("Starting wongUsenameLoginTest");
    // create driver
    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
    WebDriver driver = new FirefoxDriver();
    // open test page
    String url = "http://the-internet.herokuapp.com/login";
    driver.get(url);
    System.out.println("Page is opened.");

    // sleep for 3 seconds
    // sleep(3000);

    // maximize browser window
    driver.manage().window().maximize();
    // enter username
    WebElement username = driver.findElement(By.id("username"));
    username.sendKeys(user);
    // enter password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys(pass);
    // click login button
    WebElement loginButton = driver.findElement(By.cssSelector("button"));
    loginButton.click();
    // verifications:

    // wrong login message
    WebElement errorMessage = driver.findElement(By.xpath("//div[@id='flash']"));
    Assert.assertTrue(errorMessage.getText().contains(expectedMessage));

    //close browser
    driver.quit();
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

package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTests {

  @Test
  public void wongUsernameLoginTest() {
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
    username.sendKeys("wronguser");
    // enter password
    WebElement password = driver.findElement(By.id("password"));
    password.sendKeys("SuperSecretPassword!");
    // click login button
    WebElement loginButton = driver.findElement(By.cssSelector("button"));
    loginButton.click();
    // verifications:
    
    // wrong login message
    WebElement errorMessage = driver.findElement(By.xpath("//div[@id='flash']"));
    String expectedMessage="Your username is invalid!";
    Assert.assertTrue(errorMessage.getText().contains(expectedMessage));
    
    //close browser
    driver.quit();
  }
  @Test
  public void wongPasswordLoginTest() {
    System.out.println("Starting wongUsenameLoginTest");
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
    password.sendKeys("xxxxx");
    // click login button
    WebElement loginButton = driver.findElement(By.cssSelector("button"));
    loginButton.click();
    // verifications:
    
    // wrong login message
    WebElement errorMessage = driver.findElement(By.xpath("//div[@id='flash']"));
    String expectedMessage="Your password is invalid!";
    Assert.assertTrue(errorMessage.getText().contains(expectedMessage));
    
    //close browser
    driver.quit();
  }
}
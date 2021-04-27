package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

  @Test
  public void loginTest() {
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
    driver.findElement(By.id("username")).sendKeys("tomsmith");
    // enter password
    driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
    // click login button
    driver.findElement(By.cssSelector("button")).sendKeys(Keys.ENTER);
    // verifications:
    // new url
    Assert.assertTrue(driver.getCurrentUrl().equals("http://the-internet.herokuapp.com/secure"));
    // logout button is visible
    Assert.assertNotNull(driver.findElement(By.xpath("//a[@href='/logout']")));
    // successfull login message
    Assert.assertNotNull(driver.findElement(By.xpath("//h2[contains(text(),'Secure Area')]")));
    
    
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

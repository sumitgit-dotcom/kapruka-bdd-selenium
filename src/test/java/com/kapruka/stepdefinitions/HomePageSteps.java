package com.kapruka.stepdefinitions;

import static org.junit.Assert.assertTrue;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePageSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Given("I am on the Kapruka homepage")
    public void i_am_on_the_kapruka_homepage() {
        driver.get("https://www.kapruka.com/");
        wait.until(ExpectedConditions.titleContains("Kapruka"));
    }

    @Then("I should print the homepage result")
    public void i_should_print_the_homepage_result() {
        System.out.println("==============================================");
        System.out.println("KAPRUKA HOMEPAGE - OPEN RESULT");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("Status: PASS - Homepage opened");
        System.out.println("==============================================");
        assertTrue(driver.getCurrentUrl().startsWith("https://www.kapruka.com"));
    }

    @Then("I should see the section {string}")
    public void i_should_see_the_section(String sectionTitle) {
        By locator = By.xpath("//h2[contains(., '" + sectionTitle + "')] | //h3[contains(., '" + sectionTitle + "')]");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
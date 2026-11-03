package com.kapruka.hooks;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.kapruka.factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @Before
    public void beforeScenario(Scenario scenario) {
        driver = DriverFactory.createDriver();
        // Note: We use a 15-second timeout for the wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @After
    public void afterScenario(Scenario scenario) {
        DriverFactory.quitDriver(driver);
        driver = null;
        wait = null;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }
}
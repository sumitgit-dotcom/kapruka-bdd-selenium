package com.kapruka.factory;

import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = resolveBrowser().toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        WebDriver driver;
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    // Some CI environments require no-sandbox for Chrome
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        if (!isHeadless) {
            driver.manage().window().maximize();
        }
        
        System.out.println("[DriverFactory] Started " + browser + " (Headless: " + isHeadless + ")");
        return driver;
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
            System.out.println("[DriverFactory] Browser closed.");
        }
    }

    private static String resolveBrowser() {
        // 1. Check command line argument (-Dbrowser=firefox)
        String fromSystem = System.getProperty("browser");
        if (fromSystem != null && !fromSystem.isBlank()) {
            System.out.println("[DriverFactory] Browser forced from command line: " + fromSystem);
            return fromSystem;
        }

        // 2. Read from config.properties
        try (InputStream in = DriverFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                String fromFile = props.getProperty("browser");
                if (fromFile != null && !fromFile.isBlank()) {
                    System.out.println("[DriverFactory] Browser read from config.properties: " + fromFile);
                    return fromFile;
                }
            }
        } catch (Exception e) {
            System.out.println("[DriverFactory] Error reading config: " + e.getMessage());
        }

        // 3. Fallback
        return "chrome";
    }
}
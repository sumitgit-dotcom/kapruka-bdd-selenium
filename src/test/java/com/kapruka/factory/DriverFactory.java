package com.kapruka.factory;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public final  class DriverFactory {
	private DriverFactory() {
    }

	 public static WebDriver createDriver() {
	        String browser = resolveBrowser().toLowerCase();
	        
	        WebDriver driver;
	        switch (browser) {
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                driver = new FirefoxDriver();
	                break;
	            case "edge":
	                WebDriverManager.edgedriver().setup();
	                driver = new EdgeDriver();
	                break;
	            case "chrome":
	            default:
	                WebDriverManager.chromedriver().setup();
	                driver = new ChromeDriver();
	                break;
	        }

	        driver.manage().window().maximize();
	        System.out.println("[DriverFactory] Started " + browser);
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

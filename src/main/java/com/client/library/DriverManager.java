package com.client.library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    WebDriver driver;
    protected static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public DriverManager(){}

    public static WebDriver getDriver(){
        if (driverThread == null) {
            logger.error("Driver object is not instantiated, createWebDriver() in DriverClass should be called");
            throw new RuntimeException("Driver object is not instantiated, createWebDriver() in DriverClass should be called");
        }
        return driverThread.get();
    }

    public static void setDriver(WebDriver driver){driverThread.set(driver);}

    public void createDriver(Driver driver){
      this.driver =  driver.createDriver();
      setDriver(this.driver);
    }

    public void cleanDriver(){
       if(driverThread.get() != null){
           (driverThread.get()).quit();
       }
    }
}

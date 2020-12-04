package com.client.stepDefinitions;

import com.client.library.DriverClass;
import com.client.library.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);
    DriverManager driverManager = new DriverManager();

    @Before("@ui")
    public void beforeScenario(){
        driverManager.createDriver(new DriverClass());
        logger.info("browser launched");
    }

    @After("@ui")
    public void afterScenario(){
        driverManager.cleanDriver();
        DriverManager.getDriver().quit();
        logger.info("browser closed");
    }
}

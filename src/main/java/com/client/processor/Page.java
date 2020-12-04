package com.client.processor;

import com.client.helpers.AppHelpers;
import com.client.library.DriverManager;
import com.client.library.PropertyReader;
import org.openqa.selenium.WebDriver;

public class Page {

        public WebDriver driver;
        public PropertyReader propertyReader;
        public AppHelpers appHelpers;

        public Page(){
            driver = DriverManager.getDriver();
            propertyReader = new PropertyReader();
            appHelpers = new AppHelpers(driver);
        }
}

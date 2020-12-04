package com.client.modules;

import com.client.library.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LandingPage extends BasePage {

    @FindBy(name = "email")
    public WebElement txtEmail;

    @FindBy(name = "password")
    public WebElement txtPassword;

    @FindBy(xpath = "//span[text()=' Login ']")
    public WebElement btnLogin;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public void loadUrl() {
        driver.get(new PropertyReader().getProperty("url"));
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        assertThat("Task Manager home page is not loaded", driver.getTitle(),
                equalTo("Task Manager"));
    }
}

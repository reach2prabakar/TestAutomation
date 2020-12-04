package com.client.modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ImportantTaskPage extends BasePage{

    @FindBy(xpath = "//mat-toolbar/div[text()='Important Tasks']")
    public WebElement lblImportantTaskHeader;

    @FindAll(
            @FindBy(css = ".home-card.task-card .mat-card-content>span")
    )
    public List<WebElement> lstTaskImportantList;

    public ImportantTaskPage(WebDriver driver) {
        super(driver);
        waitForPage();
    }

    public void waitForPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(lblImportantTaskHeader));
        assertThat("Task Manager - Important Task page is not loaded",
                appHelpers.getText(lblImportantTaskHeader).equals("Important Tasks")
        );
    }
}

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

public class MyDayPage extends BasePage{

    @FindAll(
            @FindBy(xpath = "//a[contains(@class,'mat-list-item sidenav-item')]")
    )
    public List<WebElement> lblMyDaySideNav;

    @FindBy(css = "[ng-reflect-name='taskTitle']")
    public WebElement txtTaskTitle;

    @FindBy(css = "[ng-reflect-name='taskDesc']")
    public WebElement txtTaskDesc;

    @FindBy(css="input[name='isImportant']+div")
    public WebElement chkImportant;

    @FindBy(id="addTask")
    public WebElement btnAddTask;

    @FindBy(xpath = "//mat-error[@class='mat-error']")
    public WebElement lblTitleError;

    @FindAll(
            @FindBy(css = ".task-card .mat-card-content>span")
    )
    public List<WebElement> lstTaskList;

    @FindAll(
            @FindBy(css = ".task-card .mat-card-content .remove-icon")
    )
    public List<WebElement> lstRemoveTaskList;

    @FindAll(
            @FindBy(css = ".task-card .mat-card-content>mat-checkbox")
    )
    public List<WebElement> lstTaskListCheckBox;

    public MyDayPage(WebDriver driver) {
        super(driver);
        waitForPage();
    }

    public void waitForPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(btnAddTask));
        assertThat("Task Manager Landing page is not loaded",
                appHelpers.isTxtPresentOnList(lblMyDaySideNav,"My day")
        );
    }
}

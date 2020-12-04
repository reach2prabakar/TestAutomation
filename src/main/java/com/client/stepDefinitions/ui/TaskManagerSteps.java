package com.client.stepDefinitions.ui;

import com.client.modules.AllTaskPage;
import com.client.modules.ImportantTaskPage;
import com.client.modules.LandingPage;
import com.client.modules.MyDayPage;
import com.client.processor.Page;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class TaskManagerSteps extends Page {
    private static final Logger logger = LogManager.getLogger(TaskManagerSteps.class);

    @Given("^user opens task manager login page$")
    public void loadApplication() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.loadUrl();
    }

    @When("^user login to the application$")
    public void login(){
        LandingPage landingPage = new LandingPage(driver);
        String[] credentials = appHelpers.getCredentials();
        appHelpers.enterTextOnElement(landingPage.txtEmail,credentials[0]);
        appHelpers.enterTextOnElement(landingPage.txtPassword,credentials[1]);
        appHelpers.clickOnElement(landingPage.btnLogin);
    }

    @Then("^user is able to login and view the landing page$")
    public void validateHomePage(){
        new MyDayPage(driver);
        logger.info("My Task Landing page is validated and working fine");
    }

    @And("^user is able to add a task with with title,description and date$")
    public void addTaskInMyDay(DataTable dataTable){
        logger.info("addTaskInMyDay --> ");
        MyDayPage myDayPage = new MyDayPage(driver);
        List<Map<String, String>> taskListMap  = dataTable.asMaps();
        for (Map<String, String> varMap : taskListMap) {
            String var = varMap.get("taskTitle");
            if(var.equals("[blank]")){
                var = "";
            }
            appHelpers.enterTextOnElement(myDayPage.txtTaskTitle,var);
            appHelpers.enterTextOnElement(myDayPage.txtTaskDesc,varMap.get("description"));
            if(varMap.get("important").equalsIgnoreCase("yes")){
                appHelpers.clickOnElement(myDayPage.chkImportant);
            }
            appHelpers.clickOnElement(myDayPage.btnAddTask);
            String errorMsg =  appHelpers.getText(myDayPage.lblTitleError);
            if(varMap.get("taskTitle").equalsIgnoreCase("[blank]")){
               assertThat("No error msg is displayed when adding task with no title ",
                        errorMsg.equalsIgnoreCase("Task title is required"));
            }
            assertThat("Task title should be cleared after adding the task to the grid",
                    appHelpers.getAttribute(myDayPage.txtTaskTitle, "ng-reflect-model").isEmpty());
            assertThat("error msg should not be displayed when adding a task with title",
                    errorMsg.isEmpty());
        }
    }

    @Then("^user is able to see the task in task list table$")
    public void validateAddTask(DataTable dataTable){
        logger.info("validateAddTask --> ");
        List<Map<String, String>> taskListMap  = dataTable.asMaps();
        MyDayPage myDayPage = new MyDayPage(driver);
        for (Map<String, String> varMap : taskListMap) {
            assertThat("the task with title" + varMap.get("taskTitle") + " is not present in the task list table",
                    appHelpers.isTxtOnList(myDayPage.lstTaskList, varMap.get("taskTitle")));
        }
    }

    @Then("^user is able to remove a task with taskTitle$")
    public void validateRemoveTask(DataTable dataTable){
        logger.info("validateRemoveTask -->");
        List<Map<String, String>> taskListMap  = dataTable.asMaps();
        MyDayPage myDayPage = new MyDayPage(driver);
        for (Map<String, String> varMap : taskListMap) {
            int taskPosition = appHelpers.getElementPositionOnList(myDayPage.lstTaskList,varMap.get("taskTitle"));
            appHelpers.clickElementOnListPosition(myDayPage.lstRemoveTaskList, taskPosition);
            assertThat("the task with title " + varMap.get("taskTitle") + " is present in the task list table after removing it",
                    !(appHelpers.isTxtOnList(myDayPage.lstTaskList, varMap.get("taskTitle"))));
        }
    }

    @Then("^user is able to mark/unMark the task as done$")
    public void validateMarkUnMarkTask(DataTable dataTable){
        logger.info("validateMarkUnMarkTask-->");
        List<Map<String, String>> taskListMap  = dataTable.asMaps();
        MyDayPage myDayPage = new MyDayPage(driver);
        for (Map<String, String> varMap : taskListMap) {
            int taskPosition = appHelpers.getElementPositionOnList(myDayPage.lstTaskList,varMap.get("markTask"));
            appHelpers.clickElementOnListPosition(myDayPage.lstTaskListCheckBox, taskPosition);
            String taskDone = appHelpers.getAttributeOnList(myDayPage.lstTaskListCheckBox,"ng-reflect-checked",taskPosition);
            assertThat("the task with title " + varMap.get("markTask") + " failed to mark as done",
                    taskDone.equals("true"));
            int unTaskPosition = appHelpers.getElementPositionOnList(myDayPage.lstTaskList,varMap.get("unMarkTask"));
            String unTask = appHelpers.getAttributeOnList(myDayPage.lstTaskListCheckBox,"ng-reflect-checked",unTaskPosition);
            if(unTask.equals("false")){
                appHelpers.clickElementOnListPosition(myDayPage.lstTaskListCheckBox, unTaskPosition);
            }
            appHelpers.clickElementOnListPosition(myDayPage.lstTaskListCheckBox, unTaskPosition);
            unTask = appHelpers.getAttributeOnList(myDayPage.lstTaskListCheckBox,"ng-reflect-checked",unTaskPosition);
            assertThat("the task with title " + varMap.get("unMarkTask") + " failed to unMark",
                    unTask.equals("false"));
        }
    }

    @Then("^user is able to see all the new and updated task in all task page$")
    public void validateAllTaskPage(DataTable dataTable){
        logger.info("validateAllTaskPage -->");
        MyDayPage myDayPage = new MyDayPage(driver);
        int sidePosition = appHelpers.getElementContainsOnList(myDayPage.lblMyDaySideNav, "All Tasks");
        appHelpers.clickElementOnListPosition(myDayPage.lblMyDaySideNav,sidePosition);
        AllTaskPage allTaskPage = new AllTaskPage(driver);
        logger.info("All Task Landing page is loaded and working fine");
        List<Map<String, String>> taskListMap  = dataTable.asMaps();
        for (Map<String, String> varMap : taskListMap) {
            assertThat("The task "+varMap.get("taskTitle")+" is not listed in the all task page",
            appHelpers.isTxtOnList(allTaskPage.lstTaskAllList,varMap.get("taskTitle"))
            );
        }
    }

    @Then("^user is able to see all the important task in important task page$")
    public void validateImportantTaskPage(DataTable dataTable){
        logger.info("validateImportantTaskPage -->");
        MyDayPage myDayPage = new MyDayPage(driver);
        int sidePosition = appHelpers.getElementContainsOnList(myDayPage.lblMyDaySideNav, "Important Tasks");
        appHelpers.clickElementOnListPosition(myDayPage.lblMyDaySideNav,sidePosition);
        ImportantTaskPage importantTaskPage = new ImportantTaskPage(driver);
        logger.info("Important Task Landing page is loaded and working fine");
        List<Map<String, String>> taskListMap  = dataTable.asMaps();
        for (Map<String, String> varMap : taskListMap) {
            assertThat("The task "+varMap.get("taskTitle")+" is not listed in the all task page",
                    appHelpers.isTxtOnList(importantTaskPage.lstTaskImportantList,varMap.get("taskTitle"))
            );
        }
    }
}

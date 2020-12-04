package com.client.helpers;

import com.client.library.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AppHelpers {

    private static final Logger logger = LogManager.getLogger(AppHelpers.class);
    private final WebDriver driver;

    public AppHelpers(WebDriver driver){
        this.driver = driver;
    }

    public ExpectedCondition<Boolean> jsLoad(){
        return driver -> ((JavascriptExecutor) driver)
                   .executeScript("return document.readyState").toString().equals("complete");
    }

    private ExpectedCondition<Boolean> angularLoad() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return (Boolean) ((JavascriptExecutor) driver).executeScript(
                            "return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1");
                } catch (JavascriptException e) {
                    return true;
                }
            }
        };
    }

    public void waitForJSandJQueryToLoad() {
        ExpectedCondition<Boolean> jsLoad = jsLoad();
        ExpectedCondition<Boolean> angularLoad = angularLoad();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.and(angularLoad, jsLoad));
    }

    public void clickOnElement(WebElement webElement){
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
        }catch(Exception e){
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", webElement);
        }
            waitForJSandJQueryToLoad();
    }

    public void enterTextOnElement(WebElement webElement,String text){
        logger.info("Text entered on "+webElement +" is"+text);
        webElement.clear();
        webElement.click();
        webElement.sendKeys(text);
        waitForJSandJQueryToLoad();
    }

    public boolean isTxtOnList(List<WebElement> webElementList,String text){
        if(webElementList.size()>0){
            if(webElementList.stream().noneMatch(items ->items.getText().toLowerCase().replaceAll("\n"," ").
                    equals(text.toLowerCase()))){
                return false;
            }

            return webElementList.stream()
                    .filter(items -> items.getText().toLowerCase().replaceAll("\n"," ").
                            equals(text.toLowerCase()))
                    .findFirst().get().getText().replaceAll("\n"," ").equals(text);
        }else{
            logger.error("there is no element in the list");
        }
        return false;
    }

    public boolean isTxtPresentOnList(List<WebElement> webElementList,String text){
        if(webElementList.size()>0){
            if(webElementList.stream().noneMatch(items -> items.getText().toLowerCase().replaceAll("\n"," ").
                    contains(text.toLowerCase()))){
                return false;
            }
            return webElementList.stream()
                    .filter(items -> items.getText().toLowerCase().replaceAll("\n"," ").
                            contains(text.toLowerCase()))
                    .findFirst().get().getText().replaceAll("\n"," ").contains(text);
        }else{
            logger.error("there is no element in the list");
        }
        return false;
    }

    public int getElementPositionOnList(List<WebElement> webElementList,String text){
        if(webElementList.size()>0){
            for(int i=0;i<webElementList.size();i++){
               if(webElementList.get(i).getText().equals(text)){ return i; }
            }
        }else{
            logger.error("there is no element in the list");
        }
        return -1;
    }

    public int getElementContainsOnList(List<WebElement> webElementList,String text){
        if(webElementList.size()>0){
            for(int i=0;i<webElementList.size();i++){
                if(webElementList.get(i).getText().replaceAll("\n"," ").contains(text)){ return i; }
            }
        }else{
            logger.error("there is no element in the list");
        }
        return -1;
    }

    public void clickElementOnListPosition(List<WebElement> webElementList,int position){
        if(webElementList.size()>0){
            webElementList.get(position).click();
        }else{
            logger.error("there is no element in the list");
        }
    }

    public String getText(WebElement webElement){
        String var = webElement.getText();
        if(var==null){
            throw new RuntimeException(webElement + " - Web element text is null");
        }
        logger.info("Text on element :" +var);
        return var;
    }

    public String getAttribute(WebElement webElement,String attribute){
        String var = webElement.getAttribute(attribute);
        if(var==null){
            throw new RuntimeException(webElement + " - Web element attribute is not defined");
        }
        logger.info("Text on the attribute "+attribute +" is :" +var);
        return var;
    }

    public String getAttributeOnList(List<WebElement> webElementList,String attribute,int position){
        if(webElementList.size()>0){
            return webElementList.get(position).getAttribute(attribute);
        }else{
            logger.error("there is no element in the list");
        }
        return null;
    }

    public String[] getCredentials(){
        String userName = System.getProperty("email")!=null?System.getProperty("email"):new PropertyReader().getProperty("email");
        String password = System.getProperty("password")!=null?System.getProperty("password"):new PropertyReader().getProperty("password");
        if(userName.isEmpty()&&password.isEmpty()){
            throw new RuntimeException("UserCredentials are not passed in runtime nor set in property file");
        }
        return new String[] {userName,password};
    }
}

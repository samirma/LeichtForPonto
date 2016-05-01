package com.antonio.samir.leichtforponto.webclient.actions;

import com.antonio.samir.leichtforponto.webclient.Browser;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class InputText extends NameValueInput {

    public InputText(String name, String value) {
        super(name, value);
    }

    @Override
    void executeAction(Browser browser) {
        WebElement element = null;
        try {
            element = browser.findElementByName(name);
        } catch (NoSuchElementException e) {
            element = browser.findElementById(name);
        }
        element.click();
        element.sendKeys(getValue());
    }

}

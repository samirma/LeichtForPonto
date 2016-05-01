package com.antonio.samir.leichtforponto.webclient.actions;

import com.antonio.samir.leichtforponto.webclient.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InputSelect extends NameValueInput {

    public InputSelect(String name, String value) {
        super(name, value);
    }

    @Override
    void executeAction(Browser browser) {
        WebElement element = browser.findElementByName(name);
        
        final Select select = new Select(element);
        
        select.selectByValue(getValue());

    }

}


package com.antonio.samir.leichtforponto.webclient.actions;

import com.antonio.samir.leichtforponto.webclient.Browser;
import org.openqa.selenium.WebElement;


public class BlurEvent extends WebAction {
    private String name;

    public BlurEvent(String name) {
        this.name = name;
    }

    @Override
    void executeAction(Browser browser) {
        WebElement element = browser.findElementByName(name);
        browser.blur(element);
    }

}

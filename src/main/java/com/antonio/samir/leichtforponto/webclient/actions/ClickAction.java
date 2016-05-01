
package com.antonio.samir.leichtforponto.webclient.actions;

import com.antonio.samir.leichtforponto.webclient.Browser;
import org.openqa.selenium.WebElement;


public class ClickAction extends WebAction{
    private String xpath;

    public ClickAction(String xpath) {
        this.xpath = xpath;
    }

    @Override
    void executeAction(Browser browser) {
        final WebElement element = browser.findElementByXPath(xpath);
        element.click();
    }

}

package com.antonio.samir.leichtforponto.webclient.browsers;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.antonio.samir.leichtforponto.webclient.Browser;
import javax.annotation.PostConstruct;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.stereotype.Service;

@Service("htmlUnit")
public class HtmlUnit extends Browser{

    @PostConstruct
    public void init() {
        driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    
}

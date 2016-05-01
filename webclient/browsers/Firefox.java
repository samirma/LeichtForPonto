package com.antonio.samir.leichtforponto.webclient.browsers;

import com.antonio.samir.leichtforponto.webclient.Browser;
import javax.annotation.PostConstruct;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.stereotype.Service;

@Service("firefox")
public class Firefox extends Browser {

    @PostConstruct
    public void init() {
        final FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("general.useragent.override", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        driver = new FirefoxDriver(firefoxProfile);
        javascriptExecutor = (JavascriptExecutor) driver;
    }

}

package com.antonio.samir.leichtforponto.webclient;

import com.antonio.samir.leichtforponto.webclient.exceptions.factory.MessageAlertFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author samir
 */
public abstract class Browser {

    protected WebDriver driver;
    protected JavascriptExecutor javascriptExecutor;

    @Autowired
    protected MessageAlertFactory alertFactory;

    public void loadPage(String url) {
        driver.get(url);
    }

    public WebElement findElementByName(final String name) {
        return driver.findElement(By.name(name));
    }

    public WebElement findElementByXPath(final String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public List<WebElement> findElementsByXPath(final String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public void checkAlertDialog() {
        String alertMsg = null;
        try {
            final Alert alert = driver.switchTo().alert();
            alertMsg = alert.getText();
            alert.accept();
        } catch (NoAlertPresentException e) {
            return;
        }
        throw alertFactory.getAlertException(alertMsg);
    }

    public void blur(WebElement element) {
        javascriptExecutor.executeScript("arguments[0].onblur();", element);
    }

    public File takeScreenshot() throws IOException {

        final JavascriptExecutor js = javascriptExecutor;
        Object objY = js.executeScript("return $('div#divCaptcha img').offset().top;");
        Object objX = js.executeScript("return $('div#divCaptcha img').offset().left;");

        String imageXpath = "//DIV[@id='divCaptcha']/img";
        final WebElement element = driver.findElement(By.xpath(imageXpath));

        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        BufferedImage img = null;

        File screen = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);

        img = ImageIO.read(screen);
        final int y = (objY instanceof Double) ? ((Double) objY).intValue() : ((Long) objY).intValue();
        final int x = (objX instanceof Double) ? ((Double) objX).intValue() : ((Long) objX).intValue();

        BufferedImage dest = img.getSubimage(x, y, width,
                height);

        ImageIO.write(dest, "png", screen);

        return screen;
    }

    public WebElement findElementById(String name) {
        return driver.findElement(By.id(name));
    }

    public String getHtml() {
        final String pageSource = driver.getPageSource();
        return pageSource;
    }

    public void goToFrame(final String xpath) {
        final WebElement iFrame = findElementByXPath(xpath);
        driver.switchTo().frame(iFrame);
    }

    public void close() {
        driver.close();
    }

}

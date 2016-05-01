package com.antonio.samir.leichtforponto;

import com.antonio.samir.leichtforponto.model.TimeTrack;
import com.antonio.samir.leichtforponto.util.DateUtil;
import com.antonio.samir.leichtforponto.webclient.Browser;
import com.antonio.samir.leichtforponto.webclient.actions.ClickAction;
import com.antonio.samir.leichtforponto.webclient.actions.InputText;
import com.antonio.samir.leichtforponto.webclient.actions.PageLoader;
import com.antonio.samir.leichtforponto.webclient.actions.WebAction;
import com.antonio.samir.leichtforponto.webclient.exceptions.NotifyAlertException;
import com.antonio.samir.leichtforponto.webclient.exceptions.StopIterationExcepition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ForpontoWebClient implements FormForponto {

    private static final Long DEFAULT_DELAY = 800l;
    private static final Logger LOGGER = Logger.getLogger(ForpontoWebClient.class.getName());

    private List<WebAction> loginActions;

    @Autowired()
    @Qualifier("firefox")
    private Browser browser;

    @Autowired
    private FormLogin loginData;

    @Autowired
    DataEntryParser dataEntryParser;
    private String endDate;
    private String startDate;

    @PostConstruct
    public void init() {

        loginActions = new ArrayList<>();
        
    }

    private void executeActions(List<WebAction> actions) {

        for (WebAction action : actions) {
            try {
                action.execute(browser);
            } catch (NotifyAlertException ex) {
                continue;
            } catch (Exception ex) {
                throw new StopIterationExcepition(String.format("Fail to execute: %s", action), ex);
            }

            sleep();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(DEFAULT_DELAY);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void login() {
        executeActions(loginActions);
    }

    public void loadLoginActionsData(FormLogin recoverData) {

        loadLoginActions(recoverData);

    }

    private void loadLoginActions(FormLogin recoverData) {

        loginActions.add(new PageLoader(recoverData.getServer()));

        loginActions.add(new ClickAction("/html/body/table/tbody/tr/td[1]/table/tbody/tr[2]/td/table/tbody/tr[1]/td/font/p/a"));
        loginActions.add(new InputText("deEdtUsuCodigoAD", recoverData.getLogin()));
        loginActions.add(new InputText("deEdtUsaSenhaAD", recoverData.getPassword()));

        loginActions.add(new InputText("deEdtDataDe", startDate));
        loginActions.add(new InputText("deEdtDataAte", endDate));

        loginActions.add(new ClickAction("/html/body/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table[2]/tbody/tr/td/form/table/tbody/tr/td/table/tbody/tr[2]/td/input"));

        loginActions.add(new ClickAction("/html/body/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table[2]/tbody/tr/td/form/table/tbody/tr/td/table/tbody/tr[2]/td/input"));

    }

    @Override
    public String getContent() {
        return browser.getHtml();
    }

    @Override
    public void loadUrl(String url) {
        final PageLoader pageLoader = new PageLoader(url);
        pageLoader.execute(browser);
    }

    @Override
    public List<TimeTrack> getDataEntries() {
        //browser.goToFrame("/html/frameset/frame[1]");

        final List<TimeTrack> times = new ArrayList<>();

        final List<WebElement> hours = browser.findElementsByXPath("//tr[@class='celulatabptomarc']");

        for (WebElement hour : hours) {
            final String text = hour.getText();
            final TimeTrack timeTrack = dataEntryParser.parse(text);
            times.add(timeTrack);
        }

        browser.close();

        return times;
    }

    @Override
    public List<TimeTrack> getWorksHours(final Calendar startDateCal, final Calendar endDateCal) {

        startDate = DateUtil.getDayString(startDateCal);
        endDate = DateUtil.getDayString(endDateCal);
        
        loadLoginActionsData(loginData);
        
        login();
        
        return getDataEntries();
    }

}

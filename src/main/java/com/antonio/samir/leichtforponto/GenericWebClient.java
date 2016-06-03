package com.antonio.samir.leichtforponto;

import com.antonio.samir.leichtforponto.model.TimeTrack;
import com.antonio.samir.leichtforponto.util.DateUtil;
import com.antonio.samir.leichtforponto.webclient.Browser;
import com.antonio.samir.leichtforponto.webclient.actions.PageLoader;
import com.antonio.samir.leichtforponto.webclient.actions.WebAction;
import com.antonio.samir.leichtforponto.webclient.exceptions.NotifyAlertException;
import com.antonio.samir.leichtforponto.webclient.exceptions.StopIterationExcepition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class GenericWebClient implements FormForponto {
    
    protected static final Long DEFAULT_DELAY = 800l;
    protected static final Logger LOGGER = Logger.getLogger(GenericWebClient.class.getName());
    protected List<WebAction> loginActions;
    @Autowired
    @Qualifier(value = "firefox")
    protected Browser browser;
    @Autowired
    protected FormLogin loginData;
    @Autowired
    DataEntryParser dataEntryParser;
    protected String endDate;
    protected String startDate;

    @PostConstruct
    public void init() {
        loginActions = new ArrayList<>();
    }

    protected void executeActions(List<WebAction> actions) {
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

    protected void sleep() {
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
        loadLoginActions(recoverData, loginActions);
    }

    @Override
    public String getContent() {
        return browser.getHtml();
    }

    @Override
    public void loadUrl(final String url) {
        final PageLoader pageLoader = new PageLoader(url);
        pageLoader.execute(browser);
    }

    @Override
    public List<TimeTrack> getWorksHours(final Calendar startDateCal, final Calendar endDateCal) {
        startDate = DateUtil.getDayString(startDateCal);
        endDate = DateUtil.getDayString(endDateCal);
        loadLoginActionsData(loginData);
        login();
        return getDataEntries();
    }

    public abstract void loadLoginActions(FormLogin recoverData, List<WebAction> loginActions);
    
}

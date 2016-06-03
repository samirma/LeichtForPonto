package com.antonio.samir.leichtforponto;

import com.antonio.samir.leichtforponto.model.TimeTrack;
import com.antonio.samir.leichtforponto.webclient.actions.ClickAction;
import com.antonio.samir.leichtforponto.webclient.actions.InputText;
import com.antonio.samir.leichtforponto.webclient.actions.PageLoader;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

@Service
public class ForpontoWebClient extends GenericWebClient {


    @Override
    public void loadLoginActions(FormLogin recoverData, List loginActions) {

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

}

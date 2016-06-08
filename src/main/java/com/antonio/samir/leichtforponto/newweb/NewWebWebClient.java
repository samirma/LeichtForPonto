package com.antonio.samir.leichtforponto.newweb;

import com.antonio.samir.leichtforponto.FormLogin;
import com.antonio.samir.leichtforponto.GenericWebClient;
import com.antonio.samir.leichtforponto.model.TimeTrack;
import com.antonio.samir.leichtforponto.webclient.actions.ClickAction;
import com.antonio.samir.leichtforponto.webclient.actions.InputText;
import com.antonio.samir.leichtforponto.webclient.actions.PageLoader;
import com.antonio.samir.leichtforponto.webclient.actions.WebAction;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewWebWebClient extends GenericWebClient {

    @Autowired
    protected NewWebFormlogin webFormlogin;

    private static final String URL = "http://norber.m4u/WebPonto/just_user/justuser_corpo.asp?AlteraFutura=1&AlteraHorasApu=F&Assinar=F&AutoJust=T&CodEmpresaEmpregado=1&CodLotacao=&DataFinal=END_DATE&DataIniBH=START_DATE&DataInicial=START_DATE&EPAutoInc=&EPData=&EPTipo=&ExibirAlertSemanaNegativa=F&FUCodEmp=1&FULotacao=&FiltrarCentroCusto=&FiltroCheck=&FiltroNome=&FuncMesmaMatr=&FuncionarioSelecionado=1&Grid=T&Marc1=&Marc2=&NenhumFunc=&PermiteMarcacaoOriginal=F&Submit=%20Exibir%20&TipoES=&TipoFuncs=&VisualizaMesmoOperador=T&hSelecao=0&hiExbibido=1&hidDtFimPeriodoAtual=END_DATE&hidDtIniPeriodoAtual=START_DATE&hidErroFiltro=&hidExibir=ON&hidItemEsp=%24MARCACAO&hidItemEsp=%24INCLUIR&hidLivreParaNavegacao=&hidMatriculaNaoMudou=&hidNaoAprovadas=&hidNmJanela=%20Per%C3%ADodo%20de%20START_DATE%20%C3%A0%20END_DATE&hidPeriodoSelecionado=&hidPeriodoSelecionadoFim=&hidPeriodoSelecionadoIni=&hidPodeNavegar=S&lbConfirmado=Confirmado&lstMarcacao=0&lstNome=1%3B100287&quantItemAcertEspeciais=0&txtDsLotacao=&txtMatricula=100287&wDtBaseFim=31%2F01%2F2016&wDtBaseIni=START_DATE";

    @PostConstruct
    public void init() {
        loginActions = new ArrayList<>();

        loginData = webFormlogin;

    }

    @Override
    public void loadLoginActions(FormLogin recoverData, List<WebAction> loginActions) {

        loginActions.add(new PageLoader(recoverData.getServer()));

        loginActions.add(new InputText("CodEmpresa", ((NewWebFormlogin) recoverData).getCompany()));

        loginActions.add(new InputText("requiredusuario", recoverData.getLogin()));
        loginActions.add(new InputText("requiredsenha", recoverData.getPassword()));
        loginActions.add(new ClickAction("/html/body/form/div[3]/div[1]/div[3]/div[2]/input[2]"));

        final String startDateEncoded = URLEncoder.encode(startDate);
        final String endDateEncoded = URLEncoder.encode(endDate);

        final String urlWithStartDate = StringUtils.replace(URL, "START_DATE", startDateEncoded);
        final String urlDone = StringUtils.replace(urlWithStartDate, "END_DATE", endDateEncoded);

        loginActions.add(new PageLoader(urlDone));

    }

    @Override
    public List<TimeTrack> getDataEntries() {
        List<TimeTrack> times = generateList();

        browser.close();

        return times;
    }

    private List<TimeTrack> generateList() {
        final List<WebElement> tbs = browser.findElementsByXPath("//td");
        final List<TimeTrack> times = new ArrayList<>();
        String timeRawDay = null;
        TimeTrack timeTrack = null;
        StringBuffer hoursEntries = new StringBuffer("");
        for (WebElement tb : tbs) {

            final String text = tb.getAttribute("class");

            try {

                if (StringUtils.isNotBlank(text)) {

                    Date date = dataEntryParser.getDate(tb.getText());

                    addTime(timeTrack, hoursEntries.toString(), times);

                    hoursEntries = new StringBuffer("");

                    timeTrack = new TimeTrack();

                    timeTrack.dateEntry = date;

                    continue;

                }

            } catch (ParseException ex) {

            }

            final List<WebElement> rawHours = tb.findElements(By.xpath(".//input[@size='7']"));
            for (WebElement rawHour : rawHours) {
                hoursEntries.append("   ");
                final String value = rawHour.getAttribute("value");
                hoursEntries.append(value);
            }
        }
        addTime(timeTrack, hoursEntries.toString(), times);
        return times;
    }

    private void addTime(TimeTrack timeTrack, String hours, List<TimeTrack> times) {
        if (timeTrack != null) {
            final long hoursWorked = dataEntryParser.getHoursWorked(hours);
            timeTrack.hoursWorked = hoursWorked;
            times.add(timeTrack);
        }
    }

}

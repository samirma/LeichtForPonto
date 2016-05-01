package com.antonio.samir.leichtforponto.webclient.exceptions.factory;

import com.antonio.samir.leichtforponto.webclient.exceptions.AlertException;
import com.antonio.samir.leichtforponto.webclient.exceptions.NotifyAlertException;
import com.antonio.samir.leichtforponto.webclient.exceptions.StopIterationExcepition;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageAlertFactoryImpl implements MessageAlertFactory {

    private final List<String> notifyStrings = new ArrayList<>();
    private final List<String> stopIterationsStrings = new ArrayList<>();
    private final List<String> criticalStrings = new ArrayList<>();

    @PostConstruct
    public void init() {
        stopIterationsStrings.add("Verificar código digitado");
    }

    @Override
    public AlertException getAlertException(final String message) {
        AlertException excepetion = null;
        if (isNotifyExeption(message)) {
            excepetion = new NotifyAlertException(message);
        } else if (isStopIterationException(message)){
            excepetion = new StopIterationExcepition(message);
        } 
        
        return excepetion;
    }

    private boolean isNotifyExeption(final String message) {

        return simpleCheckList(message, notifyStrings);
    }

    private boolean isStopIterationException(final String message) {

        return simpleCheckList(message, stopIterationsStrings);

    }

    
    private boolean simpleCheckList(final String message, final List<String> strings) {
        boolean isNotifyExeption = false;
        for (String string : strings) {
            if (message.contains(string)) {
                isNotifyExeption = true;
                break;
            }
        }
        return isNotifyExeption;
    }

}

package com.antonio.samir.leichtforponto.webclient.exceptions;

public class AlertException extends RuntimeException {

    public String alertMsg;

    public AlertException(final String alertMsg) {
        super();
        this.alertMsg = alertMsg;
    }

    public AlertException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public AlertException(Throwable thrwbl) {
        super(thrwbl);
    }

    public String getAlertMsg() {
        return alertMsg;
    }

    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }

}

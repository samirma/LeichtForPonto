
package com.antonio.samir.leichtforponto.webclient.exceptions;


public class StopIterationExcepition extends AlertException {

    public StopIterationExcepition(String alertMsg) {
        super(alertMsg);
    }

    public StopIterationExcepition(String message, Exception ex) {
        super(message, ex);
    }

}

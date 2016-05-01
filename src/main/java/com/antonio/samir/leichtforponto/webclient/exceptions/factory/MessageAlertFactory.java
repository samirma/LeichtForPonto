
package com.antonio.samir.leichtforponto.webclient.exceptions.factory;

import com.antonio.samir.leichtforponto.webclient.exceptions.AlertException;


public interface MessageAlertFactory {

    public AlertException getAlertException(final String message);

}

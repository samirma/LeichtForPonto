package com.antonio.samir.leichtforponto.webclient.actions;

import com.antonio.samir.leichtforponto.webclient.Browser;

public abstract class NameValueInput extends WebAction {
    
    protected Long defaultDelay = 500l;

    protected String name;
    protected String value;

    public NameValueInput(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "NameValueInput{" + "name=" + name + ", value=" + value + '}';
    }

    @Override
    void executeAction(Browser browser) {
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}

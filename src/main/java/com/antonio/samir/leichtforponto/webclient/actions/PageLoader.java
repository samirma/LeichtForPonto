
package com.antonio.samir.leichtforponto.webclient.actions;

import com.antonio.samir.leichtforponto.webclient.Browser;


public class PageLoader extends WebAction{
    private String url;
    
    public PageLoader(final String url) {
        this.url = url;
    }

    @Override
    void executeAction(Browser browser) {
        browser.loadPage(url);
    }



}

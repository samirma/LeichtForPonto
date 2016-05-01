package com.antonio.samir.leichtforponto;

import com.antonio.samir.leichtforponto.model.TimeTrack;
import java.util.Calendar;
import java.util.List;


public interface FormForponto {
        
    void login();

    public String getContent();
    
    void loadUrl(String url);

    public List<TimeTrack> getDataEntries();

    public List<TimeTrack> getWorksHours(Calendar startDate, Calendar endDate);

    
}


package com.antonio.samir.leichtforponto;

import com.antonio.samir.leichtforponto.model.TimeTrack;
import java.text.ParseException;
import java.util.Date;


public interface DataEntryParser {
    TimeTrack parse(String text);

    Date getDate(String text) throws ParseException;
    
    long getHoursWorked(final String text);
    
}

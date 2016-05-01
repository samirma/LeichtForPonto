package com.antonio.samir.leichtforponto.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String idDate = "yyyyMMdd";
    private static final String DAY = "dd/MM/yyyy";

    public static String getStringId(Date date) {
        SimpleDateFormat myFormat = new SimpleDateFormat(idDate);

        String reformattedStr = myFormat.format(date);

        return reformattedStr;
    }

    public static String getDayString(Calendar today) {
        SimpleDateFormat myFormat = new SimpleDateFormat(DAY);
        
        final Date date = today.getTime();

        String reformattedStr = myFormat.format(date);

        return reformattedStr;
    }

}

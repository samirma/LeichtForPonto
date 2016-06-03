package com.antonio.samir.leichtforponto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtil {

    private static final String KEY_DATE = "yyyyMMdd";
    private static final String DAY = "dd/MM/yyyy";

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DAY);

    public static String getStringId(Date date) {

        final Calendar instance = Calendar.getInstance();

        instance.setTime(date);

        final String reformattedStr = DateFormatUtils.format(date, KEY_DATE);

        return reformattedStr;
    }

    public static String getDayString(Calendar today) {
        SimpleDateFormat myFormat = SIMPLE_DATE_FORMAT;

        final Date date = today.getTime();

        String reformattedStr = myFormat.format(date);

        return reformattedStr;
    }

    public static Date parseDateFromPtString(String dateString) throws ParseException {
        SimpleDateFormat myFormat = SIMPLE_DATE_FORMAT;

        final Date reformattedStr = myFormat.parse(dateString);

        return reformattedStr;
    }

}

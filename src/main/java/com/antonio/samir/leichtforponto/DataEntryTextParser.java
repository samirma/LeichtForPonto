package com.antonio.samir.leichtforponto;

import com.antonio.samir.leichtforponto.model.TimeTrack;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class DataEntryTextParser implements DataEntryParser {

    private static final String TAG = DataEntryTextParser.class.getName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    @Override
    public TimeTrack parse(final String text) {
        TimeTrack timeTrack = null;
        try {
            final Date date = getDate(text);
            final long hoursWorked = getHoursWorked(text);

            timeTrack = new TimeTrack();
            timeTrack.dateEntry = date;
            timeTrack.hoursWorked = hoursWorked;

        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return timeTrack;
    }

    private Date getDate(String text) throws ParseException {
        String pattern = "([0-9]{2}\\/[0-9]{2}\\/[0-9]{4})";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher matcher = r.matcher(text);

        String dateString = null;

        if (matcher.find()) {
            dateString = matcher.group(1);
        }

        final Date parseDate = DateUtils.parseDate(dateString, "dd/MM/yyyy");

        return parseDate;
    }

    private long getHoursWorked(String text) {
        String pattern = "([0-9]{2}:[0-9]{2})";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher matcher = r.matcher(text);

        long workedTime = 0;

        Date enterTime = null;
        Date leftTime = null;

        while (matcher.find()) {
            String hour = matcher.group(1);

            try {
                final Date parseDate = DateUtils.parseDate(hour, "HH:mm");

                if (enterTime == null) {
                    enterTime = parseDate;
                } else if (leftTime == null) {
                    leftTime = parseDate;

                    final long startToEnd = leftTime.getTime() - enterTime.getTime();

                    workedTime = workedTime + (startToEnd/1000);

                    enterTime = null;
                    leftTime = null;

                }

            } catch (ParseException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

        }

        return workedTime;
    }

}

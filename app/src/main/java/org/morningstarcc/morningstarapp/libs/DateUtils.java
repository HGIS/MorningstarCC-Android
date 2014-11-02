package org.morningstarcc.morningstarapp.libs;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A collection of useful functions for parsing and creating date strings.
 */
public class DateUtils {
    private static final String TAG = "DateUtils";

    // input formats
    private static final SimpleDateFormat FULL = new SimpleDateFormat("M/d/yyyy h:mm a");
    private static final SimpleDateFormat MONTH_DAY_YEAR = new SimpleDateFormat("M/d/yyyy");

    // output formats
    private static final SimpleDateFormat MONTH = new SimpleDateFormat("MMM");
    private static final SimpleDateFormat DAY = new SimpleDateFormat("d");
    private static final SimpleDateFormat MONTH_DAY = new SimpleDateFormat("MMMM d");
    private static final SimpleDateFormat HOUR_MINUTE = new SimpleDateFormat("h:mm");
    private static final SimpleDateFormat MARKER = new SimpleDateFormat("a");

    public static Date getDate(String dateString) {
        try {
            return MONTH_DAY_YEAR.parse(dateString);
        }
        catch (ParseException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public static Date getDate(String dateString, String dateTime) {
        try {
            return FULL.parse(dateString + " " + dateTime);
        }
        catch (ParseException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public static String getMonthString(Date month) {
        return MONTH.format(month);
    }

    public static String getDayString(Date day) {
        return DAY.format(day);
    }

    public static String getDateInterval(Date startDate, Date endDate) {
        return intervalOrConstant(MONTH_DAY.format(startDate), MONTH_DAY.format(endDate));
    }

    public static String getTimeInterval(Date startDate, Date endDate) {
        String startTime = HOUR_MINUTE.format(startDate), startMarker = MARKER.format(startDate);
        String endTime = HOUR_MINUTE.format(endDate), endMarker = MARKER.format(endDate);

        if (startMarker.equals(endMarker)) {
            return intervalOrConstant(startTime, endTime) + " " + startMarker;
        }

        return String.format("%s %s - %s %s", startTime, startMarker, endTime, endMarker);
    }

    private static String intervalOrConstant(String first, String second) {
        if (first.equals(second)) {
            return first;
        }

        return first + " - " + second;
    }
}

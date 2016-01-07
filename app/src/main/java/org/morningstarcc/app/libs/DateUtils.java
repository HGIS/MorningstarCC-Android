package org.morningstarcc.app.libs;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A collection of useful functions for parsing and creating date strings.
 *  * 11/21/2015 - Juan Manuel Gomez - Added new method to format the date "getFullDayYearString"
 */
public class DateUtils {
    private static final String TAG = "DateUtils";

    // input formats
    private static final SimpleDateFormat FULL = new SimpleDateFormat("M/d/yyyy h:mm a", Locale.US);
    private static final SimpleDateFormat SPECIFIC_FULL = new SimpleDateFormat("M/d/yyyy h:mm:ss a", Locale.US);
    private static final SimpleDateFormat MONTH_DAY_YEAR = new SimpleDateFormat("M/d/yyyy", Locale.US);

    // output formats
    public static final SimpleDateFormat MONTH = new SimpleDateFormat("MMM", Locale.US);
    public static final SimpleDateFormat DAY = new SimpleDateFormat("dd", Locale.US);
    public static final SimpleDateFormat MONTH_DAY = new SimpleDateFormat("MMMM d", Locale.US);
    public static final SimpleDateFormat TIME_OF_DAY = new SimpleDateFormat("h:mm a", Locale.US);
    public static final SimpleDateFormat HOUR_MINUTE = new SimpleDateFormat("h:mm", Locale.US);
    public static final SimpleDateFormat MARKER = new SimpleDateFormat("a", Locale.US);
    public static final SimpleDateFormat WEEKDAY_MONTH_DATE = new SimpleDateFormat("EEE MMM d", Locale.US);

    public static final SimpleDateFormat WEEKDAY_MONTH_YEAR = new SimpleDateFormat("EEE MMM d, yyyy", Locale.US);

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

    public static Date getFullDate(String dateString) {
        try {
            return SPECIFIC_FULL.parse(dateString);
        }
        catch (ParseException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public static String getFullString(Date date) { return FULL.format(date); }

    public static String getTimeOfDay(Date date) {
        return TIME_OF_DAY.format(date);
    }

    public static String getMonthString(Date month) {
        return MONTH.format(month);
    }

    public static String getDayString(Date day) {
        return DAY.format(day);
    }

    public static String getFullDayString(Date day) {
        return WEEKDAY_MONTH_DATE.format(day);
    }

    public static String getFullDayYearString(Date day) {
        return WEEKDAY_MONTH_YEAR.format(day);
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

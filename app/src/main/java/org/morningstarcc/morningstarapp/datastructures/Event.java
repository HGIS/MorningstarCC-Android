package org.morningstarcc.morningstarapp.datastructures;

import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 10/20/2014.
 */
public class Event {

    private static SimpleDateFormat dateFormat;

    public String title, description, link;
    public Date start, end;
    public boolean registration;

    public Event(String date, String startTime, String endTime, String title, String description, String registration, String link, String pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;

        this.registration = registration != null && Boolean.valueOf(registration);
        getDates(date, startTime, endTime);
    }

    private Event(String[] fields) {
        this(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]);
    }

    private void getDates(String day, String startTime, String endTime) {
        this.start = getDate(day + " " + startTime);
        this.end = getDate(day + " " + endTime);
    }

    private Date getDate(String dateString) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        }

        try {
            return dateFormat.parse(dateString);
        }
        catch (ParseException e) {
            Log.e(getClass().getName(), Log.getStackTraceString(e));
            return null;
        }
    }

    private static int[] columnIndices;
    public static Event[] getEvents(Cursor cursor) {
        Event[] events = new Event[cursor.getCount()];

        if (columnIndices == null) {
            getColumnIndices(cursor);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            events[cursor.getPosition()] = getEvent(cursor);
            cursor.moveToNext();
        }

        return events;
    }

    private static Event getEvent(Cursor cursor) {
        return new Event(new String[] {
                cursor.getString(columnIndices[0]),
                cursor.getString(columnIndices[1]),
                cursor.getString(columnIndices[2]),
                cursor.getString(columnIndices[3]),
                cursor.getString(columnIndices[4]),
                cursor.getString(columnIndices[5]),
                cursor.getString(columnIndices[6]),
                cursor.getString(columnIndices[7]),
        });
    }

    private static void getColumnIndices(Cursor cursor) {
        columnIndices = new int[] {
                cursor.getColumnIndex("eventdate"),
                cursor.getColumnIndex("eventstarttime"),
                cursor.getColumnIndex("eventendtime"),
                cursor.getColumnIndex("title"),
                cursor.getColumnIndex("description"),
                cursor.getColumnIndex("registration"),
                cursor.getColumnIndex("link"),
                cursor.getColumnIndex("pubDate")
        };
    }
}

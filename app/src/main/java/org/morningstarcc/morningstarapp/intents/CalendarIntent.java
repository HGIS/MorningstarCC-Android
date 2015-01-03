package org.morningstarcc.morningstarapp.intents;

import android.content.Intent;

import java.util.Date;

/**
 * An intent to add an event to the users choice of calendar apps on their device.
 */
public class CalendarIntent extends Intent {

    public CalendarIntent(Date start, Date end, String title, String description) {
        super(Intent.ACTION_EDIT);

        setType("vnd.android.cursor.item/event");
        putExtra("beginTime", start.getTime());
        putExtra("allDay", false);
        putExtra("endTime", end.getTime());
        putExtra("title", title);
        putExtra("description", description);
    }
}

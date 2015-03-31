package org.morningstarcc.morningstarapp.intents;

import android.content.Intent;

import java.util.Date;

/**
 * An intent to add an event to the users choice of calendar apps on their device.
 */
public class CalendarIntent {

    public static Intent build(Date start, Date end, String title, String description) {
        Intent intent = new Intent(Intent.ACTION_EDIT);

        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", start.getTime());
        intent.putExtra("allDay", false);
        intent.putExtra("endTime", end.getTime());
        intent.putExtra("title", title);
        intent.putExtra("description", description);

        return intent;
    }
}

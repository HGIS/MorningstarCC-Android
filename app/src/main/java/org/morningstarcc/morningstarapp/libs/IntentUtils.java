package org.morningstarcc.morningstarapp.libs;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;

import java.util.Date;

/**
 * Created by Kyle on 7/18/2015.
 */
public class IntentUtils {
    public static Intent emailIntent(String address) {
        return new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
    }

    public static Intent calendarIntent(Date start, Date end, String title, String description) {
        return new Intent(Intent.ACTION_EDIT)
                .setType("vnd.android.cursor.item/event")
                .putExtra("beginTime", start.getTime())
                .putExtra("allDay", false)
                .putExtra("endTime", end.getTime())
                .putExtra("title", title)
                .putExtra("description", description);
    }

    public static Intent shareIntent(String text) {
        return new Intent()
                .setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text).toString())
                .setType("text/plain");
    }

    public static Intent webIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    public static Intent googleMapsIntent(String location) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + location))
                .setPackage("com.google.android.apps.maps");
    }
}

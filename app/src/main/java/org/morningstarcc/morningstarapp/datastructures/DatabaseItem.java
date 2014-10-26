package org.morningstarcc.morningstarapp.datastructures;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 10/21/2014.
 */
public class DatabaseItem {

    protected Context mContext;

    public DatabaseItem(Context mContext) {
        this.mContext = mContext;
    }

    public Bundle[] get(String fromTable) {
        DatabaseStorage storage = new DatabaseStorage(mContext);
        Cursor eventData = storage.get(fromTable, mContext.getResources().getStringArray(R.array.event_fields));
        Bundle[] events = getItems(eventData);

        storage.close();

        return events;
    }

    private static SimpleDateFormat dateFormat;
    public static Date getDate(String dateString, String timeString) {
        try {
            if (dateFormat == null)
                dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");

            return dateFormat.parse(dateString + " " + timeString);
        }
        catch (ParseException e) {
            Log.e("Event Parse", Log.getStackTraceString(e));
            return null;
        }
    }

    private static Bundle[] getItems(Cursor cursor) {
        Bundle[] items = new Bundle[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items[cursor.getPosition()] = getItem(cursor);
            cursor.moveToNext();
        }

        return items;
    }

    private static Bundle getItem(Cursor cursor) {
        Bundle item = new Bundle();

        for (int col = 0; col < cursor.getColumnCount(); col++) {
            item.putString(cursor.getColumnName(col), cursor.getString(col));
        }

        return item;
    }
}

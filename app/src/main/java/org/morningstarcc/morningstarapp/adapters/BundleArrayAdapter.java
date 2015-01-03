package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

/**
 * Created by Kyle on 10/21/2014.
 */
public class BundleArrayAdapter {

    protected Context mContext;

    public BundleArrayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public Bundle[] get(String fromTable, int item_fields_res_id) {
        DatabaseStorage storage = new DatabaseStorage(mContext);
        Cursor eventData = storage.get(fromTable, mContext.getResources().getStringArray(item_fields_res_id));
        Bundle[] events = getItems(eventData);

        storage.close();

        return events;
    }

    private static Bundle[] getItems(Cursor cursor) {
        if (cursor == null)
            return new Bundle[0];

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

package org.morningstarcc.morningstarapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * A class with static methods to convert types commonly accessed in the database package.
 */
public class DatabaseTypeAdapters {

    /**
     * Gets all the column names from the given rows.
     *
     * @param rows  the collection of data rows to search for column names
     * @return  an array of all the keys in the ContentValues
     */
    public static String[] getColumnNames(List<ContentValues> rows) {
        HashSet<String> keySet = new HashSet<String>();

        // get column names from each row
        for (ContentValues row : rows) {
            for (Map.Entry<String, Object> entry : row.valueSet())
                keySet.add(entry.getKey());
        }

        return keySet.toArray(new String[keySet.size()]);
    }

    /**
     * Reads the data provided by the cursor into a Bundle array.
     *
     * @param cursor    the data to read from
     * @return  the array of Bundles representing the data
     */
    public static Bundle[] getAsBundleArray(Cursor cursor) {
        if (cursor == null)
            return new Bundle[0];

        Bundle[] items = new Bundle[cursor.getCount()];

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items[cursor.getPosition()] = getAsBundle(cursor);
            cursor.moveToNext();
        }

        return items;
    }

    /**
     * Reads a single cursor row and returns the data as a bundle.
     * Does not advance the cursor.
     *
     * @param cursor the cursor to read from. Assumed to be on the row to be read
     * @return  the Bundle representing the data from the Cursor
     */
    private static Bundle getAsBundle(Cursor cursor) {
        Bundle item = new Bundle();

        for (int col = 0; col < cursor.getColumnCount(); col++)
            item.putString(cursor.getColumnName(col), cursor.getString(col));

        return item;
    }
}

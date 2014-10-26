package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Kyle on 9/14/2014.
 *
 * An interface that defines what methods are available to save or read data locally.
 * It also includes a convenience method to check when the storage was last updated.
 */
public interface LocalStorage {
    public Calendar getLastUpdated();
    public Cursor get(String from, String... fields);
    public void set(String to, List<ContentValues> data);
}

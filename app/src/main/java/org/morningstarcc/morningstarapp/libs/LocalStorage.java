package org.morningstarcc.morningstarapp.libs;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Kyle on 9/14/2014.
 */
public interface LocalStorage {
    public Calendar getLastUpdated();
    public Cursor get(String from, String... fields);
    public void set(String to, ArrayList<HashMap<String, String>> data);
}

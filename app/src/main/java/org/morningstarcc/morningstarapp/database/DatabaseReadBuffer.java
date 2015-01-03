package org.morningstarcc.morningstarapp.database;

import android.database.Cursor;
import android.os.Bundle;

import static org.morningstarcc.morningstarapp.database.DatabaseTypeAdapters.getAsBundleArray;

/**
 * Created by whykalo on 1/3/2015.
 */
public class DatabaseReadBuffer {

    private Cursor data;

    public DatabaseReadBuffer(Cursor data) {
        this.data = data;
    }

    public Cursor getData() {
        return this.data;
    }

    public int getSize() {
        return this.data.getCount();
    }

    public Bundle[] asBundleArray() {
        return getAsBundleArray(this.data);
    }
}

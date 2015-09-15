package org.morningstarcc.app.database;

import android.database.Cursor;
import android.os.Bundle;

import static org.morningstarcc.app.database.DatabaseTypeAdapters.getAsBundleArray;

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
        if (data == null)
            return 0;

        return this.data.getCount();
    }

    public Bundle[] asBundleArray() {
        return getAsBundleArray(this.data);
    }
}

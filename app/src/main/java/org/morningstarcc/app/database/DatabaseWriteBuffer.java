package org.morningstarcc.app.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A buffer for writing thread-safely to the database.
 */
public class DatabaseWriteBuffer {

    private SQLiteDatabase db;
    private String table;

    private List<ContentValues> buffer;

    public DatabaseWriteBuffer(SQLiteDatabase db, String table) {
        this.db = db;
        this.table = table;

        buffer = new ArrayList<ContentValues>();
    }

    /**
     * Appends the ContentValues to the list of things to write.
     *
     * @param contentValues the row data to append
     */
    public void add(ContentValues contentValues) {
        buffer.add(contentValues);
    }

    /**
     * Writes the data to the database, thread-safely.
     * Drops the old table if it exists.
     */
    public void flush() {
        db.beginTransaction();

        try {
            for (ContentValues row : buffer) {
                db.insert(table, null, row);
                db.yieldIfContendedSafely();
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
}

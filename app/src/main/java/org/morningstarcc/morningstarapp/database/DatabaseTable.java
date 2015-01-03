package org.morningstarcc.morningstarapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;

import java.util.Arrays;
import java.util.List;

import javax.security.auth.callback.Callback;

import static org.morningstarcc.morningstarapp.database.DatabaseTypeAdapters.getColumnNames;

/**
 * An abstraction of a database table that assists with reading and writing.
 * This class only acts upon a specific table specified at construction.
 */
public class DatabaseTable {

    private Context mContext;
    private Database databaseHandler;
    private String table;

    public DatabaseTable(Context mContext, Database databaseHandler, String tableName) {
        this.mContext = mContext;
        this.databaseHandler = databaseHandler;
        this.table = tableName;
    }

    /**
     * Checks to see if the database has a table of the given name.
     *
     * @return true iff the table is in the database.
     */
    public boolean exists() {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        boolean exists = exists(db);

        db.close();

        return exists;
    }

    /**
     * Creates a table of the given name, with the given columns inside this database.
     *
     * @param columns the array of column names as strings.
     * @return this database table (for chaining method calls)
     */
    public DatabaseTable create(String[] columns) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        create(db, columns);

        db.close();

        return this;
    }

    /**
     * Returns the values of the given columns in the given table in a cursor.
     * Does not modify the table.
     *
     * @param columns the array of column names as strings. If null, returns all columns.
     * @return  a cursor from the database query, or null if the table does not exist.
     */
    public DatabaseReadBuffer readAll(@Nullable String columns[]) {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor values = null;

        if (exists(db))
            values = readAll(db, columns);

        return new DatabaseReadBuffer(values);
    }

    /**
     * Returns the values of the given columns in the given table in a cursor with given restraints.
     * Does not modify the table.
     *
     * @param columns the array of column names as strings. If null, returns all columns.
     * @param where sql restraints clause
     * @param whereArgs arguments to the where
     * @return  a cursor from the database query, or null if the table does not exist.
     */
    public DatabaseReadBuffer read(@Nullable String columns[], String where, String[] whereArgs) {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor values = null;

        if (exists(db))
            values = read(db, columns, where, whereArgs);

        return new DatabaseReadBuffer(values);
    }

    /**
     * Convenience method to read cursor data into a database buffer.
     * Does not modify the database table.
     *
     * @param arrayId the resource id of the array of column names
     * @return  a cursor from the database query
     */
    public DatabaseReadBuffer readAll(int arrayId) {
        return readAll(mContext.getResources().getStringArray(arrayId));
    }

    /**
     * Writes the given rows into the table.
     * If the table exists, its data is replaced with the given data.
     *
     * @param rows the values of the rows to place in the database.
     */
    public void write(List<ContentValues> rows) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        if (!exists(db))
            create(db, getColumnNames(rows));

        write(db, rows);

        db.close();
    }

    /**
     * Appends the given row to the table.
     *
     * @param row the row to append to the table
     */
    public void append(ContentValues row) {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();

        append(db, row);

        db.close();
    }

    /**
     * Private implementations of the above methods to maintain database reference rather than opening/closing it several times
     */

    private boolean exists(SQLiteDatabase db) {
        Cursor tmp = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = ?", new String[]{table});

        return tmp != null && tmp.getCount() > 0;
    }

    private void create(SQLiteDatabase db, String[] columns) {
        if (columns.length == 0) {
            Log.e("Database", "Failed to create table \"" + table + "\", no columns");
            return;
        }

        String columnCreation = Arrays.toString(columns)
                .replace('[', '(')
                .replace(']', ')')
                .replaceAll(",", " TEXT,");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + table + " " + columnCreation);
    }

    private Cursor readAll(SQLiteDatabase db, String[] columns) {
        return db.query(table, columns, null, null, null, null, null, null);
    }

    private Cursor read(SQLiteDatabase db, String[] columns, String where, String[] whereArgs) {
        return db.query(table, columns, where, whereArgs, null, null, null);
    }

    private void write(SQLiteDatabase db, List<ContentValues> rows) {
        DatabaseWriteBuffer buffer = new DatabaseWriteBuffer(db, table);

        for (ContentValues row : rows)
            buffer.add(row);

        db.execSQL("DROP TABLE IF EXISTS " + table);
        create(db, getColumnNames(rows));

        buffer.flush();
    }

    private void append(SQLiteDatabase db, ContentValues row) {
        db.insert(table, null, row);
    }
}

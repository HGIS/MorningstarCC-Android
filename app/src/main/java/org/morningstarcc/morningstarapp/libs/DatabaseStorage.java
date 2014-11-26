package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.UpdateParcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyle on 10/12/2014.
 *
 * Helper class for reading and writing to the SQLite Database.
 * Includes get and set method for specific tables for convenience.
 */
public class DatabaseStorage extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase db;

    public DatabaseStorage(Context mContext) {
        super(mContext, mContext.getResources().getString(R.string.database_name), null, DATABASE_VERSION);
        this.mContext = mContext;
        this.db = getWritableDatabase();
    }

    /**
     * Gets the rows of the specified columns (or all columns if null) in Cursor format.
     * Allows for indirect database reading
     *
     * @param from table to get data from
     * @param cols the columns to get data from
     * @return  row contents in Cursor format
     */
    public Cursor get(String from, String... cols) {
        try {
            return db.query(from, cols, null, null, null, null, null, null);
        }
        catch (Exception e) {
            Log.w(DatabaseStorage.class.getName(), Log.getStackTraceString(e));
            return null;
        }
    }

    /**
     * Puts data into the database
     *
     * @param to    table to place data into
     * @param data  a list of the row values, with column mappings inside the ContentValues
     */
    public void set(String to, List<ContentValues> data) {
        if (data.size() != 0) {
            addTable(to, getColumnNames(data));
            putValues(to, data);
        }
        else {
            Log.e("DatabaseStorage", "No elements found for table " + to);
        }
    }

    /**
     * Updates a single element of the table.
     *
     * @param table the table to update
     * @param idColumn  the identifying column
     * @param idValue   the identifying value of the given column.
     *                  If it is not unique behavior is undefined (but seriously, it just updates all rows with that column value).
     *                  If it does not exist, nothing happens.
     * @param updateColumn  the column to update
     * @param updateValue   the value to place in the column
     */
    public void update(String table, String idColumn, String idValue, String updateColumn, String updateValue) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues current = new ContentValues();
        current.put(updateColumn, updateValue);

        try {
            Log.d("Test", "Updating " + db.update(table, current, idColumn + " = ?", new String[]{idValue}) + " columns");
        }
        catch (Exception e) {
            Log.w("DatabaseStorage", Log.getStackTraceString(e));
        }

        db.close();
    }

    public void addColumn(String table, String columnName, String type) {
        this.addColumn(table, columnName, type, "");
    }

    /**
     * Appends the given column to the specified table.
     * Allows for special column conditions to be set such as DEFAULT and PRIMARY KEY
     *
     * @param table the table to update
     * @param columnName the column to append to the table
     * @param type  the datatype of the new column
     * @param conditions    the special conditions of the column. Use addColumn(String, String, String) if none.
     */
    public void addColumn(String table, String columnName, String type, String conditions) {
        SQLiteDatabase db = getWritableDatabase();

        // add column if it does not exist
        if (db.query(table, null, null, null, null, null, null, null).getColumnIndex(columnName) < 0) {
            db.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s %s %s;", table, columnName, type, conditions));
        }

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop all tables (source: http://stackoverflow.com/questions/525512/drop-all-tables-command)
        db.execSQL("select 'drop table ' || name || ';' from sqlite_master where type = 'table';");
    }

    /**
     * Method to call once the caller has finished grabbing all the data from the query
     */
    public void close() {
        this.db.close();
    }


    // gives all the unique key values from ContentValues in a list form
    private List<String> getColumnNames(List<ContentValues> data) {
        List<String> columnNames = new ArrayList<String>(data.size());

        for (ContentValues values : data) {
            for (Map.Entry<String, ?> column : values.valueSet()) {
                String key = column.getKey();

                if (!columnNames.contains(key))
                    columnNames.add(column.getKey());
            }
        }

        return columnNames;
    }

    // adds a table with the given name and fields (assumed to all be as TEXT) to the database
    private void addTable(String name, List<String> columns) {
        db.execSQL("DROP TABLE IF EXISTS " + name);
        db.execSQL("CREATE TABLE " + name + "(" + getTableItems(columns) + ");");

        postUpdated();
    }

    // returns the fields in a single SQL string, assuming items as text
    private String getTableItems(List<String> items) {
        StringBuilder asString = new StringBuilder();

        for (String item : items) {
            asString.append(item);
            asString.append(" TEXT, ");
        }

        if (asString.length() >= 2)
            asString.deleteCharAt(asString.length() - 2);

        return asString.toString();
    }

    // puts all the given values into a table of the given name
    private void putValues(String into, List<ContentValues> values) {
        for (ContentValues item : values) {
            db.insert(into, null, item);
        }
    }

    // update the last updated date of the database
    private void postUpdated() {
        PreferenceManager
                .getDefaultSharedPreferences(this.mContext)
                .edit()
                .putString("Last Updated",
                        DateUtils.getFullString(new GregorianCalendar().getTime()))
                .commit()
                ;
    }
}

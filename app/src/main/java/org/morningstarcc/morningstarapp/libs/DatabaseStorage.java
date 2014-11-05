package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public Calendar getLastUpdated() {
        Calendar cal = Calendar.getInstance();
        Date lastUpdated = getDateLastUpdated();

        if (cal != null)
            cal.setTime(lastUpdated != null ? lastUpdated : new Date());

        return cal;
    }

    public Cursor get(String from, String... cols) {
        return db.query(from, cols, null, null, null, null, null, null);
    }

    public void set(String to, List<ContentValues> data) {
        if (data.size() != 0) {
            addTable(to, getColumnNames(data));
            putValues(to, data);
        }
        else {
            Log.e("DatabaseStorage", "No elements found for table " + to);
        }
    }

    public void update(UpdateParcel parcel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues current = new ContentValues();
        current.put(parcel.updateColumn, parcel.updateValue);

        db.update(parcel.table, current, "WHERE ? = ?", new String[]{parcel.identifyingColumn, parcel.identifyingValue});

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

    // returns the date this database was last updated at
    private Date getDateLastUpdated() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd HH:mm:ss yyyy");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);

            return format.parse(preferences.getString("Last Updated", ""));
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd HH:mm:ss yyyy");

        PreferenceManager
                .getDefaultSharedPreferences(this.mContext)
                .edit()
                .putString("Last Updated", format.format(calendar.getTime()))
                .commit()
                ;
    }

    public class UpdateParcel {
        String table;
        String identifyingColumn, identifyingValue;
        String updateColumn, updateValue;

        public UpdateParcel(String table, String identifyingColumn, String identifyingValue, String updateColumn, String updateValue) {
            this.table = table;
            this.identifyingColumn = identifyingColumn;
            this.identifyingValue = identifyingValue;
            this.updateColumn = updateColumn;
            this.updateValue = updateValue;
        }
    }
}

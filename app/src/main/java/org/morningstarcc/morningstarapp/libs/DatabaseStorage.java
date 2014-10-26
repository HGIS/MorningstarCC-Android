package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

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
 * TODO: fix dis
 */
public class DatabaseStorage extends SQLiteOpenHelper implements LocalStorage {

    private static final String TAG = "DatabaseStorage";

    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase db;

    public DatabaseStorage(Context mContext) {
        super(mContext, mContext.getResources().getString(R.string.database_name), null, DATABASE_VERSION);
        this.mContext = mContext;
        this.db = getWritableDatabase();
    }

    @Override
    public Calendar getLastUpdated() {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(getDateLastUpdated());
        }
        catch (NullPointerException e) {
            cal.setTime(new Date(0));
        }

        return cal;
    }

    @Override
    public Cursor get(String from, String... cols) {
        return db.query(from, cols, null, null, null, null, null, null);
    }

    @Override
    public void set(String to, List<ContentValues> data) {
        ArrayList<String> columnNames = new ArrayList<String>();

        for (ContentValues map : data) {
            for (Map.Entry<String, ?> column : map.valueSet()) {
                String key = column.getKey();
                if (!columnNames.contains(key))
                    columnNames.add(key);
            }
        }

        addTable(to, columnNames);
        putValues(to, data);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: drop all tables
    }

    public void close() {
        this.db.close();
    }

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

    private void addTable(String name, ArrayList<String> columns) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");

        sql.append(name);

        sql.append(" (");
        for (String column : columns) {
            sql.append(column);
            sql.append(" TEXT, ");
        }

        sql.deleteCharAt(sql.length() - 2);
        sql.append(");");

        db.execSQL("DROP TABLE IF EXISTS " + name);
        db.execSQL(sql.toString());

        postUpdated();
    }

    private void putValues(String into, List<ContentValues> values) {
        for (ContentValues item : values) {
            db.insert(into, null, item);
        }
    }

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
}

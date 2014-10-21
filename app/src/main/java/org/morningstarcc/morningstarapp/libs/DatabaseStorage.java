package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 10/12/2014.
 */
public class DatabaseStorage extends SQLiteOpenHelper implements LocalStorage {

    private static final String TAG = "DatabaseStorage";

    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase db;

    public DatabaseStorage(Context mContext) {
        super(mContext, mContext.getResources().getString(R.string.database_name), null, DATABASE_VERSION);
        this.mContext = mContext;
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
        db = getReadableDatabase();
        Cursor query = db.query(from, cols, null, null, null, null, null, null);

        return query;
    }

    public void onPostGet() {
        db.close();
    }

    @Override
    public void set(String to, ArrayList<HashMap<String, String>> data) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<String> columnNames = new ArrayList<String>();

        for (HashMap<String, ?> map : data) {
            for (String column : map.keySet()) {
                if (!columnNames.contains(column))
                    columnNames.add(column);
            }
        }

        addTable(db, to, columnNames);
        putValues(db, to, data);

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: drop all tables
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

    private void addTable(SQLiteDatabase db, String name, ArrayList<String> columns) {
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

    private void putValues(SQLiteDatabase db, String into, ArrayList<HashMap<String, String>> values) {
        for (HashMap<String, String> item : values) {
            putValue(db, into, item);
        }
    }

    private void putValue(SQLiteDatabase db, String into, HashMap<String, String> value) {
        // convert HashMap into ContentValues trick from http://njzk2.wordpress.com/2013/05/31/map-to-contentvalues-abusing-parcelable/
        Parcel parcel = Parcel.obtain();
        parcel.writeMap(value);
        parcel.setDataPosition(0);
        ContentValues values = ContentValues.CREATOR.createFromParcel(parcel);

        db.insert(into, null, values);
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

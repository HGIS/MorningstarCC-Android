package org.morningstarcc.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.morningstarcc.app.R;

/**
 * A wrapper class for the SQLiteDatabase. Assists with reading, writing, and modifying the database.
 *
 * This class is a singleton to preserve context. Use withContext(Context) to get an instance.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static Database singleton = null;
    private Context mContext;

    private Database(Context mContext) {
        super(mContext, mContext.getResources().getString(R.string.database_name), null, DATABASE_VERSION);
        this.mContext = mContext;
    }

    // retrieves the singleton instance of this class
    public static Database withContext(Context context) {
        if (singleton == null)
            singleton = new Database(context);

        return singleton;
    }

    /**
     * This method is called when no database file is found on the android device.
     * Since the tables are built dynamically, and the database file is initialized for us,
     *  no tables for core data is needed here.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DELETE TABLE IF EXISTS MCCStudySeriesRSS");
    }

    /**
     * This method is only called when the database on the android device has a version number less than
     *  the one provided in the super constructor call.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // drop all tables (source: http://stackoverflow.com/questions/525512/drop-all-tables-command)
        sqLiteDatabase.execSQL("select 'drop table ' || name || ';' from sqlite_master where type = 'table';");
    }

    /**
     * Gives a specific table from the database that can be written, read, etc.
     *
     * @param tableName the table to access
     * @return  the DatabaseTable representing the given table name.
     */
    public DatabaseTable forTable(String tableName) {
        return new DatabaseTable(mContext, this, tableName);
    }

    /**
     * Gets the name of the table for a given data location String, typically url location, that this
     *  database would use.
     *
     * @param location data location
     * @return the name this database would use to create a table for data at that location
     */
    public static String getTableName(String location) {
        int start = location.lastIndexOf('/'),
            end   = location.lastIndexOf('.'),
            extra = location.indexOf('=');
        String name = location.substring(start + 1, end);

        if (extra >= 0)
            name += location.substring(extra + 1);

        return name;
    }
}

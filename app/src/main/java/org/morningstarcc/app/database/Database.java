package org.morningstarcc.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.morningstarcc.app.data.Connect;
import org.morningstarcc.app.data.Devotion;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.data.Study;

import java.sql.SQLException;

/**
 * A wrapper class for the SQLiteDatabase. Assists with reading, writing, and modifying the database.
 */
public class Database extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Morningstar.db";
    private static final Class[] DATABASE_CLASSES = new Class[]{
            Connect.class, Devotion.class, Event.class, SeriesCategory.class, Series.class, Study.class
    };

    public Database(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for (Class clazz : DATABASE_CLASSES) {
            try {
                TableUtils.createTable(connectionSource, clazz);
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // TODO: drop all the tables
    }
}

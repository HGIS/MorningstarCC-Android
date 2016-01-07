package org.morningstarcc.app.http;

import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Response;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;

import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.database.Database;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyle on 9/25/2015.
 *
 * History:
 * 11/12/2015 - Juan Manuel Gomez - Added thread saving for the daos
 * 11/21/2015 - Juan Manuel Gomez - Added validatio of Series to add SeriesType Manually
 */
public class UpdateDbListener<T extends Parcelable> implements Response.Listener<RssArray> {
    private Class<T> clazz;
    private Database database;
    private AtomicInteger counter;
    private Response.Listener<List<T>> callback;
    private Response.Listener<Integer> counterCallback;
    private String seriesType;

    public UpdateDbListener(Class<T> clazz, Database database, AtomicInteger counter, Response.Listener<List<T>> callback, Response.Listener<Integer> counterCallback, String seriesType) {
        this.clazz = clazz;
        this.database = database;
        this.counter = counter;
        this.callback = callback;
        this.counterCallback = counterCallback;
        this.seriesType = seriesType;
    }

    public UpdateDbListener(Class<T> clazz, Database database, AtomicInteger counter) {
        this(clazz, database, counter, null, null, "");
    }

    public UpdateDbListener(Class<T> clazz, Database database, AtomicInteger counter, Response.Listener<Integer> counterCallback) {
        this(clazz, database, counter, null, counterCallback, "");
    }

    public UpdateDbListener(Class<T> clazz, Database database) {
        this(clazz, database, null, null, null, "");
    }

    public UpdateDbListener(Class<T> clazz, Database database, Response.Listener<List<T>> callback) {
        this(clazz, database, null, callback, null, "");
    }

    public UpdateDbListener(Class<T> clazz, Database database, String seriesType, Response.Listener<List<T>> callback) {
        this(clazz, database, null, callback, null, seriesType);
    }

    @Override
    public void onResponse(RssArray response) {
        final List<T> items = response.convert(clazz);

        //Add Series type to Series
        if (this.seriesType != null && this.seriesType.length() > 0){
            for (T item : items){
                ((Series)item).SeriesType = this.seriesType;
            }
        }

        try {
            final Dao<T, Integer> dao = database.getDao(clazz);

            dao.callBatchTasks(new Callable<Void>() {
                public Void call() throws Exception {
                    final Dao<T, Integer> dao = database.getDao(clazz);
                    for (T item : items){
                        dao.createOrUpdate(item);
                    }
                    return null;
                }
            });

        } catch (SQLException e) {
            Log.e("Database", Log.getStackTraceString(e));
        } catch (Exception e) {
            Log.e("Database", Log.getStackTraceString(e));
        }

        if (counter != null) counterCallback.onResponse(counter.decrementAndGet());
        if (callback != null) callback.onResponse(items);
    }
}

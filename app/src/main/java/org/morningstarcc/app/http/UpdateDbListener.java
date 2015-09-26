package org.morningstarcc.app.http;

import android.util.Log;

import com.android.volley.Response;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.morningstarcc.app.data.Bundlable;
import org.morningstarcc.app.database.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyle on 9/25/2015.
 */
public class UpdateDbListener<T extends Bundlable> implements Response.Listener<RssArray> {
    private Class<T> clazz;
    private Database database;
    private AtomicInteger counter;
    private Response.Listener<List<T>> callback;
    private Response.Listener<Integer> counterCallback;

    public UpdateDbListener(Class<T> clazz, Database database, AtomicInteger counter, Response.Listener<List<T>> callback, Response.Listener<Integer> counterCallback) {
        this.clazz = clazz;
        this.database = database;
        this.counter = counter;
        this.callback = callback;
        this.counterCallback = counterCallback;
    }

    public UpdateDbListener(Class<T> clazz, Database database, AtomicInteger counter) {
        this(clazz, database, counter, null, null);
    }

    public UpdateDbListener(Class<T> clazz, Database database, AtomicInteger counter, Response.Listener<Integer> counterCallback) {
        this(clazz, database, counter, null, counterCallback);
    }

    public UpdateDbListener(Class<T> clazz, Database database) {
        this(clazz, database, null, null, null);
    }

    public UpdateDbListener(Class<T> clazz, Database database, Response.Listener<List<T>> callback) {
        this(clazz, database, null, callback, null);
    }

    @Override
    public void onResponse(RssArray response) {
        List<T> items = response.convert(clazz);
        try {
            Dao<T, Integer> dao = database.getDao(clazz);

            for (T item : items) {
                try {
                    dao.create(item);
                } catch (SQLException e) {
                }
            }
        } catch (SQLException e) {
        }

        if (counter != null) counterCallback.onResponse(counter.decrementAndGet());
        if (callback != null) callback.onResponse(items);
    }
}

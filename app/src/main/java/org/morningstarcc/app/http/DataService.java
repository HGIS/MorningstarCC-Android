package org.morningstarcc.app.http;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Kyle on 9/20/2015.
 */
public class DataService {
    private static RequestQueue queue;

    public static void init(Application context) {
        queue = Volley.newRequestQueue(context);
    }

    @Deprecated
    public static void enqueue(Request request) {
        queue.add(request);
    }

    public static void getEvents() {
    }
}

package org.morningstarcc.app;

import android.app.Application;

import org.morningstarcc.app.http.DataService;

/**
 * Created by Kyle on 9/20/2015.
 */
public class App extends Application {
    public static String PARCEL = "PARCEL";

    @Override
    public void onCreate() {
        super.onCreate();
        DataService.init(this);
    }
}

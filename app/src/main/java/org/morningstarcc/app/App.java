package org.morningstarcc.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import org.morningstarcc.app.http.DataService;

/**
 * Created by Kyle on 9/20/2015.
 * 12/01/2015 - Juan Manuel Gomez - Added Joda Time init
 */
public class App extends Application {
    public static String PARCEL = "PARCEL";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DataService.init(this);
        JodaTimeAndroid.init(this);
    }
}

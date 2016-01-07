package org.morningstarcc.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import org.morningstarcc.app.http.DataService;

/**
 * Created by Kyle on 9/20/2015.
 */
public class App extends Application {
    public static String PARCEL = "PARCEL";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DataService.init(this);
    }
}

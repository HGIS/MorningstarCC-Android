package org.morningstarcc.app;

import com.facebook.stetho.Stetho;

/**
 * Created by Kyle on 9/24/2015.
 */
public class DebugApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

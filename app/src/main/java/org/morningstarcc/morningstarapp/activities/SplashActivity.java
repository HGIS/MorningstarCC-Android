package org.morningstarcc.morningstarapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DataManager;

/**
 * Created by Kyle on 10/26/2014.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Log.d("SplashActivity", "Updater launching...");
        Updater bgUpdater = new Updater(this);

        bgUpdater.update(getString(R.string.series_url));
        bgUpdater.update(getString(R.string.events_url));
        //bgUpdater.update(getString(R.string.devotions_url));
    }

    private class Updater extends DataManager {

        public Updater(Context c) {
            super(c);
        }

        @Override
        public void onDataReturned(boolean success) {
            Log.d("SplashActivity", success ? "Updated" : "Failed to update");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }
}

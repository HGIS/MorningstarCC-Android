package org.morningstarcc.morningstarapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DatabaseUpdater;
import org.morningstarcc.morningstarapp.libs.DownloadUrlContentTask;

/**
 * Created by Kyle on 10/26/2014.
 *
 * TODO: this does not launch anything when airplane mode is on
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("SplashActivity", "Updater launching...");
        updateFeeds();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void updateFeeds() {
        if (DownloadUrlContentTask.hasInternetAccess(this)) {
            new DatabaseUpdater(this).update();
        }
    }
}

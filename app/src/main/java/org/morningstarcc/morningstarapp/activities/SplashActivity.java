package org.morningstarcc.morningstarapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.morningstarcc.morningstarapp.BuildConfig;
import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DatabaseUpdater;
import org.morningstarcc.morningstarapp.libs.DownloadUrlContentTask;

/**
 * Created by Kyle on 10/26/2014.
 */
public class SplashActivity extends Activity {

    private static final int DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        findViewById(R.id.bg).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("SplashActivity", "Updater launching...");
        updateFeeds(); // TODO: change this to something that doesn't return, cause I don't really care here

        // launch the next activity after a delay
        startAfter(new Intent(SplashActivity.this, MainActivity.class), DELAY);
    }

    public void updateFeeds() {
        if (DownloadUrlContentTask.hasInternetAccess(this)) {
            new DatabaseUpdater(this).update();
        }
    }

    private void startAfter(final Intent toStart, final int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(toStart);
                finish();
            }
        }, delay);
    }
}

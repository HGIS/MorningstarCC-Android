package org.morningstarcc.morningstarapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DataManager;
import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

/**
 * Created by Kyle on 10/26/2014.
 */
public class SplashActivity extends Activity {

    private CountingUpdater mUpdater;
    private boolean hasUpdatedStudies;

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
    }

    public void updateFeeds() {
        mUpdater = new CountingUpdater(true);
        hasUpdatedStudies = false;

        mUpdater.update(getString(R.string.series_url));
        new CountingUpdater(false).update(getString(R.string.devotions_url), getString(R.string.events_url)); // TODO: also meh
    }

    public void onFinishedUpdating() {
        if (hasUpdatedStudies) {
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }
        else {
            mUpdater.update(getStudyLinks());
            hasUpdatedStudies = true;
        }
    }

    private String[] getStudyLinks() {
        Cursor seriesIds = new DatabaseStorage(this).get("MCCStudySeriesRSS", "SeriesId");
        String[] ids = new String[seriesIds.getCount()];
        String format = getString(R.string.studies_url).replace("%d", ""); // TODO: meh

        seriesIds.moveToFirst();
        for (int i = 0; !seriesIds.isAfterLast(); i++) {
            ids[i] = format + seriesIds.getString(0);
            seriesIds.moveToNext();
        }

        return ids;
    }

    private class CountingUpdater extends DataManager {
        private int numUpdates;
        private boolean callWhenDone;

        public CountingUpdater(boolean callWhenDone) {
            super(SplashActivity.this);
            this.callWhenDone = callWhenDone;
        }

        public void update(String... from) {
            numUpdates = from.length;

            for (String s : from) {
                if (s != null) {
                    Log.d("SplashActivity", "Pulling from " + s);
                    super.update(s);
                }
                else {
                    numUpdates--;
                }
            }
        }

        // TODO: ewwwwwwww (and above)
        @Override
        public void update(String from) {
            this.update(from, null);
        }

        @Override
        public void onDataReturned(boolean success) {
            if (callWhenDone && --numUpdates == 0)
                onFinishedUpdating();
        }
    }
}

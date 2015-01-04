package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.database.Cursor;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.database.Database;

/**
 * Updates the database with all the feeds it grabs.
 */
public class DatabaseUpdater extends DataManager {
    private int num = 0;
    private boolean isDoneLaunching = false;
    private boolean isNotUpdatingStudies = true;
    private Context mContext;

    public DatabaseUpdater(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    public void update() {
        for (String url : getMainLinks()) {
            super.update(url);
            num++;
        }

        isDoneLaunching = true;
    }

    @Override
    public void onDataReturned(boolean success) {
        if (--num == 0 && isDoneLaunching && isNotUpdatingStudies) {
            isNotUpdatingStudies = false;
            for (String url : getStudyLinks()) {
                super.update(url);
            }
        }
    }

    /**
     * Generic links to grab the RSS feeds from so we can update the database
     */

    private String[] getMainLinks() {
        return new String[]{
                mContext.getString(R.string.series_url),
                mContext.getString(R.string.devotions_url),
                mContext.getString(R.string.events_url),
//                mContext.getString(R.string.connect_url)
        };
    }

    private String[] getStudyLinks() {
        Cursor seriesIds = Database
                .withContext(mContext)
                .forTable("MCCStudySeriesRSS")
                .readAll(new String[]{"SeriesId"})
                .getData();
        String[] ids = new String[seriesIds.getCount()];
        String format = mContext.getString(R.string.studies_url);

        seriesIds.moveToFirst();
        for (int i = 0; !seriesIds.isAfterLast(); i++) {
            ids[i] = format + seriesIds.getString(0);
            seriesIds.moveToNext();
        }

        return ids;
    }
}

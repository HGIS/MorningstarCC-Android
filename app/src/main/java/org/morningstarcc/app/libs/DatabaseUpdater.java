package org.morningstarcc.app.libs;

import android.content.Context;
import android.database.Cursor;

import org.morningstarcc.app.R;
import org.morningstarcc.app.database.Database;

/**
 * Updates the database with all the feeds it grabs.
 */
public class DatabaseUpdater extends DataManager {
    private int num = 0, postponedNum = 0;
    private boolean isDoneLaunching = false;
    private boolean isNotUpdatingSeries = true;
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
        if (--num == 0 && isDoneLaunching && isNotUpdatingSeries) {
            isNotUpdatingSeries = false;
            for (String url : getSeriesLinks()) {
                super.update(url);
                postponedNum++;
            }
        }

        if (num == 0 && --postponedNum == 0 && isDoneLaunching && isNotUpdatingStudies) {
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
        return new String[] {
                mContext.getString(R.string.series_categories_url),
                mContext.getString(R.string.devotions_url),
                mContext.getString(R.string.events_url),
                mContext.getString(R.string.connect_url),
        };
    }

    private String[] getSeriesLinks() {
        Cursor seriesTypes = Database
                .withContext(mContext)
                .forTable("MCCCurrentStudySeriesTypesRSS")
                .readAll(new String[]{"SeriesType"})
                .getData();

        if (seriesTypes == null) return new String[0];

        String[] types = new String[seriesTypes.getCount()];
        String format = mContext.getString(R.string.series_url);

        seriesTypes.moveToFirst();
        for (int i = 0; !seriesTypes.isAfterLast(); i++) {
            types[i] = format + seriesTypes.getString(0);
            seriesTypes.moveToNext();
        }

        return types;
    }

    private String[] getStudyLinks() {
        Cursor seriesIds = Database
                .withContext(mContext)
                .forTable("MCCStudySeriesByTypeRSS")
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

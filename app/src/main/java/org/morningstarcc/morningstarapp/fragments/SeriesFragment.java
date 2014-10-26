package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.SeriesActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.SeriesAdapter;

/**
 * Created by Kyle on 7/18/2014.
 */
public class SeriesFragment extends ListFragment {

    public SeriesFragment() {
        super(SeriesActivity.class, "MCCStudySeriesRSS", R.array.series_fields);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new SeriesAdapter(mContext, data);
    }
}

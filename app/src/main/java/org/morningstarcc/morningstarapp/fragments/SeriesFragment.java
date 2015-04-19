package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.SeriesActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.SeriesAdapter;
import org.morningstarcc.morningstarapp.viewholders.SeriesHolder;

/**
 * Created by Kyle on 7/18/2014.
 */
public class SeriesFragment extends RecyclerFragment {

    public SeriesFragment() {
        super("MCCStudySeriesRSS", R.array.series_fields);
    }

    @Override
    protected DatabaseItemAdapter<SeriesHolder> getAdapter(Bundle[] data) {
        return new SeriesAdapter(mContext, R.layout.series_list_row, data, SeriesActivity.class);
    }
}

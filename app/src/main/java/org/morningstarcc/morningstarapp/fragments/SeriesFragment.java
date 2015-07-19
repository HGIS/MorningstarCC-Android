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
        super("MCCStudySeriesByTypeRSS", R.array.series_fields);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.table += args.getString("SeriesType");
    }

    @Override
    protected DatabaseItemAdapter<SeriesHolder> getAdapter(Bundle[] data) {
        return new SeriesAdapter(getActivity(), R.layout.series_list_row, data, SeriesActivity.class);
    }


}

package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.StudyActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.StudyAdapter;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyFragment extends ListFragment {

    public StudyFragment() {
        super(StudyActivity.class, "MCCStudiesInSeriesRSS", R.array.study_fields);
    }

    public StudyFragment setSeriesId(String seriesId) {
        this.table += seriesId;
        return this;
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new StudyAdapter(mContext, data);
    }
}

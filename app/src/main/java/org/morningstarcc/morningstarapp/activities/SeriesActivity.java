package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.fragments.StudyFragment;

/**
 * Created by Kyle on 10/25/2014.
 */
public class SeriesActivity extends DetailsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_series);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,
                         new StudyFragment().setSeriesId(intent.getStringExtra("SeriesId")))
                .commit();
    }
}

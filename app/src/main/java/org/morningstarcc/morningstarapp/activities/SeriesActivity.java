package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;

/**
 * Created by Kyle on 10/25/2014.
 */
public class SeriesActivity extends DetailsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        // TODO: setContentView(R.layout.activity_series);
    }
}

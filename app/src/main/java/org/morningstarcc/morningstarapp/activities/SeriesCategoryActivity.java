package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.fragments.SeriesCategoryFragment;
import org.morningstarcc.morningstarapp.fragments.SeriesFragment;

public class SeriesCategoryActivity extends DetailsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("SeriesTypeDesc"));
        setContentView(R.layout.activity_series_category);

        SeriesFragment next = new SeriesFragment();

        next.setArguments(intent.getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, next)
                .commit();
    }
}

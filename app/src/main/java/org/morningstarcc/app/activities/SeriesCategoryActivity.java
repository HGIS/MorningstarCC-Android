package org.morningstarcc.app.activities;

import android.os.Bundle;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.fragments.SeriesFragment;

public class SeriesCategoryActivity extends DetailsActivity<SeriesCategory> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(item.SeriesTypeDesc);
        setContentView(R.layout.activity_series_category);

        SeriesFragment next = new SeriesFragment();

        next.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, next)
                .commit();
    }
}

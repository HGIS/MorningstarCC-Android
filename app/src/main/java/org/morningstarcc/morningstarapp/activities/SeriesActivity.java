package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;
import org.morningstarcc.morningstarapp.fragments.StudyFragment;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/25/2014.
 */
public class SeriesActivity extends DetailsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_series);

        setImageLink(this,R.id.image, intent.getStringExtra("Imagelink"));
        setText(this, R.id.title, intent.getStringExtra("title"));
        setText(this, R.id.count, getStudyCount());

        Fragment next = new StudyFragment();

        next.setArguments(intent.getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, next)
                .commit();
    }

    private String getStudyCount() {
        int count = new DatabaseItem(this).get("MCCStudiesInSeriesRSS" + intent.getStringExtra("SeriesId"), R.array.study_fields).length;

        if (count > 0)
            return count + " studies";

        return "";
    }
}

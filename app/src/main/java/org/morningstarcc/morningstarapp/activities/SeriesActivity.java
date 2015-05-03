package org.morningstarcc.morningstarapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.fragments.StudyFragment;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/25/2014.
 */
public class SeriesActivity extends DetailsActivity {

    private static final float ALPHA_CAP = 0.5f;

    private View shadow;
    private View divider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_series);

        setImageLink(this, R.id.image, intent.getStringExtra("Imagelink"));
        setText(this, R.id.title, intent.getStringExtra("title"));
        setText(this, R.id.count, getStudyCount());

        shadow = findViewById(R.id.shadow);
        divider = findViewById(R.id.divider);

        StudyFragment next = new StudyFragment();

        next.setArguments(intent.getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, next)
                .commit();

        // initialize shadow to have 0 alpha
        ViewCompat.setAlpha(shadow, 0);
        divider.getLayoutParams().height = 1;
    }

    private String getStudyCount() {
        int count = Database
                .withContext(this)
                .forTable("MCCStudiesInSeriesRSS" + intent.getStringExtra("SeriesId"))
                .readAll(null)
                .getSize();

        if (count == 1)
            return count + " study";

        if (count > 0)
            return count + " studies";

        return "";
    }

    public void setScroll(int scroll) {
        float alpha = Math.min(scroll / (2.0f * shadow.getHeight()), ALPHA_CAP);
        ViewCompat.setAlpha(shadow, alpha);
        divider.getLayoutParams().height = alpha == 0 ? 1 : 0;
    }
}

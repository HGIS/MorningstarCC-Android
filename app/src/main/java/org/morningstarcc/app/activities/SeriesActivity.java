package org.morningstarcc.app.activities;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;

import org.morningstarcc.app.R;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.fragments.StudyFragment;

import static org.morningstarcc.app.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.app.libs.ViewConstructorUtils.setText;

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
        long count = getStudyCount();

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_series);

        setImageLink(this, R.id.image, intent.getStringExtra("Imagelink"));
        setText(this, R.id.title, intent.getStringExtra("title"));
        setText(this, R.id.count, count + (count == 1 ? " study" : " studies"));

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

    private long getStudyCount() {
        return Math.max(
                Database
                    .withContext(this)
                    .forTable("MCCStudiesInSeriesRSS" + intent.getStringExtra("SeriesId"))
                    .readAll(null)
                    .getSize(),
                0);
    }

    public void setScroll(int scroll) {
        float alpha = Math.min(scroll / (2.0f * shadow.getHeight()), ALPHA_CAP);
        ViewCompat.setAlpha(shadow, alpha);
        divider.getLayoutParams().height = alpha == 0 ? 1 : 0;
    }
}

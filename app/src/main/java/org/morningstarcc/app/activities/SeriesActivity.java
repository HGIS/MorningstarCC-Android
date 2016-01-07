package org.morningstarcc.app.activities;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.data.Study;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.fragments.StudyFragment;

import java.sql.SQLException;

import static org.morningstarcc.app.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.app.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/25/2014.
 */
public class SeriesActivity extends DetailsActivity<Series> {

    private static final float ALPHA_CAP = 0.5f;

    private View shadow;
    private View divider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int count = getStudyCount();

        setTitle(item.title);
        setContentView(R.layout.activity_series);

        setImageLink(this, R.id.image, item.Imagelink);
        setText(this, R.id.title, item.title);
        //setText(this, R.id.count, getResources().getQuantityString(R.plurals.study_counter, count, count));
        setText(this, R.id.count, String.format("%s studies", item.StudyCount));

        shadow = findViewById(R.id.shadow);
        divider = findViewById(R.id.divider);

        StudyFragment next = new StudyFragment();

        next.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, next)
                .commit();

        // initialize shadow to have 0 alpha
        ViewCompat.setAlpha(shadow, 0);
        divider.getLayoutParams().height = 1;
    }

    private int getStudyCount() {
        int count = 0;
        try {
            count = OpenHelperManager.getHelper(this, Database.class).getDao(Study.class).queryForEq("id", item.SeriesId).size();
        } catch (SQLException e) {
        }

        OpenHelperManager.releaseHelper();
        return count;
    }

    public void setScroll(int scroll) {
        float alpha = Math.min(scroll / (2.0f * shadow.getHeight()), ALPHA_CAP);
        ViewCompat.setAlpha(shadow, alpha);
        divider.getLayoutParams().height = alpha == 0 ? 1 : 0;
    }
}

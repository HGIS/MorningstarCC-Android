package org.morningstarcc.morningstarapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;
import org.morningstarcc.morningstarapp.fragments.StudyFragment;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/25/2014.
 */
public class SeriesActivity extends DetailsActivity {

    private boolean running;
    private ShadowAlphaSetter shadowAlphaSetter;
    private View shadow;
    private View divider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_series);

        setImageLink(this,R.id.image, intent.getStringExtra("Imagelink"));
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

        shadowAlphaSetter = new ShadowAlphaSetter();
    }

    @Override
    protected void onResume() {
        running = true;
        shadowAlphaSetter.execute();
        super.onResume();
    }

    @Override
    protected void onPause() {
        running = false;
        super.onPause();
    }

    private String getStudyCount() {
        int count = new DatabaseItem(this).get("MCCStudiesInSeriesRSS" + intent.getStringExtra("SeriesId"), R.array.study_fields).length;

        if (count > 0)
            return count + " studies";

        return "";
    }

    private class ShadowAlphaSetter extends AsyncTask<Void, Void, Void> {

        private Runnable uiChanger;
        private float alpha;

        public ShadowAlphaSetter() {
            super();

            this.uiChanger = new Runnable() {
                @Override
                public void run() {
                    ViewCompat.setAlpha(shadow, alpha);
                    divider.getLayoutParams().height = alpha == 0
                            ? 1
                            : 0;
                }
            };
        }


        @Override
        protected Void doInBackground(Void... voids) {
            while (running) {
                this.alpha = ((StudyFragment) SeriesActivity.this
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.content_frame))
                        .getScroll()
                            / (2.0f * shadow.getHeight());

                Log.d("SeriesActivity", "Setting shadow alpha to: " + alpha);
                uiChanger.run();
                pause();
            }

            return null;
        }

        private void pause() {
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) {
                running = false;
            }
        }
    }
}

package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.Window;

/**
 * Created by Kyle on 10/25/2014.
 */
public class DetailsActivity extends ActionBarActivity {
    protected Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.intent = getIntent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

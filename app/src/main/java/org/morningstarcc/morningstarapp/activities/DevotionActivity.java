package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Kyle on 10/21/2014.
 */
public class DevotionActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        ActionBar titleBar = getSupportActionBar();
        if (titleBar != null) {
            titleBar.setTitle(title);

        }
    }
}

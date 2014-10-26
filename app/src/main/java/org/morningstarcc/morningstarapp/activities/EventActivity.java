package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 10/21/2014.
 */
public class EventActivity extends ActionBarActivity {

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

        setContentView(R.layout.activity_events);
        setDescription(intent.getStringExtra("description"));
        setStartDate(intent.getStringExtra("eventdate"), intent.getStringExtra("eventstarttime"));
        setEndDate(intent.getStringExtra("eventenddate"), intent.getStringExtra("eventendtime"));
        setRegistration(intent.getStringExtra("hasregistration"), intent.getStringExtra("registrationlink"));
    }

    private void setDescription(String description) {
        setText(R.id.description, description);
    }

    private void setStartDate(String date, String time) {
        setText(R.id.start_date, date + " " + time);
    }

    private void setEndDate(String date, String time) {
        setText(R.id.end_date, date + " " + time);
    }

    private void setRegistration(String hasRegistration, String link) {
        if (hasRegistration.equals("true")) {
            setText(R.id.register, link);
        }
    }

    private void setText(int resId, String text) {
        ((TextView) findViewById(resId)).setText(text);
    }
}

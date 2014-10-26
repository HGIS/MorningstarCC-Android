package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 10/21/2014.
 */
public class EventActivity extends DetailsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        setTitle(intent.getStringExtra("title"));
        setText(R.id.description, intent.getStringExtra("description"));
        setText(R.id.start_date, intent.getStringExtra("eventdate") + " " + intent.getStringExtra("eventstarttime"));
        setText(R.id.end_date, intent.getStringExtra("eventenddate") + " " + intent.getStringExtra("eventendtime"));
        if (intent.getStringExtra("hasregistration").equals("true")) {
            setText(R.id.register, intent.getStringExtra("registrationlink"));
        }
    }
}

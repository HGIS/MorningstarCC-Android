package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.RemoteImageView;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDateInterval;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getTimeInterval;

/**
 * Created by Kyle on 10/21/2014.
 */
public class EventActivity extends DetailsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        setup();
    }

    private void setup() {
        Date startDate = getDate(intent.getStringExtra("eventdate"), intent.getStringExtra("eventstarttime"));
        Date endDate = getDate(intent.getStringExtra("eventenddate"), intent.getStringExtra("eventendtime"));

        setTitle(intent.getStringExtra("title"));

        ((RemoteImageView) findViewById(R.id.image)).setImageLink(intent.getStringExtra("imagepath"), null);

        setText(R.id.date, getDateInterval(startDate, endDate));
        setText(R.id.time, getTimeInterval(startDate, endDate));

        setText(R.id.description, intent.getStringExtra("description"));

        if (intent.getStringExtra("hasregistration").equalsIgnoreCase("false")) {
            findViewById(R.id.register).setVisibility(View.INVISIBLE);
        }
    }

    public void onRegister(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("registrationlink"))));
    }
}

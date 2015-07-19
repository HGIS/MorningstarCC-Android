package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.EventAdapter;
import org.morningstarcc.morningstarapp.libs.DateUtils;
import org.morningstarcc.morningstarapp.libs.IntentUtils;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDateInterval;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getFullDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getTimeInterval;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/21/2014.
 */
public class EventActivity extends DetailsActivity {

    public static final String IMAGE = "EVENT_IMAGE";

    private Date startDate, endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        ViewCompat.setTransitionName(findViewById(R.id.image), IMAGE);
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_events_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_share:
                shareEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setup() {
        startDate = getFullDate(intent.getStringExtra("eventstarttime"));
        endDate = getFullDate(intent.getStringExtra("eventendtime"));

        setTitle(Html.fromHtml(intent.getStringExtra("title")).toString());

        String imagePath = intent.getStringExtra("imagepath");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!imagePath.equals(EventAdapter.DEFAULT_IMAGE_PATH)) {
            setImageLink(this, R.id.image, imagePath, R.drawable.logo_event_default, R.drawable.logo_event_default);
        }

        setText(this, R.id.date, getDateInterval(startDate, endDate));
        setText(this, R.id.time, getTimeInterval(startDate, endDate));

        setText(this, R.id.description, intent.getStringExtra("description"));

        if (intent.getStringExtra("hasregistration").equalsIgnoreCase("false")) {
            findViewById(R.id.register).setVisibility(View.GONE);
        }

        disableScrollIfTooSmall(findViewById(R.id.scroll_container));
    }

    public void onRegister(View view) {
        try {
            startActivity(IntentUtils.webIntent(intent.getStringExtra("registrationlink")));
        }
        catch (Exception e) {
            Log.e("EventActivity", Log.getStackTraceString(e));
        }
    }

    public void onViewMoreDetails(View view) {
        startActivity(IntentUtils.webIntent(intent.getStringExtra("weblink")));
    }


    private void disableScrollIfTooSmall(View view) {
        if (view.getHeight() <= getWindow().getDecorView().getHeight()) {
            view.setScrollContainer(false);
        }
    }

    private void shareEvent() {
        Intent shareIntent = IntentUtils.shareIntent("Join me on " + DateUtils.getFullDayString(startDate) +
                " for " + intent.getStringExtra("title") +
                ". See more at: " + intent.getStringExtra("weblink"));

        startActivity(shareIntent);
    }

    public void addEventToCalendar(View view) {
        Intent calendarIntent = IntentUtils.calendarIntent(startDate, endDate, intent.getStringExtra("title"), intent.getStringExtra("description"));
        startActivity(calendarIntent);
    }
}

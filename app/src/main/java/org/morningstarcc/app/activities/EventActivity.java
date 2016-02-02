package org.morningstarcc.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.morningstarcc.app.R;
import org.morningstarcc.app.adapters.EventAdapter;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.libs.DateUtils;
import org.morningstarcc.app.libs.IntentUtils;

import java.util.Date;

import static org.morningstarcc.app.libs.DateUtils.getDateInterval;
import static org.morningstarcc.app.libs.DateUtils.getFullDate;
import static org.morningstarcc.app.libs.DateUtils.getTimeInterval;
import static org.morningstarcc.app.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.app.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/21/2014.
 */
public class EventActivity extends DetailsActivity<Event> {
    private static final String TAG = EventActivity.class.getSimpleName();

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
            // in case we ever want to implement directions in
//            case R.id.action_directions:
//                getDirections();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setup() {
        startDate = getFullDate(item.eventstarttime);
        endDate = getFullDate(item.eventendtime);

        setTitle(Html.fromHtml(item.title).toString());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!item.imagepath.equals(EventAdapter.DEFAULT_IMAGE_PATH)) {
            setImageLink(this, R.id.image, item.imagepath, R.drawable.logo_event_default, R.drawable.logo_event_default);
        }

        setText(this, R.id.date, getDateInterval(startDate, endDate));
        setText(this, R.id.time, getTimeInterval(startDate, endDate));

        setText(this, R.id.description, item.description);

        if (!Boolean.parseBoolean(item.hasregistration)) {
            findViewById(R.id.register).setVisibility(View.GONE);
        }

        disableScrollIfTooSmall(findViewById(R.id.scroll_container));
    }

    public void onRegister(View view) {
        try {
            startActivity(IntentUtils.webIntent(item.registrationlink));
        }
        catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void onViewMoreDetails(View view) {
        startActivity(IntentUtils.webIntent(item.weblink));
    }


    private void disableScrollIfTooSmall(View view) {
        if (view.getHeight() <= getWindow().getDecorView().getHeight()) {
            view.setScrollContainer(false);
        }
    }

//    private void getDirections() {
//        Intent mapIntent = IntentUtils.googleMapsIntent(item.eventlocation);
//        startActivity(mapIntent);
//    }

    private void shareEvent() {
        Intent shareIntent = IntentUtils.shareIntent(getString(R.string.share_event_format, DateUtils.getFullDayString(startDate), item.title, item.weblink));
        startActivity(shareIntent);
    }

    public void addEventToCalendar(View view) {
        Intent calendarIntent = IntentUtils.calendarIntent(startDate, endDate, item.title, item.description);
        startActivity(calendarIntent);
    }
}

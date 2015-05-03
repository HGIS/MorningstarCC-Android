package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.RelativeLayout;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.EventAdapter;
import org.morningstarcc.morningstarapp.intents.CalendarIntent;
import org.morningstarcc.morningstarapp.intents.ShareIntent;
import org.morningstarcc.morningstarapp.intents.WebViewIntent;
import org.morningstarcc.morningstarapp.libs.DateUtils;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getFullDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDateInterval;
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
            setImageLink(this, R.id.image, imagePath, R.drawable.default_event, R.drawable.default_event);

            View floatingActionButton = findViewById(R.id.fab);
            ((ViewManager) floatingActionButton.getParent()).removeView(floatingActionButton);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) floatingActionButton.getLayoutParams();

            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.image);
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -30, getResources().getDisplayMetrics()));

            ((ViewGroup) findViewById(R.id.event_container)).addView(floatingActionButton, params);
        }
        else
            findViewById(R.id.image).getLayoutParams().height = 0;

        setText(this, R.id.date, getDateInterval(startDate, endDate));
        setText(this, R.id.time, getTimeInterval(startDate, endDate));

        setText(this, R.id.description, intent.getStringExtra("description"));

        if (intent.getStringExtra("hasregistration").equalsIgnoreCase("false")) {
            findViewById(R.id.register).setVisibility(View.INVISIBLE);
        }

        disableScrollIfTooSmall(findViewById(R.id.scroll_container));
    }

    public void onRegister(View view) {
        try {
            startActivity(WebViewIntent.build(intent.getStringExtra("registrationlink")));
        }
        catch (Exception e) {
            Log.e("EventActivity", Log.getStackTraceString(e));
        }
    }

    public void onViewMoreDetails(View view) {
        startActivity(WebViewIntent.build(intent.getStringExtra("weblink")));
    }


    private void disableScrollIfTooSmall(View view) {
        if (view.getHeight() <= getWindow().getDecorView().getHeight()) {
            view.setScrollContainer(false);
        }
    }

    private void shareEvent() {
        Intent shareIntent = ShareIntent.build("Join me on " + DateUtils.getFullDayString(startDate) +
                " for " + intent.getStringExtra("title") +
                ". See more at: " + intent.getStringExtra("weblink"));

        startActivity(shareIntent);
    }

    public void addEventToCalendar(View view) {
        Intent calendarIntent = CalendarIntent.build(startDate, endDate, intent.getStringExtra("title"), intent.getStringExtra("description"));
        startActivity(calendarIntent);
    }

    public int dpToPx(int dp) {
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}

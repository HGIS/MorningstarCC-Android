package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.EventAdapter;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDateInterval;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getTimeInterval;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/21/2014.
 */
public class EventActivity extends DetailsActivity {

    private Date startDate, endDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
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
            case R.id.action_add_to_calendar:
                addEventToCalendar(startDate, endDate);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setup() {
        startDate = getDate(intent.getStringExtra("eventdate"), intent.getStringExtra("eventstarttime"));
        endDate = getDate(intent.getStringExtra("eventenddate"), intent.getStringExtra("eventendtime"));

        setTitle(intent.getStringExtra("title"));

        String imagePath = intent.getStringExtra("imagepath");
        if (!imagePath.equals(EventAdapter.DEFAULT_IMAGE_PATH))
            setImageLink(this, R.id.image, imagePath);
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
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("registrationlink"))));
        }
        catch (Exception e) {
            Log.e("EventActivity", Log.getStackTraceString(e));
        }
    }

    private void disableScrollIfTooSmall(View view) {
        if (view.getHeight() <= getWindow().getDecorView().getHeight()) {
            view.setScrollContainer(false);
        }
    }

    private void addEventToCalendar(Date startDate, Date endDate) {
        Intent calendarIntent = new Intent(Intent.ACTION_EDIT);

        calendarIntent.setType("vnd.android.cursor.item/event");
        calendarIntent.putExtra("beginTime", startDate.getTime());
        calendarIntent.putExtra("allDay", false);
        calendarIntent.putExtra("endTime", endDate.getTime());
        calendarIntent.putExtra("title", intent.getStringExtra("title"));
        calendarIntent.putExtra("description", intent.getStringExtra("description"));

        startActivity(Intent.createChooser(calendarIntent, "Add this event to:"));
    }
}

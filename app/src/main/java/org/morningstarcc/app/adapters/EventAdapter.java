package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.viewholders.EventHolder;

import java.util.ArrayList;
import java.util.Date;

import static org.morningstarcc.app.libs.DateUtils.getDateInterval;
import static org.morningstarcc.app.libs.DateUtils.getFullDate;
import static org.morningstarcc.app.libs.DateUtils.getTimeInterval;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventAdapter extends DatabaseItemAdapter<Event, EventHolder> {

    public static final String DEFAULT_IMAGE_PATH = "http://www.morningstarcc.org";

    public EventAdapter(Activity mActivity, int layout, Event[] events, Class<? extends Activity> nextActivity) {
        super(mActivity, layout, trim(events), nextActivity);
    }

    // We only want Featured events to be listed here
    private static Event[] trim(Event[] toTrim) {
        ArrayList<Event> trimmed = new ArrayList<>();

        for (Event event : toTrim) {
            String imagePath = event.imagepath;
            if (imagePath != null && !imagePath.equals(DEFAULT_IMAGE_PATH))
                trimmed.add(event);
        }

        return trimmed.toArray(new Event[trimmed.size()]);
    }

    @Override
    protected void setupView(EventHolder holder, int position) {
        Event event = data[position];

        Date startDate = TextUtils.isEmpty(event.eventstarttime) ? null : getFullDate(event.eventstarttime);
        Date endDate = TextUtils.isEmpty(event.eventendtime) ? null : getFullDate(event.eventendtime);

        if (!TextUtils.isEmpty(event.imagepath)) {
            Picasso
                    .with(mActivity)
                    .load(event.imagepath)
                    .placeholder(R.drawable.logo_event_default)
                    .into(holder.image);
        } else {
            Picasso
                    .with(mActivity)
                    .load(R.drawable.logo_event_default)
                    .into(holder.image);
        }

        if (startDate != null && endDate != null) {
            holder.date.setText(getDateInterval(startDate, endDate));
            holder.time.setText(getTimeInterval(startDate, endDate));
        } else {
            holder.date.setVisibility(View.GONE);
            holder.time.setVisibility(View.GONE);
        }
    }

    @Override
    protected EventHolder getViewHolder(View view) {
        return new EventHolder(view);
    }
}

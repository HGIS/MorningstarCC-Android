package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.morningstarcc.app.R;
import org.morningstarcc.app.viewholders.EventHolder;

import java.util.ArrayList;
import java.util.Date;

import static org.morningstarcc.app.libs.DateUtils.getDateInterval;
import static org.morningstarcc.app.libs.DateUtils.getFullDate;
import static org.morningstarcc.app.libs.DateUtils.getTimeInterval;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventAdapter extends DatabaseItemAdapter<EventHolder> {

    public static final String DEFAULT_IMAGE_PATH = "http://www.morningstarcc.org";

    public EventAdapter(Activity mActivity, int layout, Bundle[] events, Class<? extends Activity> nextActivity) {
        super(mActivity, layout, trim(events), nextActivity);
    }

    @Override
    protected void setupView(EventHolder holder, int position) {
        Bundle event = data[position];

        String startDateString = event.getString("eventstarttime");
        String endDateString = event.getString("eventendtime");
        String image = event.getString("imagepath");

        Date startDate = TextUtils.isEmpty(startDateString) ? null : getFullDate(startDateString);
        Date endDate = TextUtils.isEmpty(endDateString) ? null : getFullDate(endDateString);

        if (!TextUtils.isEmpty(image)) {
            Picasso
                    .with(mActivity)
                    .load(image)
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

    // We only want Featured events to be listed here
    private static Bundle[] trim(Bundle[] toTrim) {
        ArrayList<Bundle> trimmed = new ArrayList<Bundle>();

        for (Bundle event : toTrim) {
            String imagePath = event.getString("imagepath");
            if (imagePath != null && !imagePath.equals(DEFAULT_IMAGE_PATH))
                trimmed.add(event);
        }

        return trimmed.toArray(new Bundle[trimmed.size()]);
    }

    @Override
    protected EventHolder getViewHolder(View view) {
        return new EventHolder(view);
    }
}

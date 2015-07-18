package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.viewholders.EventHolder;

import java.util.ArrayList;
import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDateInterval;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getFullDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getTimeInterval;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

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

        Date startDate = getFullDate(event.getString("eventstarttime"));
        Date endDate = getFullDate(event.getString("eventendtime"));

        Picasso
                .with(mActivity)
                .load(event.getString("imagepath"))
                .placeholder(R.drawable.logo_event_default)
                .into(holder.image);

        holder.date.setText(getDateInterval(startDate, endDate));
        holder.time.setText(getTimeInterval(startDate, endDate));
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

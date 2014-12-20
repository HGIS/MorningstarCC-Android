package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;

import java.util.ArrayList;
import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.*;
import static org.morningstarcc.morningstarapp.libs.DateUtils.*;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventAdapter extends DatabaseItemAdapter {

    private static final String DEFAULT_IMAGE_PATH = "http://www.morningstarcc.org";

    public EventAdapter(Context mContext, Bundle[] events) {
        super(mContext, R.layout.event_list_row, trim(events));
    }

    @Override
    protected void setupView(View root, int position) {
        Bundle event = data[position];

        Date startDate = getDate(event.getString("eventdate"), event.getString("eventstarttime"));
        Date endDate = getDate(event.getString("eventenddate"), event.getString("eventendtime"));

        android.util.Log.e("EVENTS", event.getString("imagepath"));
        setImageLink(mContext, root, R.id.image, event.getString("imagepath"));
        setText(root, R.id.date, getDateInterval(startDate, endDate));
        setText(root, R.id.time, getTimeInterval(startDate, endDate));
    }

    private static Bundle[] trim(Bundle[] toTrim) {
        ArrayList<Bundle> trimmed = new ArrayList<Bundle>();

        for (Bundle event : toTrim) {
            String imagePath = event.getString("imagepath");
            if (imagePath != null && !imagePath.equals(DEFAULT_IMAGE_PATH))
                trimmed.add(event);
        }

        return trimmed.toArray(new Bundle[trimmed.size()]);
    }
}

package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;
import org.morningstarcc.morningstarapp.libs.RemoteImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 10/10/2014.
 */
public class EventAdapter extends DatabaseItemAdapter {

    private static SimpleDateFormat MONTH_DAY = new SimpleDateFormat("MMM d");
    private static SimpleDateFormat TIME = new SimpleDateFormat("h:mm a");

    public EventAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.event_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        Date startDate = DatabaseItem.getDate(data[position].getString("eventdate"), data[position].getString("eventstarttime"));
        Date endDate = DatabaseItem.getDate(data[position].getString("eventenddate"), data[position].getString("eventendtime"));

        ((RemoteImageView) root.findViewById(R.id.image)).setImageLink(data[position].getString("imagepath"));
        setText(root, R.id.start_date, MONTH_DAY.format(startDate));
        setText(root, R.id.start_time, TIME.format(startDate));
        setText(root, R.id.end_date, MONTH_DAY.format(endDate));
        setText(root, R.id.end_time, TIME.format(endDate));
        setText(root, R.id.location, "Um, no location provided?");
    }
}

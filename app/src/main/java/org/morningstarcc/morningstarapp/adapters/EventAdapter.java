package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.RemoteImageView;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.*;

/**
 * Created by Kyle on 10/10/2014.
 */
public class EventAdapter extends DatabaseItemAdapter {

    public EventAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.event_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        Date startDate = getDate(data[position].getString("eventdate"), data[position].getString("eventstarttime"));
        Date endDate = getDate(data[position].getString("eventenddate"), data[position].getString("eventendtime"));

        ((RemoteImageView) root.findViewById(R.id.image)).setImageLink(data[position].getString("imagepath"));

        setText(root, R.id.date, getDateInterval(startDate, endDate));
        setText(root, R.id.time, getTimeInterval(startDate, endDate));
    }
}

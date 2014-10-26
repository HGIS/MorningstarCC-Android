package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 10/10/2014.
 */
public class EventAdapter extends DatabaseItemAdapter {

    public EventAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.event_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        Date date = DatabaseItem.getDate(data[position].getString("eventdate"), data[position].getString("eventstarttime"));

        ((TextView) root.findViewById(R.id.month)).setText( new SimpleDateFormat("MMM").format(date) );
        ((TextView) root.findViewById(R.id.day)).setText( new SimpleDateFormat("d").format(date) );
        ((TextView) root.findViewById(R.id.title)).setText(data[position].getString("title"));
    }
}

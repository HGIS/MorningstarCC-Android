package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.Event;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 10/10/2014.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private LayoutInflater mInflater;
    private Event[] data;

    public EventAdapter(Context mContext, Event[] data) {
        super(mContext, R.layout.events_list_row, data);
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.events_list_row, parent, false);
        }

        setMonth(convertView, data[position].start);
        setDay(convertView, data[position].start);
        setTitle(convertView, data[position].title);

        return convertView;
    }

    private void setMonth(View root, Date month) {
        ((TextView) root.findViewById(R.id.month)).setText( new SimpleDateFormat("MMM").format(month) );
    }

    private void setDay(View root, Date day) {
        ((TextView) root.findViewById(R.id.day)).setText( new SimpleDateFormat("d").format(day) );
    }

    private void setTitle(View root, String title) {
        ((TextView) root.findViewById(R.id.title)).setText(title);
    }
}

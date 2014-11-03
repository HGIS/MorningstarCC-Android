package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.EventActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.EventAdapter;

/**
 * Created by Kyle on 7/19/2014.
 */
public class EventFragment extends ListFragment {

    public EventFragment() {
        super(EventActivity.class, "MCCEventsRSS", R.array.event_fields);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new EventAdapter(mContext, data);
    }
}

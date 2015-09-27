package org.morningstarcc.app.fragments;

import android.os.Bundle;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.ExpandableEventAdapter;
import org.morningstarcc.app.data.Event;

/**
 * Created by Kyle on 7/19/2014.
 */
public class ExpandableEventFragment extends RecyclerFragment<Event> {

    public ExpandableEventFragment() {
        super(Event.class);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Event[] data) {
        return new ExpandableEventAdapter(getActivity(), R.layout.expandable_event_header_row, R.layout.expandable_event_list_row, data, EventActivity.class);
    }
}

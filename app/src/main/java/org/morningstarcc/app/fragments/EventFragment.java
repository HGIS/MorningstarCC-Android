package org.morningstarcc.app.fragments;

import android.os.Bundle;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.EventAdapter;
import org.morningstarcc.app.data.Event;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventFragment extends RecyclerFragment<Event> {

    public EventFragment() {
        super(Event.class);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Event[] data) {
        return new EventAdapter(getActivity(), R.layout.event_list_row, data, EventActivity.class);
    }
}

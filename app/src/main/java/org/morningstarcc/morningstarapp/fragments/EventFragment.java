package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.EventActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.EventAdapter;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventFragment extends RecyclerFragment {

    public EventFragment() {
        super("MCCEventsRSS", R.array.event_fields);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new EventAdapter(getActivity(), R.layout.event_list_row, data, EventActivity.class);
    }
}

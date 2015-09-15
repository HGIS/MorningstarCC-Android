package org.morningstarcc.app.fragments;

import android.os.Bundle;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.EventAdapter;

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

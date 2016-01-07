package org.morningstarcc.app.fragments;

import android.os.Bundle;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.EventAdapter;
import org.morningstarcc.app.data.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventFragment extends RecyclerFragment<Event> {

    public EventFragment() {
        super(Event.class);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Event[] data) {
        //Filter events by removing the calendar only events
        List<Event> filterEvents = new ArrayList<>();
        for (Event event : data){
            if (!event.calendarOnly.toLowerCase(Locale.US).equals("true")){
                filterEvents.add(event);
            }
        }
        return new EventAdapter(getActivity(), R.layout.event_list_row, filterEvents.toArray(new Event[filterEvents.size()]), EventActivity.class);
    }
}

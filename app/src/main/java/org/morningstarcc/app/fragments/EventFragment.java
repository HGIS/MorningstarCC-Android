package org.morningstarcc.app.fragments;

import android.support.v4.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.EventAdapter;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.http.DataService;
import org.morningstarcc.app.libs.DateUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by whykalo on 12/20/2014.
 * 12/01/2015 - Juan Manuel Gomez - Added refresh functionality
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

    private void refreshData(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Database database = OpenHelperManager.getHelper(getActivity(), Database.class);
                try {
                    Dao<Event, Integer> dao2 = database.getDao(Event.class);
                    recyclerView.removeAllViews();
                    recyclerView.setAdapter(getAdapter(fetchData(dao2)));
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    public void customInit(){

        enableSwipe(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataService.updateEvents(getActivity(), new Response.Listener<Void>() {
                    @Override
                    public void onResponse(Void response) {

                        //Delete data
                        Database database = OpenHelperManager.getHelper(getActivity(), Database.class);
                        Dao<Event, Integer> dao;
                        try {
                            dao = database.getDao(Event.class);
                            DateTime currentDate = new DateTime();
                            for (Event item : dao) {
                                DateTime eventDate = new DateTime(DateUtils.getFullDate(item.eventstarttime));
                                int days = Days.daysBetween(currentDate.toDateMidnight(), eventDate.toDateMidnight()).getDays();
                                if (days < 0) {
                                    dao.delete(item);
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        refreshData();
                        endRefresh();

                    }
                });

            }
        });

    }
}

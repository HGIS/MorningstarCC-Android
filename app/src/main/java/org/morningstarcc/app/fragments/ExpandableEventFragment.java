package org.morningstarcc.app.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.ExpandableEventAdapter;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.http.DataService;
import org.morningstarcc.app.libs.DateUtils;

import java.sql.SQLException;

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

package org.morningstarcc.app.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.EventActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.ExpandableEventAdapter;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.http.DataService;
import org.morningstarcc.app.libs.DateUtils;

import java.sql.SQLException;
import java.util.Calendar;

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

    private void refreshData() {
        try {
            Database database = OpenHelperManager.getHelper(getActivity(), Database.class);
            Dao<Event, Integer> dao2 = database.getDao(Event.class);
            final DatabaseItemAdapter adapter = getAdapter(fetchData(dao2));

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    recyclerView.removeAllViews();
                    recyclerView.setAdapter(adapter);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void customInit(){
        enableSwipe(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataService.updateEvents(getActivity(), new Response.Listener<Void>() {
                    @Override
                    public void onResponse(Void response) {
                        Database database = OpenHelperManager.getHelper(getActivity(), Database.class);

                        try {
                            Dao<Event, Integer> dao = database.getDao(Event.class);
                            for (Event item : dao) {
                                Calendar date = Calendar.getInstance();
                                date.setTime(DateUtils.getFullDate(item.eventstarttime));

                                if (Calendar.getInstance().after(date)) dao.delete(item);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        refreshData();
                        endRefresh();

                        OpenHelperManager.releaseHelper();
                    }
                });
            }
        });
    }
}

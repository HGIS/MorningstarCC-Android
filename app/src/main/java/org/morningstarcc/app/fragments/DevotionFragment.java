package org.morningstarcc.app.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.DevotionActivity;
import org.morningstarcc.app.adapters.DevotionAdapter;
import org.morningstarcc.app.data.Devotion;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.http.DataService;
import org.morningstarcc.app.libs.DateUtils;

import java.sql.SQLException;

/**
 * Created by Kyle on 7/19/2014.
 */
public class DevotionFragment extends RecyclerFragment<Devotion> {

    public DevotionFragment() {
        super(Devotion.class);
    }

    @Override
    protected RecyclerView.Adapter getAdapter(Devotion[] data) {
        return new DevotionAdapter(getActivity(), R.layout.devotion_list_row, data, DevotionActivity.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }

    private void refreshData(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Database database = OpenHelperManager.getHelper(getActivity(), Database.class);
                try {
                    Dao<Devotion, Integer> dao2 = database.getDao(Devotion.class);
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
                //Delete all data
                Database database = OpenHelperManager.getHelper(getActivity(), Database.class);
                Dao<Devotion, Integer> dao;
                try {
                    dao = database.getDao(Devotion.class);
                    for (Devotion item : dao) {
                        dao.delete(item);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Get new data
                DataService.updateDevotion(getActivity(), new Response.Listener<Void>() {
                    @Override
                    public void onResponse(Void response) {
                        refreshData();
                        endRefresh();

                    }
                });

            }
        });

    }
}

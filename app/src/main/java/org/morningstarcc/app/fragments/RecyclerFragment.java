package org.morningstarcc.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.morningstarcc.app.R;
import org.morningstarcc.app.database.Database;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kyle on 4/16/2015.
 * 12/01/2015 - Juan Manuel Gomez - Added refresh functionality
 */
public abstract class RecyclerFragment<T extends Parcelable> extends Fragment {
    private Toolbar toolbar;
    private Class<T> clazz;

    protected RecyclerView.Adapter adapter;
    protected String id;

    protected RecyclerFragment(Class<T> clazz, String id) {
        this.clazz = clazz;
        this.id = id;
    }

    protected RecyclerFragment(Class<T> clazz) {
        this.clazz = clazz;
    }

    private SwipeRefreshLayout swipeRefreshLayout;

    public RecyclerView recyclerView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    // @return this to allow for setting up toolbar during initialization
    public RecyclerFragment setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.generic_recycler, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        try {
            Dao<T, Integer> dao = OpenHelperManager.getHelper(getActivity(), Database.class).getDao(clazz);

            adapter = getAdapter(fetchData(dao));
            recyclerView.setAdapter(adapter);

        } catch (SQLException ignored) {
            recyclerView.setAdapter(adapter = getAdapter(null));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        OpenHelperManager.releaseHelper();

        if (toolbar != null)
            recyclerView.setOnScrollListener(new OnScrollToolbarHider());

        if (adapter.getItemCount() == 0)
            rootView.findViewById(R.id.empty_list).setVisibility(View.VISIBLE);

        //Get reference of SwipeRefreshLayout control
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe);

        //Set default value false
        swipeRefreshLayout.setEnabled(false);

        customInit();

        return rootView;
    }

    protected T[] fetchData(Dao<T, Integer> dao) throws SQLException {
        List<T> data = id == null ? dao.queryForAll() : dao.queryForEq("id", id);

        try {
            return data.toArray(((Parcelable.Creator<T>) clazz.getDeclaredField("CREATOR").get(null)).newArray(data.size()));
        } catch (Exception e) {}

        return null;
    }

    protected abstract RecyclerView.Adapter getAdapter(T[] data);

    public class OnScrollToolbarHider extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            ViewCompat.setTranslationY(toolbar, dy);
        }
    }

    public void customInit(){

    }

    public void enableSwipe(SwipeRefreshLayout.OnRefreshListener listener){
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(listener);

    }

    public void endRefresh(){
        swipeRefreshLayout.setRefreshing(false);
    }
}

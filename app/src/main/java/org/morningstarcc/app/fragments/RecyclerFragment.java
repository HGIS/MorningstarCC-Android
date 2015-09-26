package org.morningstarcc.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
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
 */
public abstract class RecyclerFragment<T> extends Fragment {
    protected RecyclerView.Adapter adapter;
    private Toolbar toolbar;
    private Class<T> clazz;
    private Integer id;

    protected RecyclerFragment(Class<T> clazz, Integer id) {
        this.clazz = clazz;
        this.id = id;
    }

    protected RecyclerFragment(Class<T> clazz) {
        this.clazz = clazz;
    }

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
        try {
            Dao<T, Integer> dao = OpenHelperManager.getHelper(getActivity(), Database.class).getDao(clazz);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

            adapter = getAdapter(id == null ? dao.queryForAll() : dao.queryForEq("id", id));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            if (toolbar != null)
                recyclerView.setOnScrollListener(new OnScrollToolbarHider());

            if (adapter.getItemCount() == 0)
                rootView.findViewById(R.id.empty_list).setVisibility(View.VISIBLE);

            OpenHelperManager.releaseHelper();
        } catch (SQLException ignored) {
        }

        return rootView;
    }

    protected abstract RecyclerView.Adapter getAdapter(List<T> data);

    public class OnScrollToolbarHider extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            ViewCompat.setTranslationY(toolbar, dy);
        }
    }
}

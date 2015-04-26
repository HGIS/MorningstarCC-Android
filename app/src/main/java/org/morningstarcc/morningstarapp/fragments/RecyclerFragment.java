package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.database.Database;

/**
 * Created by Kyle on 4/16/2015.
 */
public abstract class RecyclerFragment extends Fragment {

    protected Context mContext;
    protected RecyclerView.Adapter adapter;
    protected String table;
    private int arrayResId;
    private Toolbar toolbar;

    protected RecyclerFragment(String table, int arrayResId) {
        this.table = table;
        this.arrayResId = arrayResId;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    // @return this to allow for setting up toolbar during initialization
    public RecyclerFragment setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.generic_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        Bundle[] data = Database
                .withContext(mContext)
                .forTable(table)
                .readAll(arrayResId)
                .asBundleArray();

        adapter = getAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        if (toolbar != null)
            recyclerView.setOnScrollListener(new OnScrollToolbarHider());

        return rootView;
    }

    protected abstract RecyclerView.Adapter getAdapter(Bundle[] data);

    public class OnScrollToolbarHider extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            ViewCompat.setTranslationY(toolbar, dy);
        }
    }
}

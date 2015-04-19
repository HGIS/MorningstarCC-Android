package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.adapters.StudyAdapter;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyFragment extends RecyclerFragment {

    private int scroll = 0;

    public StudyFragment() {
        super("MCCStudiesInSeriesRSS", R.array.study_fields);
    }

    @Override
    public void setArguments(Bundle args) {
        this.table += args.getString("SeriesId");
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new StudyAdapter(getActivity(), R.layout.study_list_row, data, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.generic_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        Bundle[] data = Database
                .withContext(mContext)
                .forTable(table)
                .readAll(R.array.study_fields)
                .asBundleArray();

        scroll = 0;
        adapter = getAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnScrollListener(new RecyclerScrollListener());

        return rootView;
    }

    private class RecyclerScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scroll += dy;
        }
    }

    public int getScroll() {
        return scroll;
    }
}

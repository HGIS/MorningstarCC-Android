package org.morningstarcc.app.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.app.App;
import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.SeriesActivity;
import org.morningstarcc.app.activities.StudyActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.StudyAdapter;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.data.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyFragment extends RecyclerFragment<Study> {

    private int scroll = 0;

    private String seriesId;

    public StudyFragment() {
        super(Study.class);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        //TODO: get the real id
        this.id = args.getString("SeriesId");

        if (args.containsKey(App.PARCEL)) {
            Series temp = (Series) args.get(App.PARCEL);
            seriesId = temp.SeriesId;
        }
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Study[] data) {
        //Filter the data
        List<Study> realData = new ArrayList<>();
        for (Study study : data) {
            if (study.SeriesId.equals(seriesId)) {
                realData.add(study);
            }
        }
        return new StudyAdapter(getActivity(), R.layout.study_list_row, realData.toArray(new Study[realData.size()]), StudyActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        scroll = 0;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnScrollListener(new RecyclerScrollListener());

        return rootView;
    }

    private class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scroll += dy;

            ((SeriesActivity) getActivity()).setScroll(scroll);
        }
    }
}

package org.morningstarcc.morningstarapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.StudyAdapter;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyFragment extends ListFragment {
    private static String VIDEO_LINK = "vnd.youtube://";

    public StudyFragment() {
        super(null, "MCCStudiesInSeriesRSS", R.array.study_fields);
    }

    public StudyFragment setSeriesId(String seriesId) {
        this.table += seriesId;
        return this;
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new StudyAdapter(mContext, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        ((ListView) root.findViewById(R.id.list)).setOnItemClickListener(new StudyItemClickListener());

        return root;
    }

    private class StudyItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String videoId = getVideoId(((Bundle) adapter.getItem(position)).getString("VideoLink"));
            Intent tostart = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_LINK + videoId));

            startActivity(Intent.createChooser(tostart, "Complete action using"));
        }
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}

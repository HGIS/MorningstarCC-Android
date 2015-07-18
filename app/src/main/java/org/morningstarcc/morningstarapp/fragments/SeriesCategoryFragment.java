package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.SeriesActivity;
import org.morningstarcc.morningstarapp.activities.SeriesCategoryActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.SeriesAdapter;
import org.morningstarcc.morningstarapp.adapters.SeriesCategoryAdapter;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.viewholders.SeriesCategoryHolder;
import org.morningstarcc.morningstarapp.viewholders.SeriesHolder;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesCategoryFragment extends RecyclerFragment {
    public SeriesCategoryFragment() {
        super("MCCCurrentStudySeriesTypesRSS", R.array.series_category_fields);
    }

    @Override
    protected DatabaseItemAdapter<SeriesCategoryHolder> getAdapter(Bundle[] data) {
        return new SeriesCategoryAdapter(getActivity(), R.layout.series_category_list_row, data, SeriesCategoryActivity.class);
    }

    @Override
    protected Bundle[] fetchData(int arrayResId) {
        Bundle[] unsorted = super.fetchData(arrayResId);

        Arrays.sort(unsorted, new Comparator<Bundle>() {
            @Override
            public int compare(Bundle lhs, Bundle rhs) {
                return lhs.getString("SeriesTypeSortOrder").compareTo(rhs.getString("SeriesTypeSortOrder"));
            }
        });

        return unsorted; // because its sorted now!
    }
}

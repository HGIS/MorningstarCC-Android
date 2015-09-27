package org.morningstarcc.app.fragments;

import android.support.v4.app.Fragment;

import com.j256.ormlite.dao.Dao;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.SeriesCategoryActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.SeriesCategoryAdapter;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.viewholders.SeriesCategoryHolder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesCategoryFragment extends RecyclerFragment<SeriesCategory> {
    public SeriesCategoryFragment() {
        super(SeriesCategory.class);
    }

    @Override
    protected DatabaseItemAdapter<SeriesCategory, SeriesCategoryHolder> getAdapter(SeriesCategory[] data) {
        return new SeriesCategoryAdapter(getActivity(), R.layout.series_category_list_row, data, SeriesCategoryActivity.class);
    }

    @Override
    protected SeriesCategory[] fetchData(Dao<SeriesCategory, Integer> dao) throws SQLException {
        SeriesCategory[] unsorted = super.fetchData(dao);

        Arrays.sort(unsorted, new Comparator<SeriesCategory>() {
            @Override
            public int compare(SeriesCategory lhs, SeriesCategory rhs) {
                return lhs.SeriesTypeSortOrder.compareTo(rhs.SeriesTypeSortOrder);
            }
        });

        return unsorted; // because its sorted now!
    }
}

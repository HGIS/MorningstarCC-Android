package org.morningstarcc.app.fragments;

import android.os.Bundle;
import android.util.Log;

import org.morningstarcc.app.App;
import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.SeriesActivity;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.adapters.SeriesAdapter;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.viewholders.SeriesHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 7/18/2014.
 * 11/21/2015 - Juan Manuel Gomez - Limit the Series of each category
 */
public class SeriesFragment extends RecyclerFragment<Series> {

    private String seriesType;

    public SeriesFragment() {
        super(Series.class);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

        if (args != null) {
            if (args != null && args.containsKey(App.PARCEL)) {
                SeriesCategory temp = (SeriesCategory) args.get(App.PARCEL);
                seriesType = temp.SeriesType;
            }
        }


    }

    @Override
    protected DatabaseItemAdapter<Series, SeriesHolder> getAdapter(Series[] data) {

        //Clean the order
        if (seriesType != null && seriesType.length() > 0 && data != null && data.length > 0){
            List<Series> realData = new ArrayList<>();

            //Search
            for (Series item : data){
                if (item.SeriesType.equals(seriesType)){
                    realData.add(item);
                }
            }

            if (realData != null && realData.size() > 1){
                data = realData.toArray(new Series[realData.size()]);
            }
        }

        return new SeriesAdapter(getActivity(), R.layout.series_list_row, data, SeriesActivity.class);
    }


}

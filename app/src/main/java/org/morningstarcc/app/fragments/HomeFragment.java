package org.morningstarcc.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.morningstarcc.app.App;
import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.MainActivity;
import org.morningstarcc.app.activities.SeriesActivity;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.data.Study;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.libs.DateUtils;
import org.morningstarcc.app.libs.ViewConstructorUtils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/***
 * Created by Juan Manuel Gomez on 11/11/2015.
 *
 * History:
 * 11/11/2015 - Juan Manuel Gomez - Added fragment for home
 * 11/21/2015 - Juan Manuel Gomez - Added new validation to get the correct study
 */
public class HomeFragment extends Fragment {

    private SeriesCategory singleData;

    private Series lastSerie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Load all the studies
        List<Study> studiesDetails = null;
        try {
            studiesDetails = OpenHelperManager.getHelper(getActivity().getApplicationContext(), Database.class).getDao(Study.class).queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Get next sunday date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 8 - calendar.get(Calendar.DAY_OF_WEEK));


        int minDif = 99999;
        Study lastStudy = null;
        if (studiesDetails != null && studiesDetails.size() > 0){
            for (Study item : studiesDetails){
                if (item.StudyDate != null && item.StudyDate.length() > 0){
                    int days = diffOfDates(calendar.getTime(), DateUtils.getDate(item.StudyDate));
                    if (days < minDif){
                        minDif = days;
                        lastStudy = item;
                    }
                }
            }
        }

        if (lastStudy != null){

            //Search for series of study
            try {
                lastSerie = OpenHelperManager.getHelper(getActivity().getApplicationContext(), Database.class).getDao(Series.class).queryForEq("id", lastStudy.SeriesId).get(0);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (lastSerie != null){
                ViewConstructorUtils.setImageLink(getActivity(), view, R.id.home_image, lastSerie.Imagelink);
                ImageView imageView = (ImageView)view.findViewById(R.id.home_image);
                imageView.setOnClickListener(clickListener);
                view.findViewById(R.id.home_label).setVisibility(View.VISIBLE);
            }

        }

        view.findViewById(R.id.home_bulletin).setOnClickListener(clickListener);
        view.findViewById(R.id.home_events).setOnClickListener(clickListener);
        view.findViewById(R.id.home_devotions).setOnClickListener(clickListener);
        view.findViewById(R.id.home_studies).setOnClickListener(clickListener);

        getActivity().setTitle(getString(R.string.app_name));

        return view;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_image:
                    //getActivity().startActivity(new Intent(getActivity(), SeriesCategoryActivity.class).putExtra(App.PARCEL, singleData));
                    //Send to Series selected
                    Intent intent = new Intent(getActivity().getApplicationContext(), SeriesActivity.class);
                    intent.putExtra(App.PARCEL, lastSerie);
                    startActivity(intent);

                    break;
                case R.id.home_bulletin:
                    ((MainActivity)getActivity()).selectItem(MainActivity.BULLETIN);
                    break;
                case R.id.home_events:
                    ((MainActivity)getActivity()).selectItem(MainActivity.EVENTS);
                    break;
                case R.id.home_devotions:
                    ((MainActivity)getActivity()).selectItem(MainActivity.DEVOTIONS);
                    break;
                case R.id.home_studies:
                    ((MainActivity)getActivity()).selectItem(MainActivity.SERIES);
                    break;
            }
        }
    };

    private int diffOfDates(Date date1, Date date2){
        return (int) ((date1.getTime() - date2.getTime())/ (1000 * 60 * 60 * 24));
    }

}

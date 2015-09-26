package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.app.viewholders.StudyHolder;

import java.util.Date;

import static org.morningstarcc.app.libs.DateUtils.getDate;
import static org.morningstarcc.app.libs.DateUtils.getDayString;
import static org.morningstarcc.app.libs.DateUtils.getMonthString;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter<StudyHolder> {

    public StudyAdapter(Activity activity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(activity, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(StudyHolder viewHolder, final int position) {
        Bundle study = data[position];
        Date studyDate = getDate(study.getString("StudyDate"));

        viewHolder.title.setText(String.valueOf(study.getString("title")));
        
        if (studyDate != null) {
            viewHolder.day.setText(getDayString(studyDate));
            viewHolder.month.setText(getMonthString(studyDate));
        } else {
            viewHolder.day.setVisibility(View.GONE);
            viewHolder.month.setVisibility(View.GONE);
        }
    }

    @Override
    protected StudyHolder getViewHolder(View view) {
        return new StudyHolder(view);
    }
}

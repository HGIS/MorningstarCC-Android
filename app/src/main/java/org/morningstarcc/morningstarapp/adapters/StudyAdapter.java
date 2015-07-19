package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.viewholders.StudyHolder;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDayString;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getMonthString;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter<StudyHolder> {

    public StudyAdapter(Activity activity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(activity, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(StudyHolder viewHolder, final int position) {
        Date studyDate = getDate(data[position].getString("StudyDate"));

        viewHolder.title.setText(data[position].getString("title"));
        viewHolder.month.setText(getMonthString(studyDate));
        viewHolder.day.setText(getDayString(studyDate));
    }

    @Override
    protected StudyHolder getViewHolder(View view) {
        return new StudyHolder(view);
    }
}

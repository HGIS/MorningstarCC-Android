package org.morningstarcc.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.app.R;

/**
 * Created by Kyle on 4/19/2015.
 */
public class StudyHolder extends RecyclerView.ViewHolder {
    public TextView month;
    public TextView day;
    public TextView title;

    public StudyHolder(View view) {
        super(view);
        this.month = (TextView) view.findViewById(R.id.month);
        this.day = (TextView) view.findViewById(R.id.day);
        this.title = (TextView) view.findViewById(R.id.title);
    }
}

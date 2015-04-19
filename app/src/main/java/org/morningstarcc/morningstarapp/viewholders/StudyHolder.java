package org.morningstarcc.morningstarapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 4/19/2015.
 */
public class StudyHolder extends RecyclerView.ViewHolder {
    public TextView month;
    public TextView day;
    public TextView title;

    public ImageView play;
    public ImageView listen;

    public View root;

    public StudyHolder(View view) {
        super(view);
        this.month = (TextView) view.findViewById(R.id.month);
        this.day = (TextView) view.findViewById(R.id.day);
        this.title = (TextView) view.findViewById(R.id.title);
        this.play = (ImageView) view.findViewById(R.id.play);
        this.listen = (ImageView) view.findViewById(R.id.listen);
        this.root = view;
    }
}

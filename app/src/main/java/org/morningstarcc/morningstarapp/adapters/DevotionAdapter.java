package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.*;

/**
 * Created by Kyle on 10/10/2014.
 */
public class DevotionAdapter extends DatabaseItemAdapter {

//    private static Integer blue = null;

    public DevotionAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.devotion_list_row, data);
/*
        if (blue == null) blue = mContext.getResources().getColor(R.color.blue);*/
    }

    @Override
    protected void setupView(View root, int position) {
        Date date = getDate(data[position].getString("pubDate"));

        setText(root, R.id.month, getMonthString(date));
        setText(root, R.id.day, getDayString(date));
        setText(root, R.id.title, data[position].getString("title"));
        setText(root, R.id.author, data[position].getString("dc:creator"));

        // NOTE: if i want to set the color, do so here, not in the xml (because recycled views will have blue bgs still)
        /*if (position % 2 == 1) root.findViewById(R.id.title).setBackgroundColor(blue);*/
    }
}

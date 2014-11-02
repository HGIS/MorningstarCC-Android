package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 10/10/2014.
 */
public class DevotionAdapter extends DatabaseItemAdapter {
    private static String TIME_OF_DAY_PLACEHOLDER = "1:00 AM";

    public DevotionAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.devotion_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        Date date = DatabaseItem.getDate(data[position].getString("pubDate"), TIME_OF_DAY_PLACEHOLDER);

        setText(root, R.id.month, new SimpleDateFormat("MMM").format(date));
        setText(root, R.id.day, new SimpleDateFormat("d").format(date));
        setText(root, R.id.title, data[position].getString("title"));
    }
}

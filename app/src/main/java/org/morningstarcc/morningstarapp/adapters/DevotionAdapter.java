package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.*;

/**
 * Created by Kyle on 10/10/2014.
 */
public class DevotionAdapter extends DatabaseItemAdapter {

    public DevotionAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.devotion_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        Date date = getDate(data[position].getString("pubDate"));

        setText(root, R.id.month, getMonthString(date));
        setText(root, R.id.day, getDayString(date));
        setText(root, R.id.title, data[position].getString("title"));
    }
}

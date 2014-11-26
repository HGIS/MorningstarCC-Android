package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.*;

/**
 * Created by Kyle on 10/10/2014.
 */
public class DevotionAdapter extends DatabaseItemAdapter {

    Context mContext;
    Resources mResources;

    public DevotionAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.devotion_list_row, data);
        this.mContext = mContext;
        this.mResources = mContext.getResources();
    }

    @Override
    protected void setupView(View root, int position) {
        Date date = getDate(data[position].getString("pubDate"));

        setText(root, R.id.month, getMonthString(date));
        setText(root, R.id.day, getDayString(date));
        setText(root, R.id.title, data[position].getString("title"));
        setText(root, R.id.author, data[position].getString("dc:creator"));

        root.findViewById(R.id.bg).setBackgroundResource(getItemBackground(data[position].getString("title")));
    }

    private int getItemBackground(String curTitle) {
        Cursor lookup = new DatabaseStorage(mContext).get(mResources.getString(R.string.devotion_table));
        int valIdx = lookup.getColumnIndex("READ"), checkIdx = lookup.getColumnIndex("title");

        if (valIdx >= 0) {
            lookup.moveToFirst();
            while (!lookup.isAfterLast() && !lookup.getString(checkIdx).equals(curTitle))
                lookup.moveToNext();

            if (!lookup.isAfterLast() && lookup.getInt(valIdx) > 0)
                return R.drawable.devotion_read_bg;
        }

        return R.drawable.devotion_unread_bg;
    }
}

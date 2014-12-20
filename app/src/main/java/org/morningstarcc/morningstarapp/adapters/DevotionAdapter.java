package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDayString;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getMonthString;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

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

        ((TextView) root.findViewById(R.id.title)).setTypeface(
                isRead(data[position].getString("title"))
                        ? Typeface.DEFAULT
                        : Typeface.DEFAULT_BOLD
        );
    }

    private boolean isRead(String curTitle) {
        Cursor lookup = new DatabaseStorage(mContext).get(mResources.getString(R.string.devotion_table));
        int valIdx = lookup.getColumnIndex("READ"), checkIdx = lookup.getColumnIndex("title");

        if (valIdx >= 0) {
            lookup.moveToFirst();
            while (!lookup.isAfterLast() && !lookup.getString(checkIdx).equals(curTitle))
                lookup.moveToNext();

            return !lookup.isAfterLast() && lookup.getInt(valIdx) > 0;
        }

        return false;
    }
}

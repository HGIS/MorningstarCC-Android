package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.DevotionActivity;
import org.morningstarcc.morningstarapp.database.Database;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDayString;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getMonthString;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setTypeface;

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

        Typeface textStyle = isRead(data[position].getString("devoId"))
                ? Typeface.DEFAULT
                : Typeface.DEFAULT_BOLD;

        setTypeface(root, R.id.title, textStyle);
        setTypeface(root, R.id.author, textStyle);
    }

    private boolean isRead(String id) {
        Cursor lookup = Database
                .withContext(mContext)
                .forTable(DevotionActivity.READ_DEVOTIONS_TABLE)
                .readAll(null)
                .getData();

        if (lookup == null)
            return false;

        int readColumn = lookup.getColumnIndex(DevotionActivity.READ_COLUMN),
            idColumn   = lookup.getColumnIndex("devoId");

        lookup.moveToFirst();
        while (!lookup.isAfterLast()) {
            if (lookup.getString(idColumn).equals(id))
                return lookup.getString(readColumn).equals(DevotionActivity.IS_READ);

            lookup.moveToNext();
        }

        return false;

/*        Cursor lookup = new DatabaseStorage(mContext).get(mResources.getString(R.string.devotion_table));
        int valIdx = lookup.getColumnIndex("read"),
          checkIdx = lookup.getColumnIndex("title");

        if (valIdx >= 0) {
            lookup.moveToFirst();
            while (!lookup.isAfterLast() && !lookup.getString(checkIdx).equals(curTitle))
                lookup.moveToNext();

            return !lookup.isAfterLast() && lookup.getInt(valIdx) > 0;
        }

        return false;*/
    }
}

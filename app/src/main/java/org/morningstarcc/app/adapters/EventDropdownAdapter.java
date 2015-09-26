package org.morningstarcc.app.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import static org.morningstarcc.app.libs.ViewConstructorUtils.setText;

/**
 * Created by whykalo on 12/21/2014.
 */
public class EventDropdownAdapter implements SpinnerAdapter {

    private ArrayList<DataSetObserver> mObservers;
    private LayoutInflater mLayoutInflater;

    private String[] titles;

    private int mLayout;
    private int mDropdownLayout;

    public EventDropdownAdapter(Context mContext, String[] titles, int layoutResId, int dropdownLayoutResId) {
        super();
        this.titles = titles;
        this.mLayout = layoutResId;
        this.mDropdownLayout = dropdownLayoutResId;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mObservers = new ArrayList<>();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(mDropdownLayout, parent, false);

        setText(convertView, android.R.id.text1, String.valueOf(titles[position]));

        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        mObservers.add(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        mObservers.remove(dataSetObserver);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(mLayout, parent, false);

        setText(convertView, android.R.id.text1, String.valueOf(titles[position]));

        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return titles.length == 0;
    }

    public static EventDropdownAdapter createFromResource(Context context, int arrayResId, int layoutResId, int dropdownLayoutResId) {
        return new EventDropdownAdapter(context, context.getResources().getStringArray(arrayResId), layoutResId, dropdownLayoutResId);
    }
}

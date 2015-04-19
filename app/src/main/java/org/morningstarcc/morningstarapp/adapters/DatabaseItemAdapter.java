package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A generic adapter for RecyclerViews
 */
public abstract class DatabaseItemAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected LayoutInflater mInflater;
    protected Bundle[] data;
    protected int row_layout;
    protected Context mContext;
    protected RecyclerView mRecyclerView;
    protected Class<? extends Activity> nextActivity;

    public DatabaseItemAdapter(Context mContext, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.row_layout = row_layout;
        this.mContext = mContext;
        this.nextActivity = nextActivity;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(row_layout, parent, false);

        rootView.setOnClickListener(new ItemClickListener());

        return getViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        setupView(holder, position);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    protected abstract void setupView(VH holder, int position);
    protected abstract VH getViewHolder(View view);

    private class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildPosition(v);
            Bundle item = data[itemPosition];
            mContext.startActivity(new Intent(mContext, nextActivity).putExtras(item));
        }
    }
}

package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.app.App;
import org.morningstarcc.app.R;

/**
 * A generic adapter for RecyclerViews
 */
public abstract class DatabaseItemAdapter<T extends Parcelable, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected LayoutInflater mInflater;
    protected T[] data;
    protected int row_layout;
    protected Activity mActivity;
    protected RecyclerView mRecyclerView;
    protected Class<? extends Activity> nextActivity;

    public DatabaseItemAdapter(Activity mActivity, int row_layout, T[] data, Class<? extends Activity> nextActivity) {
        this.mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.row_layout = row_layout;
        this.mActivity = mActivity;
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
        return data == null ? 0 : data.length;
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
            int itemPosition = mRecyclerView.getChildAdapterPosition(v);
            T item = data[itemPosition];

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, v, mActivity.getString(R.string.transition_item));
            Intent intent = new Intent(mActivity, nextActivity).putExtra(App.PARCEL, item);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        }
    }
}

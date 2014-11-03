package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.libs.RemoteImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kyle on 10/10/2014.
 */
public abstract class DatabaseItemAdapter extends ArrayAdapter<Bundle> {

    private LayoutInflater mInflater;
    protected Bundle[] data;

    private int row_layout;

    public DatabaseItemAdapter(Context mContext, int row_layout, Bundle[] data) {
        super(mContext, row_layout, data);

        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.row_layout = row_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(this.row_layout, parent, false);
        }

        setupView(convertView, position);

        return convertView;
    }

    protected void setText(View parent, int resId, String text) {
        ((TextView) parent.findViewById(resId)).setText(text);
    }

    // TODO: create file and update db
    protected void setImageLink(View parent, int resId, String link) {
        ((RemoteImageView) parent.findViewById(resId)).setImageLink(link);
    }

    protected abstract void setupView(View root, int position);


    // TODO: check space before we just store the image
    // example: http://www.growingthrugrace.com/images/studyalbumcovers/leviticus_450x450.jpg
    // Adpated from http://stackoverflow.com/questions/649154/save-bitmap-to-location
    private File saveBitmapToFile(Bitmap bmp, String filename) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new File(filename);
    }
}

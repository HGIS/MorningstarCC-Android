package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectFragment extends Fragment {

    private Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_connect, container, false);
        View.OnClickListener myToastFailure = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "action unimplemented", Toast.LENGTH_SHORT).show();
            }
        };

        root.findViewById(R.id.about_us).setOnClickListener(myToastFailure);
        root.findViewById(R.id.see_us_on_the_web).setOnClickListener(new WebLinkListener("http://www.morningstarcc.org"));
        root.findViewById(R.id.donate).setOnClickListener(myToastFailure);
        root.findViewById(R.id.facebook).setOnClickListener(myToastFailure);
        root.findViewById(R.id.twitter).setOnClickListener(myToastFailure);
        root.findViewById(R.id.youtube).setOnClickListener(myToastFailure);
        root.findViewById(R.id.instagram).setOnClickListener(myToastFailure);
        root.findViewById(R.id.email_us).setOnClickListener(myToastFailure);

        return root;
    }

    class WebLinkListener implements View.OnClickListener {

        private String url;

        public WebLinkListener(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.url)));
        }
    }
}

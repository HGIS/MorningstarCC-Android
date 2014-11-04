package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;
import android.text.Html;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 10/21/2014.
 */
public class DevotionActivity extends DetailsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_devotion);

        setText(R.id.content, Html.fromHtml(intent.getStringExtra("content:encoded")).toString());
    }
}

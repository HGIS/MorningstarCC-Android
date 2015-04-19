package org.morningstarcc.morningstarapp.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.intents.ShareIntent;

import java.util.ArrayList;

/**
 * Created by Kyle on 10/21/2014.
 */
public class DevotionActivity extends DetailsActivity {

    public static final String READ_DEVOTIONS_TABLE = "DevotionsRead";
    public static final String IS_READ = "TRUE";
    public static final String READ_COLUMN = "read";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRead();

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_devotion);

        // NOTE: do not use ViewConstructorUtils.setText(...) as the html formatting will be lost
        ((TextView) findViewById(R.id.content)).setText(Html.fromHtml(intent.getStringExtra("content:encoded")));
    }

    public void onShare(View view) {
        Intent shareIntent = ShareIntent.build(intent.getStringExtra("content:encoded"));
        startActivity(shareIntent);
    }

    private void setRead() {
        ContentValues readDevotion = new ContentValues();

        readDevotion.put("devoId", intent.getStringExtra("devoId"));
        readDevotion.put(READ_COLUMN, IS_READ);

        Database
                .withContext(this)
                .forTable(READ_DEVOTIONS_TABLE)
                .create(new String[] {
                        "devoId", READ_COLUMN
                })
                .append(readDevotion);
    }
}

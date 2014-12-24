package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

/**
 * Created by Kyle on 10/21/2014.
 */
public class DevotionActivity extends DetailsActivity {

    private static boolean hasColumn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRead();

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_devotion);

        // NOTE: do not use ViewConstructorUtils.setText(...) as the html formatting will be lost
        ((TextView) findViewById(R.id.content)).setText(Html.fromHtml(intent.getStringExtra("content:encoded")));
    }

    private void setRead() {
        DatabaseStorage storage = new DatabaseStorage(this);

        if (!hasColumn) {
            storage.addColumn("MCCDailyDevoRSS", "READ",  "INTEGER",  "DEFAULT 0");
            hasColumn = true;
        }

        storage.update(
                "MCCDailyDevoRSS",
                "title", intent.getStringExtra("title"),
                "READ", "1"
        );
    }
}

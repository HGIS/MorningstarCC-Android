package org.morningstarcc.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Devotion;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.libs.IntentUtils;

import java.sql.SQLException;

/**
 * Created by Kyle on 10/21/2014.
 */
public class DevotionActivity extends DetailsActivity<Devotion> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRead();

        setContentView(R.layout.activity_devotion);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(item.title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // NOTE: do not use ViewConstructorUtils.setText(...) as the html formatting will be lost
        ((TextView) findViewById(R.id.content)).setText(Html.fromHtml(item.encoded));
    }

    public void onShare(View view) {
        Intent shareIntent = IntentUtils.shareIntent(item.encoded);
        startActivity(shareIntent);
    }

    private void setRead() {
        try {
            if (!item.read) {
                item.read = true;
                OpenHelperManager.getHelper(this, Database.class).getDao(Devotion.class).update(item);
            }
        } catch (SQLException e) {
        }

        OpenHelperManager.releaseHelper();
    }
}

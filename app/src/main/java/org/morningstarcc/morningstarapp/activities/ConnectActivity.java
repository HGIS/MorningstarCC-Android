package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.fragments.ConnectFragment;
import org.morningstarcc.morningstarapp.intents.WebViewIntent;
import org.morningstarcc.morningstarapp.libs.ViewConstructorUtils;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectActivity extends DetailsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connect);
        ViewConstructorUtils.setTitle(this, intent.getStringExtra("title"));

        android.util.Log.e("TEST", intent.getExtras().toString());

        String content = intent.getStringExtra("content:encoded");
        if (content == null || content.length() == 0) {
            startActivity(new WebViewIntent(intent.getStringExtra("weblink")));
            finish();
        }

        Fragment fragment = new ConnectFragment();

        fragment.setArguments(intent.getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

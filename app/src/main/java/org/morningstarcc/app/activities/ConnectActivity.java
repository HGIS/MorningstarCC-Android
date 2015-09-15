package org.morningstarcc.app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.morningstarcc.app.R;
import org.morningstarcc.app.fragments.ConnectFragment;
import org.morningstarcc.app.libs.IntentUtils;
import org.morningstarcc.app.libs.ViewConstructorUtils;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectActivity extends DetailsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connect);
        ViewConstructorUtils.setTitle(this, intent.getStringExtra("title"));

        String hasChild = intent.getStringExtra("haschild");
        String content = intent.getStringExtra("content:encoded");

        if (!"True".equalsIgnoreCase(hasChild) && (content == null || content.length() == 0)) {
            startActivity(IntentUtils.webIntent(intent.getStringExtra("weblink")));
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

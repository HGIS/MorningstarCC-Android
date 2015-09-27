package org.morningstarcc.app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Connect;
import org.morningstarcc.app.fragments.ConnectFragment;
import org.morningstarcc.app.libs.IntentUtils;
import org.morningstarcc.app.libs.ViewConstructorUtils;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectActivity extends DetailsActivity<Connect> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connect);

        ViewConstructorUtils.setTitle(this, item.title);

        boolean hasChild = Boolean.parseBoolean(item.haschild);
        String content = item.encoded;

        if (!hasChild && (content == null || content.length() == 0)) {
            startActivity(IntentUtils.webIntent(item.weblink));
            finish();
        }

        Fragment fragment = new ConnectFragment();

        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

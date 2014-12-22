package org.morningstarcc.morningstarapp.activities;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectActivity extends DetailsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dummy);

        setText(this, R.id.tv, "Dummy");
    }
}

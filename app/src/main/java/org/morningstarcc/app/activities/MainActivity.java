package org.morningstarcc.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.morningstarcc.app.R;
import org.morningstarcc.app.adapters.EventDropdownAdapter;
import org.morningstarcc.app.adapters.NavigationDrawerAdapter;
import org.morningstarcc.app.fragments.ConnectFragment;
import org.morningstarcc.app.fragments.DevotionFragment;
import org.morningstarcc.app.fragments.EventFragment;
import org.morningstarcc.app.fragments.ExpandableEventFragment;
import org.morningstarcc.app.fragments.HomeFragment;
import org.morningstarcc.app.fragments.SeriesCategoryFragment;
import org.morningstarcc.app.http.DataService;
import org.morningstarcc.app.http.DownloadTask;
import org.morningstarcc.app.libs.IntentUtils;

import java.io.File;

/**
 * TODO list:
 * bug:
 * /sdcard/ is hardcoded. Android has a dynamic file system that may not be loaded or in /sdcard/
 * popping fragments off back stack does not change action bar state
 * <p>
 * Version Alpha/Beta 0
 * - Store updates that keep wifi requests to minimum -- try testing DatabaseStorage.update(...);
 * + update picasso to use file backing cache
 * + remove feature that lets you add event to calendar when you have already done so
 * + do some stuff with downloading audio
 * + Consolidate update and old
 * - Allow for Vimeo
 * - see Pastor Rod's scrum updates
 * <p>
 * Version Beta 1
 * - Style everything better
 * - animations!
 * - palettes?
 * - update nav drawer
 * - push notifications?
 * <p>
 * History:
 * 11/11/2015 - Juan Manuel Gomez - Added Home option to the drawer
 * 11/12/2015 - Juan Manuel Gomez - Added pdf functionality, back press button improved,
 * splash progress, youtube crash fixed
 */
public class MainActivity extends ActionBarActivity {
    private static final int PERMISSION_REQUEST = 0x1234;

    // the indices for the drawer items
    public static final int
            HOME = 0,
            CONNECT = 1,
            SERIES = 2,
            EVENTS = 3,
            DEVOTIONS = 4,
            BULLETIN = 5,
            LIVE_STREAM = 6;
    private CharSequence mTitle;
    // Navigation Drawer support variables
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private String[] mDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int mPosition;
    // a variable to hold state of events drop-down
    private int mEventPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set up animations for those of us lucky to have them
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
            getWindow().setSharedElementEnterTransition(new ChangeBounds());
            getWindow().setSharedElementExitTransition(new ChangeBounds());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer);
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarNavDrawerToggle(this, mDrawerLayout);
        mEventPosition = 0;

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(new NavigationDrawerAdapter(this, mDrawerTitles));

        // Events drop-down
        SpinnerAdapter mSpinnerAdapter = EventDropdownAdapter
                .createFromResource(this, R.array.event_types, R.layout.event_spinner, android.R.layout.simple_spinner_dropdown_item);
        OnNavigationListener mOnNavigationListener = new EventChangeListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();

        mPosition = 1;
        mDrawerList.setItemChecked(mPosition, true);
        setTitle(getString(R.string.app_name));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerLayout.openDrawer(Gravity.START);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(mTitle = title);
    }

    /**
     * Swaps fragments in the main content view
     */
    public void selectItem(int position) {
        final Fragment fragment;
        String title;

        switch (position) {
            case HOME:
                title = getString(R.string.app_name);
                fragment = new HomeFragment();
                break;
            case CONNECT:
                title = mDrawerTitles[position];
                fragment = new ConnectFragment();
                break;
            case SERIES:
                title = mDrawerTitles[position];
                fragment = new SeriesCategoryFragment();
                break;
            case EVENTS:
                title = mDrawerTitles[position];
                fragment = pickEventFragment(mEventPosition);
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
                break;
            case DEVOTIONS:
                title = mDrawerTitles[position];
                fragment = new DevotionFragment();
                break;
            case BULLETIN:
                launchBulletin();
                return;
            case LIVE_STREAM:
                launchLiveStream();
                return;
            default:
                Log.e("MainActivity", "Cannot create fragment for drawer item position: " + position);
                return;
        }

        mPosition = position;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(title);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // change the title
        Fragment cur = getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        if (cur instanceof ConnectFragment) {
            mPosition = CONNECT;
            setTitle(mDrawerTitles[mPosition]);
        } else if (cur instanceof SeriesCategoryFragment) {
            mPosition = SERIES;
            setTitle(mDrawerTitles[mPosition]);
        } else if (cur instanceof EventFragment || cur instanceof ExpandableEventFragment) {
            mPosition = EVENTS;
            setTitle("");
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        } else if (cur instanceof DevotionFragment) {
            mPosition = DEVOTIONS;
            setTitle(mDrawerTitles[mPosition]);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchBulletin();
            } else {
                Toast.makeText(this, getString(R.string.bulletin_permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Fragment pickEventFragment(int position) {
        if (position == 0)
            return new EventFragment();
        return new ExpandableEventFragment();
    }

    private void launchBulletin() {
        // ensure we have file write (and subsequently read) permissions before downloading
        final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                new AlertDialog.Builder(this)
                        .setMessage(R.string.bulletin_permission_request)
                        .setPositiveButton(getString(R.string.ok).toUpperCase(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, PERMISSION_REQUEST);
                            }
                        })
                        .setNegativeButton(getString(R.string.not_now).toUpperCase(), null)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {permission}, PERMISSION_REQUEST);
            }
            return;
        }

        if (!isNetworkAvailable()) {
            if (!new File("/sdcard/bulletin.pdf").exists()) {
                return;
            }
            Intent i = new Intent(this, PDFActivity.class);
            startActivity(i);
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading bulletin...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);

        final DownloadTask downloadTask = new DownloadTask(this, progressDialog);
        downloadTask.execute(getString(R.string.bulletin_url));

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                downloadTask.cancel(true);
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void launchLiveStream() {
        startActivity(IntentUtils.webIntent(DataService.LiveStreamUrl));
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class ActionBarNavDrawerToggle extends ActionBarDrawerToggle {
        public ActionBarNavDrawerToggle(Activity mActivity, DrawerLayout mDrawerLayout) {
            super(mActivity,
                    mDrawerLayout,
                    R.string.drawer_open,
                    R.string.drawer_close);
        }

        /**
         * Called when a drawer has settled in a completely closed state.
         */
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            if (mPosition == EVENTS)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            getSupportActionBar().setTitle(mTitle);
            invalidateOptionsMenu();
        }

        /**
         * Called when a drawer has settled in a completely open state.
         */
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            getSupportActionBar().setTitle(mDrawerTitle);
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            invalidateOptionsMenu();
        }
    }

    private class EventChangeListener implements OnNavigationListener {
        // Get the same strings provided for the drop-down's ArrayAdapter
        String[] strings = getResources().getStringArray(R.array.event_types);

        @Override
        public boolean onNavigationItemSelected(int position, long itemId) {
            Fragment fragment = pickEventFragment(mEventPosition = position);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment, strings[position])
                    .addToBackStack(null)
                    .commit();

            return true;
        }
    }

    /*private class BulletinDownloader extends FileDownloader {
        private ProgressDialog dialog;

        public BulletinDownloader(String filename, Context context, ProgressDialog dialog) {
            super(filename, context);
            this.dialog = dialog;
        }

        @Override
        public void onPostExecute(File file) {
            dialog.hide();
            openBulletin();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            dialog.setIndeterminate(false);
            dialog.setMax((int) length);
            dialog.setProgress((int) progress);
        }
    }*/

    private class AsyncTaskCancelListener implements ProgressDialog.OnCancelListener {
        private AsyncTask task;

        public AsyncTaskCancelListener(AsyncTask task) {
            this.task = task;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            task.cancel(false);
        }
    }
}
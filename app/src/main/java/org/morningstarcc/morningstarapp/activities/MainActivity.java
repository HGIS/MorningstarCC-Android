package org.morningstarcc.morningstarapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.NavigationDrawerAdapter;
import org.morningstarcc.morningstarapp.fragments.DevotionFragment;
import org.morningstarcc.morningstarapp.fragments.EventFragment;
import org.morningstarcc.morningstarapp.fragments.ExpandableEventFragment;
import org.morningstarcc.morningstarapp.fragments.SeriesFragment;

/**
 * TODO list:
 *
 * Version Alpha/Beta 0
 *  - Store updates that keep wifi requests to minimum -- try testing DatabaseStorage.update(...);
 *      + add seen flag to database for devotions (probably use blue and red)
 *      + change link column to use local file for image
 *          - make sure to delete all files associated with the table when you drop it
 *      + remove feature that lets you add event to calendar when you have already done so
 *      + do some stuff with downloading audio
 *  - default views to error/loading state
 *  - Update only when required
 *  - Allow for Vimeo
 *  - click on different sub-list items
 *  - see Pastor Rod's scrum updates
 *
 * Version Beta 1
 *  - Style everything better
 *  - animations!
 *  - palettes
 *  - update nav drawer
 *  - Get differently sized images (almost all of them) -- maybe
 *  - Month dividers (and spinner) for events
 *  - push notifications?
 */
public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private CharSequence mTitle;

    // Navigation Drawer support variables
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private String[] mDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer);
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarNavDrawerToggle(this, mDrawerLayout);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(new NavigationDrawerAdapter(this, mDrawerTitles));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        selectItem(0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(mTitle = title);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        final Fragment fragment;
        String title;

        switch (position) {
            case 0:
                title = mDrawerTitles[position];
                fragment = new SeriesFragment();
                break;
            case 1:
                title = "";
                fragment = new EventFragment();
                SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.event_types, android.R.layout.simple_spinner_dropdown_item);

                OnNavigationListener mOnNavigationListener = new OnNavigationListener() {
                    // Get the same strings provided for the drop-down's ArrayAdapter
                    String[] strings = getResources().getStringArray(R.array.event_types);

                    @Override
                    public boolean onNavigationItemSelected(int position, long itemId) {
                        Fragment fragment = position == 0
                                ? new EventFragment()
                                : new ExpandableEventFragment();

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragment, strings[position])
                                .addToBackStack(null)
                                .commit();

                        return true;
                    }
                };

                ActionBar actionBar = getSupportActionBar();
                actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

                break;
            case 2:
                title = mDrawerTitles[position];
                fragment = new DevotionFragment();
                break;
            case 4:
                launchBulletin();
                return;
            case 5:
                launchLiveStream();
                return;
            default:
                Log.e(TAG, "Cannot create fragment for drawer item position: " + position);
                return;
        }

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

    private void launchBulletin() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.bulletin_url))));
    }

    private void launchLiveStream() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.live_stream_url))));
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
                  R.drawable.ic_drawer,
                  R.string.drawer_open,
                  R.string.drawer_close);
        }

        /** Called when a drawer has settled in a completely closed state. */
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            getSupportActionBar().setTitle(mTitle);
            invalidateOptionsMenu();
        }

        /** Called when a drawer has settled in a completely open state. */
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            getSupportActionBar().setTitle(mDrawerTitle);
            invalidateOptionsMenu();
        }
    }
}
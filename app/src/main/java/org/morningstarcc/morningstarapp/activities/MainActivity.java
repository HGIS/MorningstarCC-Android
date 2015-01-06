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
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.EventDropdownAdapter;
import org.morningstarcc.morningstarapp.adapters.NavigationDrawerAdapter;
import org.morningstarcc.morningstarapp.fragments.ConnectFragment;
import org.morningstarcc.morningstarapp.fragments.DevotionFragment;
import org.morningstarcc.morningstarapp.fragments.EventFragment;
import org.morningstarcc.morningstarapp.fragments.ExpandableEventFragment;
import org.morningstarcc.morningstarapp.fragments.SeriesFragment;
import org.morningstarcc.morningstarapp.intents.WebViewIntent;

/**
 * TODO list:
 *  bug:
 *      popping fragments off back stack does not change action bar state
 *
 * Version Alpha/Beta 0
 *  - Connect Tab
 *  - Store updates that keep wifi requests to minimum -- try testing DatabaseStorage.update(...);
 *      + update picasso to use file backing cache
 *      + remove feature that lets you add event to calendar when you have already done so
 *      + do some stuff with downloading audio
 *      + Consolidate update and old
 *  - default views to error/loading state
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

    private CharSequence mTitle;

    // Navigation Drawer support variables
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private String[] mDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int mPosition;

    // the indices for the drawer items
    private static final int CONNECT = 0,
                             SERIES = 1,
                             EVENTS = 2,
                             DEVOTIONS = 3,
                             DIVIDER = 4,
                             BULLETIN = 5,
                             LIVE_STREAM = 6;

    // a variable to hold state of events drop-down
    private int mEventPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer);
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarNavDrawerToggle(this, mDrawerLayout);
        mEventPosition = 0;

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
                .replace(R.id.content_frame, new ConnectFragment())
                .commit();

        mPosition = 0;
        mDrawerList.setItemChecked(mPosition, true);
        setTitle(mDrawerTitles[mPosition]);
        mDrawerLayout.closeDrawer(mDrawerList);
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
            case CONNECT:
                title = mDrawerTitles[position];
                fragment = new ConnectFragment();
                break;
            case SERIES:
                title = mDrawerTitles[position];
                fragment = new SeriesFragment();
                break;
            case EVENTS:
                title = "";
                fragment = pickEventFragment(mEventPosition);
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
                break;
            case DEVOTIONS:
                title = mDrawerTitles[position];
                fragment = new DevotionFragment();
                break;
            case DIVIDER:
                return;
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
            mPosition = 0;
            setTitle(mDrawerTitles[mPosition]);
        }
        else if (cur instanceof SeriesFragment) {
            mPosition = 1;
            setTitle(mDrawerTitles[mPosition]);
        }
        else if (cur instanceof EventFragment || cur instanceof ExpandableEventFragment) {
            mPosition = 2;
            setTitle("");
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        }
        else if (cur instanceof DevotionFragment) {
            mPosition = 3;
            setTitle(mDrawerTitles[mPosition]);
        }
    }

    private Fragment pickEventFragment(int position) {
        if (position == 0)
            return new EventFragment();
        return new ExpandableEventFragment();
    }

    private void launchBulletin() {
        startActivity(new WebViewIntent(getString(R.string.bulletin_url)));
    }

    private void launchLiveStream() {
        startActivity(new WebViewIntent(getString(R.string.live_stream_url)));
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
            if (mPosition == EVENTS)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            getSupportActionBar().setTitle(mTitle);
            invalidateOptionsMenu();
        }

        /** Called when a drawer has settled in a completely open state. */
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
}
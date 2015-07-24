package com.eventtasks.tokko.eventtasks;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EventListActivity extends Activity implements EventListFragment.EventListCallbacks {
    public static final String EXTRA_DETAILS_FRAGMENTS = "details";
    private EventListFragment listFragment;
    private DetailFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listFragment = new EventListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, listFragment);
        if (savedInstanceState != null) {
            detailsFragment = (DetailFragment) getFragmentManager().getFragment(savedInstanceState, EXTRA_DETAILS_FRAGMENTS);
            ft.replace(android.R.id.content, detailsFragment);
            ft.addToBackStack("name");
        } else {
            detailsFragment = new DetailFragment();
        }
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, EXTRA_DETAILS_FRAGMENTS, detailsFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventClicked(long id) {

    }
}

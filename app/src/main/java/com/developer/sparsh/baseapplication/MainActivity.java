package com.developer.sparsh.baseapplication;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.developer.sparsh.baseapplication.Adapters.NavigationPagerAdapter;
import com.developer.sparsh.baseapplication.Fragments.Event_Info_Fragment;
import com.developer.sparsh.baseapplication.Fragments.Feed_Fragment;
import com.developer.sparsh.baseapplication.Fragments.Invities_Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Boolean slidingTabPresent;
    private NavigationPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read the JSON file
        String json = readJsonFile("data.json");
        try {
            Log.d(TAG, "onCreate: " + json);
            JSONObject baseObject = new JSONObject(json);
            slidingTabPresent = baseObject.getBoolean("slidingTabPresent");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (slidingTabPresent) {
            Toast.makeText(this, "Its Sliding Tabs", Toast.LENGTH_SHORT).show();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            toggle.setDrawerIndicatorEnabled(false);
            mSectionsPagerAdapter = new NavigationPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }else {
            Toast.makeText(this,"Its Navigation bar",Toast.LENGTH_SHORT).show();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            Feed_Fragment firstFragment = new Feed_Fragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public String readJsonFile(String filename) {
        try {
            InputStream inputStream = this.getAssets().open(filename);
            int size = inputStream.available();

            byte buffer[] = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_feed) {
            Feed_Fragment feed_fragment = new Feed_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, feed_fragment).commit();
        } else if (id == R.id.nav_invitees) {
            Invities_Fragment invities_fragment = new Invities_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, invities_fragment).commit();
        } else if (id == R.id.nav_event_info) {
            Event_Info_Fragment event_info_fragment = new Event_Info_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, event_info_fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

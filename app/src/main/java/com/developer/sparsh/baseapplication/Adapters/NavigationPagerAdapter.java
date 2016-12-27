package com.developer.sparsh.baseapplication.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.developer.sparsh.baseapplication.Fragments.Event_Info_Fragment;
import com.developer.sparsh.baseapplication.Fragments.Feed_Fragment;
import com.developer.sparsh.baseapplication.Fragments.Invities_Fragment;

/**
 * Created by utkarshnath on 27/12/16.
 */

public class NavigationPagerAdapter extends FragmentPagerAdapter {

    public NavigationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //
        if(position==0){
            Feed_Fragment fragment = new Feed_Fragment();
            return fragment;
        }
        if(position==1){
            Invities_Fragment fragment = new Invities_Fragment();
            return fragment;
        }
        if(position==2){
            Event_Info_Fragment fragment = new Event_Info_Fragment();
            return fragment;
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return " My Feed";
            case 1:
                return "Invitees";
            case 2:
                return "Event Info";
        }
        return null;
    }
}
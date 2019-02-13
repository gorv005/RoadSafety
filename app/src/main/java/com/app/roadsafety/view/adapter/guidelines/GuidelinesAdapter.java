package com.app.roadsafety.view.adapter.guidelines;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.roadsafety.model.Guidelines;
import com.app.roadsafety.view.GuidelinesFragment;

import java.util.List;


public class GuidelinesAdapter extends FragmentPagerAdapter {

    List<Guidelines> guidelines;
    public GuidelinesAdapter(FragmentManager fragmentManager,List<Guidelines> guidelines ) {
        super(fragmentManager);
        this.guidelines=guidelines;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return guidelines.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return GuidelinesFragment.newInstance(guidelines.get(position));
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return GuidelinesFragment.newInstance(guidelines.get(position));
            case 2: // Fragment # 1 - This will show SecondFragment
                return GuidelinesFragment.newInstance(guidelines.get(position));
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }


}

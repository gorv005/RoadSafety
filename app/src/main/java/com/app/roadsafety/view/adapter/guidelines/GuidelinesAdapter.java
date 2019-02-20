package com.app.roadsafety.view.adapter.guidelines;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.roadsafety.model.guidelines.Guidelines;
import com.app.roadsafety.model.guidelines.GuidelinesResponseDataList;
import com.app.roadsafety.view.GuidelinesFragment;

import java.util.List;


public class GuidelinesAdapter extends FragmentPagerAdapter {

    List<GuidelinesResponseDataList> guidelines;
    public GuidelinesAdapter(FragmentManager fragmentManager,List<GuidelinesResponseDataList> guidelines ) {
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
                return GuidelinesFragment.newInstance(guidelines.get(position),position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }


}

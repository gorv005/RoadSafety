package com.app.roadsafety.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.roadsafety.R;
import com.app.roadsafety.view.fragments.IncidentImageViewPager;

public class IncidentImageViewPagerAdapter extends FragmentStatePagerAdapter {
    public IncidentImageViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=IncidentImageViewPager.newInstance(R.mipmap.ic_launcher,"Intialize");

        switch (position)
        {
            case 0:
                fragment= IncidentImageViewPager.newInstance(R.mipmap.ic_launcher,"http://imgs.abduzeedo.com/files/paul0v2/footwear-ads/ADIDAS_TREE.preview.jpg");
                break;
            case 1:
                fragment=IncidentImageViewPager.newInstance(R.mipmap.ic_launcher,"http://www.sourceraven.com/wp-content/uploads/2014/01/creative1.png");
                break;
            case 2:
                fragment=IncidentImageViewPager.newInstance(R.mipmap.ic_launcher,"http://pixelantix.com/img/slider/Creative-Design-Business.jpg");
                break;
            default:
                fragment=IncidentImageViewPager.newInstance(R.mipmap.ic_launcher,"http://4.bp.blogspot.com/-vSfob76kWOc/VBmof0C800I/AAAAAAAAEBw/9LNwNyViXa8/s1600/funadress%2Bcreative%2Bdrawings%2B9.jpg");
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}

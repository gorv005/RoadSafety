package com.app.roadsafety.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.roadsafety.R;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.view.adapter.map.IncidentMapsFragment;
import com.app.roadsafety.view.feed.FeedListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ivFeed)
    ImageView ivFeed;
    @BindView(R.id.llFeed)
    LinearLayout llFeed;
    @BindView(R.id.ivSettings)
    ImageView ivSettings;
    @BindView(R.id.llSettings)
    LinearLayout llSettings;
    @BindView(R.id.ivMap)
    ImageView ivMap;
    @BindView(R.id.llMap)
    LinearLayout llMap;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentLoader(AppConstants.FRAGMENT_FEED_LIST,null);

    }


    public void fragmentLoader(int fragmentType, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (fragmentType) {
            case AppConstants.FRAGMENT_FEED_LIST:
                fragment = new FeedListFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case AppConstants.FRAGMENT_MAP:
                fragment = new IncidentMapsFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;

        }
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    @OnClick({R.id.llFeed, R.id.llSettings, R.id.llMap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llFeed:
                fragmentLoader(AppConstants.FRAGMENT_FEED_LIST,null);
                break;
            case R.id.llSettings:
                break;
            case R.id.llMap:
                FragmentManager fm = getSupportFragmentManager();
                fm.popBackStack (R.id.container, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentLoader(AppConstants.FRAGMENT_MAP,null);

                break;
        }
    }
}

package com.app.roadsafety.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.FragmentHistory;
import com.app.roadsafety.view.fragmentnavigationcontroller.FragNavController;
import com.app.roadsafety.view.fragments.AddIncidentFragment;
import com.app.roadsafety.view.fragments.BaseFragment;
import com.app.roadsafety.view.fragments.FeedListFragment;
import com.app.roadsafety.view.fragments.IncidentMapsFragment;
import com.app.roadsafety.view.fragments.NotificationFragment;
import com.app.roadsafety.view.fragments.SettingsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private Fragment fragment;
    private FragNavController mNavController;

    private FragmentHistory fragmentHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        init(savedInstanceState);
        //  fragmentLoader(AppConstants.FRAGMENT_FEED_LIST, null);
        changeStatusBarColor();
        switchTab(0);
       // fragmentHistory.push(0);
        updateTabSelection(0);

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //  toolbar.setTitleTextColor(Color.parseColor("#475261"));


    }

    void init(Bundle savedInstanceState) {
        fragmentHistory = new FragmentHistory();


        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, 3)
                .build();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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
            case AppConstants.FRAGMENT_SETTINGS:
                fragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(C.TAG_FRAGMENT_PRODUCTS_HOME);
                break;
            case AppConstants.FRAGMENT_NOTIFICATION:
                fragment = new NotificationFragment();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
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
                //  fragmentLoader(AppConstants.FRAGMENT_FEED_LIST, null);
                fragmentHistory.push(0);
                switchTab(0);
                updateTabSelection(0);
                break;
            case R.id.llSettings:
                //  fragmentLoader(AppConstants.FRAGMENT_SETTINGS, null);
                fragmentHistory.push(2);
                switchTab(2);
                updateTabSelection(2);
                break;
            case R.id.llMap:
                //fragmentLoader(AppConstants.FRAGMENT_MAP, null);
                fragmentHistory.push(1);
                switchTab(1);
                updateTabSelection(1);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {

            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {


                if (fragmentHistory.getStackSize() > 1) {

                    int position = fragmentHistory.popPrevious();

                    switchTab(position);

                    updateTabSelection(position);

                } else {

                    switchTab(0);

                    updateTabSelection(0);

                    fragmentHistory.emptyStack();
                }
            }

        }
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);


//        updateToolbarTitle(position);
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {

            case FragNavController.TAB1:
                return new FeedListFragment();
            case FragNavController.TAB2:
                return new IncidentMapsFragment();
            case FragNavController.TAB3:
                return new SettingsFragment();


        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        if (getSupportActionBar() != null && mNavController != null) {


            updateToolbar();

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (getSupportActionBar() != null && mNavController != null) {

            updateToolbar();

        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    private void updateToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setDisplayShowHomeEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    private void updateTabSelection(int currentTab) {
        switch (currentTab) {

            case FragNavController.TAB1:
                ivFeed.setImageResource(R.drawable.feed_selected);
                ivMap.setImageResource(R.drawable.map);
                ivSettings.setImageResource(R.drawable.setting);
                break;
            case FragNavController.TAB2:
                ivFeed.setImageResource(R.drawable.feed);
                ivMap.setImageResource(R.drawable.map_selected);
                ivSettings.setImageResource(R.drawable.setting);

                break;
            case FragNavController.TAB3:
                ivFeed.setImageResource(R.drawable.feed);
                ivMap.setImageResource(R.drawable.map);
                ivSettings.setImageResource(R.drawable.setting_selected);
                break;


        }
    }

    public void updateToolbarTitle(String title, boolean isVisible) {

        if (isVisible) {
            toolbar.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            toolbarTitle.setText(title);
        } else {
            getSupportActionBar().hide();
            toolbar.setVisibility(View.GONE);
            changeStatusBarColor();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
  /*  for (Fragment fragment : getSupportFragmentManager().getFragments()) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }*/
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof AddIncidentFragment) {
            ((AddIncidentFragment) fragment).onActivityResult(requestCode, resultCode, data);

        } else if (fragment != null && fragment instanceof IncidentMapsFragment) {
            ((IncidentMapsFragment) fragment).onActivityResult(requestCode, resultCode, data);

        }
    }

    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }
    public void gotoWebPage(String url){
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof FeedListFragment) {
            ((FeedListFragment) fragment).gotoWebPage(url);

        }
    }
    public void gotoIncidentList(){
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof IncidentMapsFragment) {
            ((IncidentMapsFragment) fragment).gotoIncidentList();

        }
    }

    public void gotoIncidentDescription(String id){
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof IncidentMapsFragment) {
            ((IncidentMapsFragment) fragment).gotoIncidentDescription(id);

        }
    }

}

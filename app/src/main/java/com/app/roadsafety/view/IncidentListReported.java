package com.app.roadsafety.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.roadsafety.R;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.view.adapter.incidents.AdapterIncidentList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncidentListReported extends AppCompatActivity {

    @BindView(R.id.rvIncident)
    RecyclerView rvIncident;
    LinearLayoutManager layoutManager;
    AdapterIncidentList adapterIncidentList;
    List<Feed> feeds;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_incident_list_reported);
        ButterKnife.bind(this);
        setToolbar();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvIncident.setLayoutManager(layoutManager);
        rvIncident.setHasFixedSize(true);
        setFeed();
        changeStatusBarColor();
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("#475261"));
        getSupportActionBar().setTitle("12 incidents reported");
       /* final Drawable upArrow =  ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.title_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
    }

    void setFeed() {
        feeds = new ArrayList<>();
        Feed g1 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g1);
        Feed g2 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g2);
        Feed g3 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g3);
        adapterIncidentList = new AdapterIncidentList(feeds, this);
        rvIncident.setAdapter(adapterIncidentList);
    }

}

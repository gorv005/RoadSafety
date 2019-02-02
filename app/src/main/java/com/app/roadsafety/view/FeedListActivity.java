package com.app.roadsafety.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.Guidelines;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.view.adapter.GuidelinesAdapter;
import com.app.roadsafety.view.adapter.feed.AdapterFeedList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedListActivity extends AppCompatActivity {

    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    LinearLayoutManager layoutManager;
    AdapterFeedList adapterFeedList;
    List<Feed> feeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFeed.setLayoutManager(layoutManager);
        rvFeed.setHasFixedSize(true);
        setFeed();
    }


    void setFeed() {
        feeds = new ArrayList<>();
        Feed g1 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g1);
        Feed g2 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g2);
        Feed g3 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g3);
        adapterFeedList = new AdapterFeedList(feeds, this);
        rvFeed.setAdapter(adapterFeedList);
    }
}

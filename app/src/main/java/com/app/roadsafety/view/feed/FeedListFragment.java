package com.app.roadsafety.view.feed;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.roadsafety.R;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.feed.AdapterFeedList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedListFragment extends Fragment {


    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    Unbinder unbinder;
    LinearLayoutManager layoutManager;
    AdapterFeedList adapterFeedList;
    List<Feed> feeds;
    public FeedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFeed.setLayoutManager(layoutManager);
        rvFeed.setHasFixedSize(true);
        setFeed();
    }

    @Override
    public void onResume() {
        super.onResume();
        ( (MainActivity)getActivity()).updateToolbarTitle(getString(R.string.road_safety_feed),true);

    }

    void setFeed() {
        feeds = new ArrayList<>();
        Feed g1 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g1);
        Feed g2 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g2);
        Feed g3 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g3);
        Feed g4 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g4);
        Feed g5 = new Feed("login_back",getString(R.string.watch_out_big_cars) , getString(R.string.feed_desc));
        feeds.add(g5);
        adapterFeedList = new AdapterFeedList(feeds, getActivity());
        rvFeed.setAdapter(adapterFeedList);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

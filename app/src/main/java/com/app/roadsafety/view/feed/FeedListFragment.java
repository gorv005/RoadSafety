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
import com.app.roadsafety.model.feed.FeedResponse;
import com.app.roadsafety.presenter.feed.FeedListPresenterImpl;
import com.app.roadsafety.presenter.feed.IFeedListPresenter;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
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
public class FeedListFragment extends Fragment implements IFeedListPresenter.IFeedListView {


    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    Unbinder unbinder;
    LinearLayoutManager layoutManager;
    AdapterFeedList adapterFeedList;
    List<Feed> feeds;
    IFeedListPresenter iFeedListPresenter;
    AppUtils util;
    public FeedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iFeedListPresenter=new FeedListPresenterImpl(this,getActivity());
        util=new AppUtils();
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
      //  setFeed();
        getFeedList(""+1);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.road_safety_feed), true);

    }

    void getFeedList(String page){
        iFeedListPresenter.getFeedList(page,
                SharedPreference.getInstance(getActivity()).getString(AppConstants.REGION));

    }
    void setFeed() {
        feeds = new ArrayList<>();
        Feed g1 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g1);
        Feed g2 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g2);
        Feed g3 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g3);
        Feed g4 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g4);
        Feed g5 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g5);
      //  adapterFeedList = new AdapterFeedList(feeds, getActivity());
      //  rvFeed.setAdapter(adapterFeedList);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessFeedListResponse(FeedResponse response) {
        adapterFeedList = new AdapterFeedList(response.getData().getData(), getActivity());
          rvFeed.setAdapter(adapterFeedList);
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(getString(R.string.please_wait), getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }
}

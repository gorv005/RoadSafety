package com.app.roadsafety.view.fragments;


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
import com.app.roadsafety.model.feed.FeedListData;
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
public class FeedListFragment extends BaseFragment implements IFeedListPresenter.IFeedListView {


    @BindView(R.id.rvFeed)
    RecyclerView rvFeed;
    Unbinder unbinder;
    LinearLayoutManager layoutManager;
    AdapterFeedList adapterFeedList;
    List<Feed> feeds;
    IFeedListPresenter iFeedListPresenter;
    AppUtils util;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    List<FeedListData> feedListData;
    int page = 1, totalPages;

    public FeedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iFeedListPresenter = new FeedListPresenterImpl(this, getActivity());
        util = new AppUtils();
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
        rvFeed.addOnScrollListener(recyclerViewOnScrollListener);
        //  setFeed();
               page=1;
            getFeedList("" + page);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.road_safety_feed), true);

    }

    void getFeedList(String page) {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();

        iFeedListPresenter.getFeedList(auth_token, page,
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

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) //check for scroll down
            {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (loading && page <totalPages && totalPages > 1) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        page=page+1;
                        getFeedList("" + page);
                    }
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessFeedListResponse(FeedResponse response) {
        try {
            if (feedListData != null && feedListData.size() > 0 && page != 1) {
                feedListData.addAll(response.getData().getData());
                adapterFeedList.notifyDataSetChanged();
            }
            else if(response.getData()==null && response.getErrors()!=null && response.getErrors().size()>0){
                String error="";
                for(int i=0;i<response.getErrors().size();i++){
                    error=error+response.getErrors().get(i)+"\n";
                }
                util.resultDialog(getActivity(),error);
            }
            else {
                if (response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {

                    if (response.getData().getMeta() != null && response.getData().getMeta().getPagination() != null) {
                        totalPages = response.getData().getMeta().getPagination().getTotalPages();
                    }
                    feedListData = response.getData().getData();
                    adapterFeedList = new AdapterFeedList(feedListData, getActivity());
                    rvFeed.setAdapter(adapterFeedList);
                }
            }
            loading = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {

    }

    public void gotoWebPage(String url){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(WepPageFragment.newInstance(url,1));
        }
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

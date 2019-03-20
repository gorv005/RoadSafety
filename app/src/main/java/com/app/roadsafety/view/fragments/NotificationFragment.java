package com.app.roadsafety.view.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.roadsafety.R;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.model.notification.NotificationData;
import com.app.roadsafety.model.notification.NotificationResponse;
import com.app.roadsafety.presenter.notification.INotificationPresenter;
import com.app.roadsafety.presenter.notification.NotificationPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.notification.AdapterNotificationList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment implements INotificationPresenter.INotificationView {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    Unbinder unbinder;
    AdapterNotificationList adapterNotificationList;
    List<Feed> feeds;
    LinearLayoutManager layoutManager;
    INotificationPresenter iNotificationPresenter;
    AppUtils util;
    List<NotificationData> notificationData;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.ivNoNotification)
    ImageView ivNoNotification;
    @BindView(R.id.rlNoNotification)
    RelativeLayout rlNoNotification;
    @BindView(R.id.rlParentView)
    RelativeLayout rlParentView;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int page = 1, totalPages;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNotificationPresenter = new NotificationPresenterImpl(this, getActivity());
        util = new AppUtils();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.notifications), false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(layoutManager);
        rvNotification.setHasFixedSize(true);
        rvNotification.addOnScrollListener(recyclerViewOnScrollListener);

        if (notificationData != null && notificationData.size() > 0) {
            rvNotification.setVisibility(View.VISIBLE);
            rlNoNotification.setVisibility(View.GONE);
            rlParentView.setBackgroundColor(Color.WHITE);
            adapterNotificationList = new AdapterNotificationList(notificationData, getActivity());
            rvNotification.setAdapter(adapterNotificationList);
        } else {
            getNotificationList("" + page);
        }
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

                if (loading && page < totalPages && totalPages > 1) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        page = page + 1;
                        getNotificationList("" + page);
                    }
                }
            }
        }
    };

    void setFeed() {
        feeds = new ArrayList<>();
        Feed g1 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g1);
        Feed g2 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g2);
        Feed g3 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g3);
        //  adapterNotificationList = new AdapterNotificationList(feeds, getActivity());
        //  rvNotification.setAdapter(adapterNotificationList);
    }

    public void gotoIncidentDescription(String id){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(IncidentDescriptionFragment.newInstance(1,id));
            readNotification(id);
        }
    }
    public void readNotification(int pos,String noti_id,String incident_id){
        notificationData.get(pos).getAttributes().setRead(true);
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(IncidentDescriptionFragment.newInstance(1,incident_id));
            readNotification(noti_id);
        }
    }
    void getNotificationList(String page) {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iNotificationPresenter.getNotification(auth_token, page);

    }
  public  void readNotification(String id) {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iNotificationPresenter.readNotification(auth_token, id);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSuccessReadNotificationResponse(NotificationResponse response) {

    }

    @Override
    public void onSuccessNotificationResponse(NotificationResponse response) {
        try {
            if (notificationData != null && notificationData.size() > 0 && page != 1) {
                rvNotification.setVisibility(View.VISIBLE);
                notificationData.addAll(response.getData().getData());
                adapterNotificationList.notifyDataSetChanged();
            } else if (response.getData() == null && response.getErrors() != null && response.getErrors().size() > 0) {
                String error = "";
                for (int i = 0; i < response.getErrors().size(); i++) {
                    error = error + response.getErrors().get(i) + "\n";
                }
                util.resultDialog(getActivity(), error);
            } else {
                if (response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {
                    rvNotification.setVisibility(View.VISIBLE);
                    rlNoNotification.setVisibility(View.GONE);
                    rlParentView.setBackgroundColor(Color.WHITE);
                    if (response.getData().getMeta() != null && response.getData().getMeta().getPagination() != null) {
                        totalPages = response.getData().getMeta().getPagination().getTotalPages();
                    }
                    notificationData = response.getData().getData();
                    adapterNotificationList = new AdapterNotificationList(notificationData, getActivity());
                    rvNotification.setAdapter(adapterNotificationList);
                } else {
                    rvNotification.setVisibility(View.GONE);
                    rlNoNotification.setVisibility(View.VISIBLE);
                    rlParentView.setBackgroundColor(Color.GRAY);
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

    @Override
    public void showProgress() {
        util.showDialog(getString(R.string.please_wait), getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }
}

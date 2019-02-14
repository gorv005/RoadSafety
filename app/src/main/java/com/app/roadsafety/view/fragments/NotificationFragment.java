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
import android.widget.ImageView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.notification.AdapterNotificationList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.app.roadsafety.view.fragments.BaseFragment.ARGS_INSTANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    Unbinder unbinder;
    AdapterNotificationList adapterNotificationList;
    List<Feed> feeds;
    LinearLayoutManager layoutManager;
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
        ( (MainActivity)getActivity()).updateToolbarTitle(getString(R.string.notifications),true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(layoutManager);
        rvNotification.setHasFixedSize(true);
        setFeed();
    }

    void setFeed() {
        feeds = new ArrayList<>();
        Feed g1 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g1);
        Feed g2 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g2);
        Feed g3 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
        feeds.add(g3);
        adapterNotificationList = new AdapterNotificationList(feeds, getActivity());
        rvNotification.setAdapter(adapterNotificationList);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rvNotification)
    public void onViewClicked() {
    }
}

package com.app.roadsafety.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;
import com.app.roadsafety.presenter.incident.IIncidentListPresenter;
import com.app.roadsafety.presenter.incident.IncidentListPresenterPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.IncidentImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentDescriptionFragment extends BaseFragment implements IIncidentListPresenter.IIncidentView {


    @BindView(R.id.vp_adds)
    ViewPager vpAdds;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tvFeedTitle)
    TextView tvFeedTitle;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.ivAddImage)
    ImageView ivAddImage;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;
    IIncidentListPresenter iIncidentListPresenter;
    AppUtils util;
    String incidentId;
    List<String> mImageList;

    public IncidentDescriptionFragment() {
        // Required empty public constructor
    }

    public static IncidentDescriptionFragment newInstance(int instance, String id) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString(AppConstants.INCIDENT_ID, id);
        IncidentDescriptionFragment fragment = new IncidentDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.map), false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new AppUtils();
        mImageList = new ArrayList<>();
        iIncidentListPresenter = new IncidentListPresenterPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incident_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        incidentId = getArguments().getString(AppConstants.INCIDENT_ID);
        getDetailsOfIncidents();

    }


    void getDetailsOfIncidents() {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iIncidentListPresenter.getIncidentDetails(auth_token, incidentId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessIncidentListResponse(IncidentResponse response) {

    }

    @Override
    public void onSuccessIncidentDetailsResponse(IncidentDetailResponse response) {
        if (response != null && response.getData() != null) {
            tvDescription.setText(response.getData().getAttributes().getDescription());
            for (int i = 0; i < response.getData().getAttributes().getImages().size(); i++) {
                mImageList.add(response.getData().getAttributes().getImages().get(i));
            }
            vpAdds.setAdapter(new IncidentImageViewPagerAdapter(getActivity().getSupportFragmentManager(), mImageList,AppConstants.IS_FROM_REMOTE));
            tabLayout.setupWithViewPager(vpAdds, true);
        } else if (response.getData() == null && response.getErrors() != null && response.getErrors().size() > 0) {
            String error = "";
            for (int i = 0; i < response.getErrors().size(); i++) {
                error = error + response.getErrors().get(i) + "\n";
            }
            util.resultDialog(getActivity(), error);
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

    @OnClick(R.id.ivback)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }
}

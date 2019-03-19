package com.app.roadsafety.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.profile.ProfileResponse;
import com.app.roadsafety.presenter.profile.IProfilePresenter;
import com.app.roadsafety.presenter.profile.ProfilePresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.ImageUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends BaseFragment implements IProfilePresenter.IProfileView {



    @BindView(R.id.ivProfile)
    CircleImageView ivProfile;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.rlGuideLineView)
    RelativeLayout rlGuideLineView;
    Unbinder unbinder;
    IProfilePresenter iProfilePresenter;
    AppUtils util;

    public FragmentProfile() {
        // Required empty public constructor
    }
    public static FragmentProfile newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FragmentProfile fragment = new FragmentProfile();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iProfilePresenter = new ProfilePresenterImpl(this, getActivity());
        util = new AppUtils();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.map), false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!SharedPreference.getInstance(getActivity()).getBoolean(AppConstants.IS_GUEST_LOGIN)) {

            getProfile();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void getProfile() {
        String id = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getId();
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iProfilePresenter.getProfile(auth_token, id);
    }

    @Override
    public void onSuccessProfileResponse(ProfileResponse response) {
        try {
            if (response.getData() == null && response.getErrors() != null && response.getErrors().size() > 0) {

                String error = "";
                for (int i = 0; i < response.getErrors().size(); i++) {
                    error = error + response.getErrors().get(i) + "\n";
                }
                util.resultDialog(getActivity(), error);
            } else {
                if (response.getData().getLinks().getProfilePicture() == null || response.getData().getLinks().getProfilePicture().equals("")) {
                    ImageUtils.loadImage(getActivity(), "profile_image", ivProfile);
                } else {
                    ImageUtils.setImage(getActivity(), response.getData().getLinks().getProfilePicture(), ivProfile);

                }
                tvName.setText(response.getData().getAttributes().getName());
                tvEmail.setText(response.getData().getAttributes().getEmail());

            }
        }
        catch (Exception e){
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

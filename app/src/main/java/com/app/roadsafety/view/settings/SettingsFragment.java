package com.app.roadsafety.view.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.roadsafety.R;
import com.app.roadsafety.view.IncidentListReported;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.NotificationActivity;
import com.app.roadsafety.view.adapter.incidents.AdapterIncidentList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    @BindView(R.id.rlProfile)
    RelativeLayout rlProfile;
    @BindView(R.id.rlLocationServices)
    RelativeLayout rlLocationServices;
    @BindView(R.id.rlNotifications)
    RelativeLayout rlNotifications;
    @BindView(R.id.rlLinkedAccount)
    RelativeLayout rlLinkedAccount;
    @BindView(R.id.rlLogout)
    RelativeLayout rlLogout;
    Unbinder unbinder;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlProfile, R.id.rlLocationServices, R.id.rlNotifications, R.id.rlLinkedAccount, R.id.rlLogout})
    public void onViewClicked(View view) {
        Intent intent ;
        switch (view.getId()) {


            case R.id.rlProfile:
                break;
            case R.id.rlLocationServices:
                intent = new Intent(getActivity(), IncidentListReported.class);
                startActivity(intent);
                break;
            case R.id.rlNotifications:
                 intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.rlLinkedAccount:
                break;
            case R.id.rlLogout:
                break;
        }
    }
}

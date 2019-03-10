package com.app.roadsafety.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.UserLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment {


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
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.settings), false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlProfile, R.id.rlLocationServices, R.id.rlNotifications, R.id.rlLinkedAccount, R.id.rlLogout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {


            case R.id.rlProfile:
                if (!SharedPreference.getInstance(getActivity()).getBoolean(AppConstants.IS_GUEST_LOGIN)) {
                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(FragmentProfile.newInstance(1));
                    }
                }
                break;
            case R.id.rlLocationServices:
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(FragmentLocationServices.newInstance(1));
                }
                break;
            case R.id.rlNotifications:
                 /*intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);*/
                //   ((MainActivity)getActivity()).fragmentLoader(AppConstants.FRAGMENT_NOTIFICATION,null);
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(NotificationFragment.newInstance(1));

                }
                break;
            case R.id.rlLinkedAccount:
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(FragmentLinkedAccounts.newInstance(1));
                }
                break;
            case R.id.rlLogout:
                logoutDialog(getActivity(),getString(R.string.logout_confirm));
                break;
        }
    }


    public void logoutDialog(Context context, String msg) {

        final Dialog dialog = new Dialog(context, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.result_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView textView=(TextView)dialog.findViewById(R.id.tvMsg);
        textView.setText(msg);
        Button btnOk=(Button)dialog.findViewById(R.id.btnOk);
        ImageView ivCross=(ImageView)dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                gotoStart();
            }
        });
//to set the message
        dialog.show();
    }

    void gotoStart(){
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        intent.putExtra(AppConstants.TAB_SELECTION,1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

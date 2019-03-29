package com.app.roadsafety.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.app.roadsafety.model.notification.NotificationResponse;
import com.app.roadsafety.presenter.notification.INotificationPresenter;
import com.app.roadsafety.presenter.notification.NotificationPresenterImpl;
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
public class SettingsFragment extends BaseFragment implements INotificationPresenter.INotificationView {


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
    INotificationPresenter iNotificationPresenter;
    @BindView(R.id.tvNotificationCount)
    TextView tvNotificationCount;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iNotificationPresenter = new NotificationPresenterImpl(this, getActivity());

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNotificationList("" + 1);
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

    @OnClick({R.id.rlProfile, R.id.rlLocationServices,
            R.id.rlNotifications, R.id.rlLinkedAccount, R.id.rlLogout,R.id.rlTerms,R.id.rlPrivacy,R.id.rlContactUs})
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
                alertDialog(getString(R.string.logout_confirm));
                break;
            case R.id.rlTerms:
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(FragmentTermsAndServices.newInstance(1,1));
                }
                break;
            case R.id.rlPrivacy:
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(FragmentTermsAndServices.newInstance(1,2));
                }
                break;
            case R.id.rlContactUs:
                contactUs(getActivity(),getString(R.string.email));
                break;
        }
    }

    void getNotificationList(String page) {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iNotificationPresenter.getNotification(auth_token, page);

    }
    public void alertDialog(String msg) {

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.alert_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnDelete = (Button) dialog.findViewById(R.id.btnDelete);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(msg);
        btnDelete.setText(getString(R.string.ok));
        btnCancel.setText(getString(R.string.cancel));
        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SharedPreference.getInstance(getActivity()).setBoolean(AppConstants.IS_LOGIN, false);
                SharedPreference.getInstance(getActivity()).setString(AppConstants.REGION, null);
                gotoStart();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

//to set the message
        dialog.show();
    }

    public void contactUs(Context context, String msg) {

        final Dialog dialog = new Dialog(context, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.contact_us_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView textView = (TextView) dialog.findViewById(R.id.tvMsg);
        textView.setText(msg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={getString(R.string.email)};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setText(getString(R.string.cancel));
        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);
        ivCross.setVisibility(View.GONE);
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
            }
        });
//to set the message
        dialog.show();
    }

    void gotoStart() {
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        intent.putExtra(AppConstants.TAB_SELECTION, 1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onSuccessReadNotificationResponse(NotificationResponse response) {

    }

    @Override
    public void onSuccessNotificationResponse(NotificationResponse response) {
        try {
            if (response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {
                if (response.getData().getMeta() != null && response.getData().getMeta().getPagination() != null) {
                    tvNotificationCount.setText("" + response.getData().getMeta().getPagination().getTotalEntries());
                    tvNotificationCount.setVisibility(View.VISIBLE);

                }
            } else {
                tvNotificationCount.setVisibility(View.GONE);
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
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }
}

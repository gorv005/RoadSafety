package com.app.roadsafety.view.fragments;


import android.app.Dialog;
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
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocationServices extends BaseFragment {


    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.llNotification)
    RelativeLayout llNotification;
    Unbinder unbinder;

    public FragmentLocationServices() {
        // Required empty public constructor
    }

    public static FragmentLocationServices newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FragmentLocationServices fragment = new FragmentLocationServices();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (SharedPreference.getInstance(getActivity()).getBoolean(AppConstants.IS_LOCATION_SERVICES_ON)) {
            switchButton.setChecked(true);
        } else {
            switchButton.setChecked(false);
        }
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (!isChecked) {
                    alertDialog(getString(R.string.are_you_sure_you_want_to_disable_location_services));
                } else {
                    SharedPreference.getInstance(getActivity()).setBoolean(AppConstants.IS_LOCATION_SERVICES_ON, true);

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.location_services), true);

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
        btnDelete.setText(getString(R.string.disable));
        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                switchButton.setChecked(true);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                switchButton.setChecked(false);
                SharedPreference.getInstance(getActivity()).setBoolean(AppConstants.IS_LOCATION_SERVICES_ON, false);

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                switchButton.setChecked(true);
                SharedPreference.getInstance(getActivity()).setBoolean(AppConstants.IS_LOCATION_SERVICES_ON, true);

            }
        });

//to set the message
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

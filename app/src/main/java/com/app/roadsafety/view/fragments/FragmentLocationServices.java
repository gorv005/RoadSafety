package com.app.roadsafety.view.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.GpsUtils;
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
    boolean isListner=false;
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
        switchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isListner=false;
                return false;
            }
        });
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(!isListner) {
                    if (!isChecked) {
                        alertDialog(getString(R.string.are_you_sure_you_want_to_disable_location_services));
                    } else {
                        GpsEnable();
                    }
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.location_services), true);
       checkGps();

    }

    void checkGps(){
        isListner=true;
        boolean lc=AppUtils.isLocationServiceEnabled(getActivity());
        if(lc){
            switchButton.setChecked(true);
        }
        else {

            switchButton.setChecked(false);
        }
    }
    void GpsEnable() {
        new GpsUtils(getActivity()).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS

            }
        });
    }
    private void turnGPSOff(){
      Intent  intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent1);
       /* String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getActivity().sendBroadcast(poke);
        }*/
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

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                turnGPSOff();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                checkGps();

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

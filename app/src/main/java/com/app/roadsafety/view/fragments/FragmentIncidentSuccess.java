package com.app.roadsafety.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.roadsafety.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIncidentSuccess extends BaseFragment {


    public FragmentIncidentSuccess() {
        // Required empty public constructor
    }
    public static FragmentIncidentSuccess newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        FragmentIncidentSuccess fragment = new FragmentIncidentSuccess();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incident_success, container, false);
    }

}

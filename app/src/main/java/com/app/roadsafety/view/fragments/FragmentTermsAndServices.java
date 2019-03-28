package com.app.roadsafety.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTermsAndServices extends BaseFragment {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvData)
    TextView tvData;
    Unbinder unbinder;
    int type;
    public FragmentTermsAndServices() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       type= getArguments().getInt(AppConstants.TYPE);
    }
    public static FragmentTermsAndServices newInstance(int instance,int type) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putInt(AppConstants.TYPE, type);
        FragmentTermsAndServices fragment = new FragmentTermsAndServices();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terms_and_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvData.setMovementMethod(new ScrollingMovementMethod());
        if(type==1){
            tvTitle.setText(getString(R.string.terms));
        }
        else {
            tvTitle.setText(getString(R.string.privacy));
        }

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
}

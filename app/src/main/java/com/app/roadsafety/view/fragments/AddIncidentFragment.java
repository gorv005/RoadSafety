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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.roadsafety.R;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.IncidentImageViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.roadsafety.view.fragments.BaseFragment.ARGS_INSTANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIncidentFragment extends Fragment {


    @BindView(R.id.vp_adds)
    ViewPager vpAdds;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.spninner)
    Spinner spninner;
    @BindView(R.id.etLocation)
    EditText etLocation;
    @BindView(R.id.spninner1)
    Spinner spninner1;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private Menu menu;

    public AddIncidentFragment() {
        // Required empty public constructor
    }

    public static AddIncidentFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        AddIncidentFragment fragment = new AddIncidentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        ( (MainActivity)getActivity()).updateToolbarTitle(getString(R.string.map),false);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_incident, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vpAdds.setAdapter(new IncidentImageViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(vpAdds, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
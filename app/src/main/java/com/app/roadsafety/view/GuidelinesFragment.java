package com.app.roadsafety.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.roadsafety.R;
import com.app.roadsafety.model.Guidelines;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuidelinesFragment extends Fragment {


    @BindView(R.id.image_guideline)
    CircleImageView imageGuideline;
    Unbinder unbinder;

    Guidelines guidelines;
    @BindView(R.id.rlGuideLineView)
    RelativeLayout rlGuideLineView;

    public static GuidelinesFragment newInstance(Guidelines guidelines) {
        GuidelinesFragment fragmentFirst = new GuidelinesFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.GUIDELINES_DATA, guidelines);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public GuidelinesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guidelines = (Guidelines) getArguments().getSerializable(AppConstants.GUIDELINES_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guidelines, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlGuideLineView.setBackgroundColor(Color.parseColor(guidelines.getColorCode()));
        ImageUtils.loadImage(getActivity(),guidelines.getImage(),imageGuideline);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

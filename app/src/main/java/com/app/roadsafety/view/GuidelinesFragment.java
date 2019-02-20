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
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.guidelines.GuidelinesResponseDataList;
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

    GuidelinesResponseDataList guidelines;
    @BindView(R.id.rlGuideLineView)
    RelativeLayout rlGuideLineView;
    String colorCode[] = {"#1EB8DD", "#9E89FF", "#FEDB61", "#1EB8DD", "#9E89FF"};
    int pos;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDesc)
    TextView tvDesc;

    public static GuidelinesFragment newInstance(GuidelinesResponseDataList guidelines, int pos) {
        GuidelinesFragment fragmentFirst = new GuidelinesFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.GUIDELINES_DATA, guidelines);
        args.putInt(AppConstants.POS, pos);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public GuidelinesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guidelines = (GuidelinesResponseDataList) getArguments().getSerializable(AppConstants.GUIDELINES_DATA);
        pos = getArguments().getInt(AppConstants.POS);

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
        rlGuideLineView.setBackgroundColor(Color.parseColor(colorCode[pos]));
        // ImageUtils.loadImage(getActivity(),guidelines.getImage(),imageGuideline);
        ImageUtils.setImage(getActivity(), guidelines.getLinks().getOriginalImage(), imageGuideline);
        tvTitle.setText(guidelines.getAttributes().getTitle());
        tvDesc.setText(guidelines.getAttributes().getDescription());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

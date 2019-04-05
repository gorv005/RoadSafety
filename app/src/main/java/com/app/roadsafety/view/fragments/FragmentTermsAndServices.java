package com.app.roadsafety.view.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.terms.TermsResponse;
import com.app.roadsafety.presenter.terms.ITermsPresenter;
import com.app.roadsafety.presenter.terms.TermsPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTermsAndServices extends BaseFragment implements ITermsPresenter.ITermsView {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvData)
    TextView tvData;
    Unbinder unbinder;
    int type;
    ITermsPresenter iTermsPresenter;
    AppUtils utils;
    public FragmentTermsAndServices() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       type= getArguments().getInt(AppConstants.TYPE);
       iTermsPresenter=new TermsPresenterImpl(this,getActivity());
       utils=new AppUtils();
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
            getTermsAndServices("terms_of_service");
        }
        else {
            tvTitle.setText(getString(R.string.privacy));
            getTermsAndServices("privacy_policy");

        }

    }

    void getTermsAndServices(String name) {
      //  String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iTermsPresenter.termsAndServices("", name);

    }
    @Override
    public void onResume() {
        super.onResume();
        if(type==1){
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.terms), true);
        }
        else {
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.privacy), true);

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessTermsResponse(TermsResponse response) {
        if(response!=null && response.getData()!=null && response.getData().getAttributes()!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvData.setText(Html.fromHtml(response.getData().getAttributes().getValue(), Html.FROM_HTML_MODE_LEGACY));

            }
            else {
                tvData.setText(Html.fromHtml(response.getData().getAttributes().getValue()));
            }
        }
    }

    @Override
    public void getResponseError(String response) {

    }


    @Override
    public void showProgress() {
        utils.showDialog(getString(R.string.please_wait), getActivity());
    }

    @Override
    public void hideProgress() {
        utils.hideDialog();
    }

}

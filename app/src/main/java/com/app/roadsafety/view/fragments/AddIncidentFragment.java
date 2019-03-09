package com.app.roadsafety.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roadsafety.R;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.presenter.createIncident.CreateIncidentPresenterImpl;
import com.app.roadsafety.presenter.createIncident.ICreateIncidentPresenter;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.IncidentImageViewPagerAdapter;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIncidentFragment extends BaseFragment implements ICreateIncidentPresenter.ICreateIncidentView {


    @BindView(R.id.vp_adds)
    ViewPager vpAdds;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.spninnerCityHall)
    Spinner spninnerCityHall;
    @BindView(R.id.etLocation)
    EditText etLocation;
    @BindView(R.id.spninnerType)
    Spinner spninnerType;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.ivAddImage)
    ImageView ivAddImage;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tvFeedTitle)
    TextView tvFeedTitle;
    List<String> mImageList;
    ICreateIncidentPresenter iCreateIncidentPresenter;
    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayList<String> cityHallList;
    ArrayList<String> type;
    @BindView(R.id.btnDone)
    Button btnDone;
    String mType, mCityHallId, incidentAction;
    AppUtils util;
    String latitude, longitude;
    CityHallResponse cityHallResponse;
    IncidentDetailResponse incidentDetailResponse;

    public AddIncidentFragment() {
        // Required empty public constructor
    }

    public static AddIncidentFragment newInstance(int instance, String latitude, String longitude, String action, IncidentDetailResponse incidentDetailResponse) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString(AppConstants.LATITUDE, latitude);
        args.putSerializable(AppConstants.INCIDENT_DATA, incidentDetailResponse);
        args.putString(AppConstants.INCIDENT_ACTION, action);
        args.putString(AppConstants.LONGITUDE, longitude);
        AddIncidentFragment fragment = new AddIncidentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageList = new ArrayList<>();
        cityHallList = new ArrayList<>();
        type = new ArrayList<>();
        util = new AppUtils();
        iCreateIncidentPresenter = new CreateIncidentPresenterImpl(this, getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.map), false);

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
        getCityHall();

        latitude = getArguments().getString(AppConstants.LATITUDE);
        longitude = getArguments().getString(AppConstants.LONGITUDE);
        incidentAction = getArguments().getString(AppConstants.INCIDENT_ACTION);
        if (incidentAction.endsWith(AppConstants.INCIDENT_ACTION_EDIT)) {
            incidentDetailResponse = (IncidentDetailResponse) getArguments().getSerializable(AppConstants.INCIDENT_DATA);
        }
        initValues();
        spninnerCityHall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mCityHallId = cityHallResponse.getData().getData().get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spninnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mType = "accident";
                } else {
                    mType = "necessary_intervention";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void setEditValue() {
        etDescription.setText(incidentDetailResponse.getData().getAttributes().getDescription());
        etLocation.setText("Lati. " + latitude + "   " + "Long. " + longitude);
        if (incidentDetailResponse.getData().getAttributes().getType().equals("accident")) {
            spninnerType.setSelection(0);
        } else {
            spninnerType.setSelection(1);
        }
        for (int i = 0; i < incidentDetailResponse.getData().getAttributes().getImages().size(); i++) {
            mImageList.add(incidentDetailResponse.getData().getAttributes().getImages().get(i));
        }
        vpAdds.setAdapter(new IncidentImageViewPagerAdapter(getActivity().getSupportFragmentManager(), mImageList, AppConstants.IS_FROM_REMOTE));
        tabLayout.setupWithViewPager(vpAdds, true);
    }

    void initValues() {
        type.add(getString(R.string.accident));
        type.add(getString(R.string.necessary_intervention));
        spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        type);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spninnerType.setAdapter(spinnerArrayAdapter);

        etLocation.setText("Lati. " + latitude + "   " + "Long. " + longitude);
    }

    void init() {
        new ImagePicker.Builder(getActivity())
                .mode(ImagePicker.Mode.CAMERA)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.JPG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            mImageList.add(mPaths.get(0));
            //Your Code
        }

        vpAdds.setAdapter(new IncidentImageViewPagerAdapter(getActivity().getSupportFragmentManager(), mImageList, AppConstants.IS_FROM_INTERNAL_STORAGE));
        tabLayout.setupWithViewPager(vpAdds, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ivAddImage, R.id.ivback, R.id.btnDone})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ivAddImage:
                init();
                break;
            case R.id.ivback:
                getActivity().onBackPressed();
                break;
            case R.id.btnDone:
                if(etDescription.getText().toString().length()>0) {

                    if (incidentAction.equals(AppConstants.INCIDENT_ACTION_EDIT)) {
                        updateIncident();
                    } else {
                        createIncident();

                    }
                }
                else {
                    Toast.makeText(getActivity(),getString(R.string.description_error),Toast.LENGTH_LONG).show();
                }
                break;
        }

    }


    void getCityHall() {
        iCreateIncidentPresenter.getCityHall();

    }

    void createIncident() {
            CreateIncidentRequest createIncidentRequest = new CreateIncidentRequest();
            createIncidentRequest.setDescription(etDescription.getText().toString());
            createIncidentRequest.setCityHallId(Integer.parseInt(mCityHallId));
            createIncidentRequest.setLatitude(latitude);
            createIncidentRequest.setLongitude(longitude);
            List<String> strings = new ArrayList<>();
            strings.add("http://placehold.it/120x120&text=image1");
            createIncidentRequest.setImages(strings);
            createIncidentRequest.setType(mType);
            String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();

            iCreateIncidentPresenter.createIncident(auth_token, createIncidentRequest);

    }

    void updateIncident() {
        CreateIncidentRequest createIncidentRequest = new CreateIncidentRequest();
        createIncidentRequest.setDescription(etDescription.getText().toString());
        createIncidentRequest.setCityHallId(Integer.parseInt(mCityHallId));
        createIncidentRequest.setLatitude(latitude);
        createIncidentRequest.setLongitude(longitude);
        List<String> strings = new ArrayList<>();
        strings.add("http://placehold.it/120x120&text=image1");
        createIncidentRequest.setImages(strings);
        createIncidentRequest.setType(mType);
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iCreateIncidentPresenter.updateIncident(auth_token, incidentDetailResponse.getData().getId(), createIncidentRequest);
    }

    @Override
    public void onSuccessCreateIncidentResponse(CreateIncidentResponse response) {
        Log.e("DEBUG", "Incident Created");
          if(response.getData()==null && response.getErrors()!=null && response.getErrors().size()>0){
            String error="";
            for(int i=0;i<response.getErrors().size();i++){
                error=error+response.getErrors().get(i)+"\n";
            }
            util.resultDialog(getActivity(),error);
        }
        else {
              Toast.makeText(getActivity(),getString(R.string.incident_created),Toast.LENGTH_LONG).show();
              Intent intent = new Intent(getActivity(), MainActivity.class);
              intent.putExtra(AppConstants.TAB_SELECTION,1);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
          }
    }

    @Override
    public void onSuccessCityHallResponse(CityHallResponse response) {
        cityHallResponse = response;
        for (int i = 0; i < response.getData().getData().size(); i++) {
            cityHallList.add(response.getData().getData().get(i).getAttributes().getName());
        }
        spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        cityHallList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spninnerCityHall.setAdapter(spinnerArrayAdapter);

        if (incidentAction.equals(AppConstants.INCIDENT_ACTION_EDIT)) {
            setEditValue();
        }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void onSuccessUpdateIncidentResponse(CreateIncidentResponse response) {
        if(response.getData()==null && response.getErrors()!=null && response.getErrors().size()>0){
            String error="";
            for(int i=0;i<response.getErrors().size();i++){
                error=error+response.getErrors().get(i)+"\n";
            }
            util.resultDialog(getActivity(),error);
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.incident_update), Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onSuccessReportAbuseIncidentResponse(ReportAbuseIncidentResponse response) {

    }

    @Override
    public void onSuccessDeleteIncidentResponse(ResponseBody response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(getString(R.string.please_wait), getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }
}

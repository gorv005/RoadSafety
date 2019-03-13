package com.app.roadsafety.view.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roadsafety.R;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentRequest;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;
import com.app.roadsafety.presenter.createIncident.CreateIncidentPresenterImpl;
import com.app.roadsafety.presenter.createIncident.ICreateIncidentPresenter;
import com.app.roadsafety.presenter.incident.IIncidentListPresenter;
import com.app.roadsafety.presenter.incident.IncidentListPresenterPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.IncidentImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentDescriptionFragment extends BaseFragment implements IIncidentListPresenter.IIncidentView, ICreateIncidentPresenter.ICreateIncidentView {


    @BindView(R.id.vp_adds)
    ViewPager vpAdds;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tvFeedTitle)
    TextView tvFeedTitle;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.ivAddImage)
    ImageView ivAddImage;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;
    IIncidentListPresenter iIncidentListPresenter;
    ICreateIncidentPresenter iCreateIncidentPresenter;
    AppUtils util;
    String incidentId;
    List<String> mImageList;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    String latitude, longitude;
    IncidentDetailResponse incidentDetailResponse;

    public IncidentDescriptionFragment() {
        // Required empty public constructor
    }

    public static IncidentDescriptionFragment newInstance(int instance, String id) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString(AppConstants.INCIDENT_ID, id);
        IncidentDescriptionFragment fragment = new IncidentDescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.map), false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new AppUtils();
        mImageList = new ArrayList<>();
        iIncidentListPresenter = new IncidentListPresenterPresenterImpl(this, getActivity());
        iCreateIncidentPresenter = new CreateIncidentPresenterImpl(this, getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incident_description, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        incidentId = getArguments().getString(AppConstants.INCIDENT_ID);
        getDetailsOfIncidents();

    }


    void getDetailsOfIncidents() {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iIncidentListPresenter.getIncidentDetails(auth_token, incidentId);
    }


    void deleteIncident() {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();

        iCreateIncidentPresenter.deleteIncident(auth_token, incidentId);
    }

    void reportAbuseIncident(String comment) {
        String auth_token = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        ReportAbuseIncidentRequest reportAbuseIncidentRequest = new ReportAbuseIncidentRequest();
        reportAbuseIncidentRequest.setComments(comment);
        iCreateIncidentPresenter.reportAbuseIncident(auth_token, incidentId, reportAbuseIncidentRequest);
    }

    void gotoUpdateIncident() {
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(AddIncidentFragment.newInstance(1, latitude, longitude, AppConstants.INCIDENT_ACTION_EDIT, incidentDetailResponse));
        }
    }


    public void alertDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.alert_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnDelete = (Button) dialog.findViewById(R.id.btnDelete);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

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
                deleteIncident();

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


    public void reportAbuseDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.report_abuse_comment_dailog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final EditText etComment = (EditText) dialog.findViewById(R.id.etComment);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);

        ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etComment.getText().toString().length()>0) {
                    dialog.dismiss();

                    reportAbuseIncident(etComment.getText().toString());
                }
                else {
                    Toast.makeText(getActivity(),getString(R.string.comment_error),Toast.LENGTH_LONG).show();
                }
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

    @Override
    public void onSuccessIncidentListResponse(IncidentResponse response) {

    }

    @Override
    public void onSuccessIncidentDetailsResponse(IncidentDetailResponse response) {
        if (response != null && response.getData() != null) {
            incidentDetailResponse = response;
            tvDescription.setText(response.getData().getAttributes().getDescription());
            tvDate.setText(AppUtils.getDate(response.getData().getAttributes().getCreatedAt()));
            latitude = "" + response.getData().getAttributes().getLatitude();
            longitude = "" + response.getData().getAttributes().getLongitude();
            mImageList.clear();
            for (int i = 0; i < response.getData().getAttributes().getImages().size(); i++) {
                mImageList.add(response.getData().getAttributes().getImages().get(i));
            }
            vpAdds.setAdapter(new IncidentImageViewPagerAdapter(getActivity().getSupportFragmentManager(), mImageList, AppConstants.IS_FROM_REMOTE));
            tabLayout.setupWithViewPager(vpAdds, true);
            Address address=util.getAddress(getActivity(),Double.parseDouble(latitude),Double.parseDouble(longitude));
            setAddress(address);

        } else if (response.getData() == null && response.getErrors() != null && response.getErrors().size() > 0) {
            String error = "";
            for (int i = 0; i < response.getErrors().size(); i++) {
                error = error + response.getErrors().get(i) + "\n";
            }
            util.resultDialog(getActivity(), error);
        }
    }


    void setAddress(Address locationAddress) {
        String address = locationAddress.getAddressLine(0);
        String address1 = locationAddress.getAddressLine(1);
        String city = locationAddress.getLocality();
        String state = locationAddress.getAdminArea();
        String country = locationAddress.getCountryName();
        String postalCode = locationAddress.getPostalCode();

        String currentLocation;

        if (!TextUtils.isEmpty(address)) {
            currentLocation = address;

            if (!TextUtils.isEmpty(address1))
                currentLocation += " " + address1;

            if (!TextUtils.isEmpty(city)) {
                currentLocation += " " + city;

                if (!TextUtils.isEmpty(postalCode))
                    currentLocation += " - " + postalCode;
            } else {
                if (!TextUtils.isEmpty(postalCode))
                    currentLocation += " " + postalCode;
            }

            if (!TextUtils.isEmpty(state))
                currentLocation += " " + state;

            if (!TextUtils.isEmpty(country))
                currentLocation += " " + country;

            tvAddress.setText(currentLocation);
        }
    }
    @Override
    public void onSuccessCreateIncidentResponse(CreateIncidentResponse response) {

    }

    @Override
    public void onSuccessCityHallResponse(CityHallResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void onSuccessUpdateIncidentResponse(CreateIncidentResponse response) {

    }

    @Override
    public void onSuccessReportAbuseIncidentResponse(ReportAbuseIncidentResponse response) {
        if (response.getData() == null && response.getErrors() != null && response.getErrors().size() > 0) {
            String error = "";
            for (int i = 0; i < response.getErrors().size(); i++) {
                error = error + response.getErrors().get(i) + "\n";
            }
            util.resultDialog(getActivity(), error);
        } else {
            Toast.makeText(getActivity(), getString(R.string.report_abuse_msg), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onSuccessDeleteIncidentResponse(ResponseBody response) {

        //  Log.e("DEBUG","DELETE SUCCESS");
        Toast.makeText(getActivity(), getString(R.string.incident_deleted_success), Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        intent.putExtra(AppConstants.TAB_SELECTION,1);
//        startActivity(intent);
        getActivity().onBackPressed();
    }

    @Override
    public void showProgress() {
        util.showDialog(getString(R.string.please_wait), getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }


    @OnClick({R.id.ivMenu, R.id.ivback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivMenu:
                int[] location = new int[2];

                // Get the x, y location and store it in the location[] array
                // location[0] = x, location[1] = y.
                view.getLocationOnScreen(location);

                //Initialize the Point with x, and y positions
                Point point = new Point();
                point.x = location[0];
                point.y = location[1];
                String id = SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getId();

                if (id.equals(""+incidentDetailResponse.getData().getAttributes().getUserId())) {
                    showCrudPopup(getActivity(), point);
                } else {
                    showReportAbusePopup(getActivity(), point);
                }
                break;

            case R.id.ivback:
                getActivity().onBackPressed();

                break;
        }
    }

    private void showReportAbusePopup(final Activity context, Point p) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.abuse_pop_up, null);

        // Creating the PopupWindow
        final PopupWindow changeStatusPopUp = new PopupWindow(context);
        changeStatusPopUp.setContentView(layout);
        // changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        //changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 50;
        int OFFSET_Y = 60;

        //Clear the default translucent background
        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        TextView tvReportAbuse = (TextView) layout.findViewById(R.id.tvReportAbuse);

        tvReportAbuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusPopUp.dismiss();
                reportAbuseDialog();


            }
        });

    }

    private void showCrudPopup(final Activity context, Point p) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.crud_pop_up, null);

        // Creating the PopupWindow
        final PopupWindow changeStatusPopUp = new PopupWindow(context);
        changeStatusPopUp.setContentView(layout);
        // changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        //changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 50;
        int OFFSET_Y = 60;

        //Clear the default translucent background
        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        TextView tvEdit = (TextView) layout.findViewById(R.id.tvEdit);
        TextView tvDelete = (TextView) layout.findViewById(R.id.tvDelete);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusPopUp.dismiss();
                Log.e("DEBUG", "EDIT");
                gotoUpdateIncident();

            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DEBUG", "DELETE");
                changeStatusPopUp.dismiss();
                alertDialog();

            }
        });


    }

}

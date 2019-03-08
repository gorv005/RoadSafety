package com.app.roadsafety.view.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roadsafety.R;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.model.incidents.IncidentDataRes;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;
import com.app.roadsafety.presenter.authentication.AuthenticationPresenterImpl;
import com.app.roadsafety.presenter.authentication.IAuthenticationPresenter;
import com.app.roadsafety.presenter.incident.IIncidentListPresenter;
import com.app.roadsafety.presenter.incident.IncidentListPresenterPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.GpsUtils;
import com.app.roadsafety.utility.ImageUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.incidents.AdapterIncidentHorizontalList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.greenhalolabs.facebooklogin.FacebookLoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentMapsFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, IAuthenticationPresenter.IAuthenticationView, IIncidentListPresenter.IIncidentView {

    @BindView(R.id.mapview)
    MapView mapview;
    Unbinder unbinder;
    @BindView(R.id.llFilterIncident)
    LinearLayout llFilterIncident;
    @BindView(R.id.ivAddPost)
    ImageView ivAddPost;
    @BindView(R.id.rvIncident)
    RecyclerView rvIncident;
    @BindView(R.id.tvIncidentCount)
    TextView tvIncidentCount;
    @BindView(R.id.ivIncidentArrow)
    ImageView ivIncidentArrow;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    Circle circle;
    CircleOptions circleOptions;
    BottomSheetBehavior mBottomSheetBehavior2;
    AdapterIncidentHorizontalList adapterIncidentList;
    LinearLayoutManager layoutManager;
    List<Feed> feeds;
    List<Feed> markerInfo;
    public Marker marker;
    IAuthenticationPresenter iAuthenticationPresenter;
    IIncidentListPresenter iIncidentListPresenter;
    AppUtils util;
    LatLng latLng;
    String latitude,longitude;
    List<IncidentDataRes>  incidentDataResList;
    boolean isAddLocationOnMap=false;
    public IncidentMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circleOptions = new CircleOptions();
        iAuthenticationPresenter = new AuthenticationPresenterImpl(this, getActivity());
        iIncidentListPresenter=new IncidentListPresenterPresenterImpl(this,getActivity());
        util = new AppUtils();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incident_maps, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View bottomSheet2 = view.findViewById(R.id.bottom_sheet2);
        mBottomSheetBehavior2 = BottomSheetBehavior.from(bottomSheet2);
        mBottomSheetBehavior2.setHideable(false);
        mBottomSheetBehavior2.setPeekHeight(170);
        mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
        circle = null;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        setLocationRequest();

        locationCallback();
        try {
            mapview.onCreate(savedInstanceState);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mapview.getMapAsync(this);
        setBehavior();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvIncident.setLayoutManager(layoutManager);
        rvIncident.setHasFixedSize(true);
        mBottomSheetBehavior2.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    ivIncidentArrow.setImageResource(R.drawable.down_arrow);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    ivIncidentArrow.setImageResource(R.drawable.up_arrow);
                }

            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
    //    setFeed();
    }

    void setFeed() {
        feeds = new ArrayList<>();
        markerInfo = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Feed g1 = new Feed("login_back", getString(R.string.watch_out_big_cars), getString(R.string.feed_desc));
            feeds.add(g1);

        }

        Feed m = new Feed("login_back", getString(R.string.watch_out_big_cars), "dkfjdkfjsg", 28.609170, 28.609170);
        markerInfo.add(m);
        Feed m1 = new Feed("login_back", getString(R.string.watch_out_big_cars), "dkfjdkfjsgvxdhdhdh", 28.616178, 77.351240);
        markerInfo.add(m1);
        Feed m2 = new Feed("login_back", getString(R.string.watch_out_big_cars), "dkfjhfjfjfjjdkfjsgvxdhdhdh", 28.620704, 77.372713);
        markerInfo.add(m2);
        Feed m3 = new Feed("login_back", getString(R.string.watch_out_big_cars),"hdhdh", 28.614978, 77.381881);
        markerInfo.add(m3);
        Feed m4 = new Feed("login_back", getString(R.string.watch_out_big_cars), "dkfj", 28.612567, 77.338555);
        markerInfo.add(m4);
        Feed m5 = new Feed("login_back", getString(R.string.watch_out_big_cars), "czxzczv", 28.624245, 77.361032);
        markerInfo.add(m5);
     //  adapterIncidentList = new AdapterIncidentHorizontalList(feeds, getActivity());
      //  rvIncident.setAdapter(adapterIncidentList);
      //  tvIncidentCount.setText("" + feeds.size() + " " + getString(R.string.incident_reported));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    void setLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000);
    }


    void GpsEnable() {
        new GpsUtils(getActivity()).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;

            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstants.LOCATION_REQUEST);

        } else {
            startLocationUpdates();
        }
    }

    void locationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        setMapLocation(location);

                    }
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.map), false);
        if (mapview != null)
            mapview.onResume();
        GpsEnable();
        getLocation();
    }

    void setMapLocation(Location location) {
        latitude=""+location.getLatitude();
        longitude=""+location.getLongitude();
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        setGeoFence(latLng);
       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
       // markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);*/

       /* if ( mMap != null ) {

            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

            for(int i=0;i<markerInfo.size();i++) {
                final Marker hamburg = mMap.addMarker(new MarkerOptions().position(new LatLng(markerInfo.get(i).getLat(),markerInfo.get(i).getLongi())).title(""+i));
            //    markers.put(hamburg.getId(), "http://img.india-forums.com/images/100x100/37525-a-still-image-of-akshay-kumar.jpg");

            }
        }*/
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                    mMap.setMyLocationEnabled(true);

                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FacebookLoginActivity.FACEBOOK_LOGIN_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                String accessToken = data.getStringExtra(FacebookLoginActivity.EXTRA_FACEBOOK_ACCESS_TOKEN);
                if(accessToken!=null) {
                    Log.e("DEBUG", accessToken);
                    //Toast.makeText(this, "Access Token: " + accessToken, Toast.LENGTH_LONG).show();
                    facebookLogin(accessToken);
                }
            } else {
                String errorMessage = data.getStringExtra(FacebookLoginActivity.EXTRA_ERROR_MESSAGE);
                //Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e("DEBUG", errorMessage);
            }
            //  gotoSelectRegion();
        }
      else   if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setLocationRequest();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastLocation != null)
                setMapLocation(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    void setGeoFence(LatLng point) {
        if (circle == null) {
            // Specifying the center of the circle
            circleOptions.center(point);

            // Radius of the circle
            circleOptions.radius(16.0934 * 1609.34);// Converting Miles into Meters...

            // Border color of the circle
            circleOptions.strokeColor(Color.parseColor("#fff0f5"));

            // Fill color of the circle
            // 0x represents, this is an hexadecimal code
            // 55 represents percentage of transparency. For 100% transparency, specify 00.
            // For 0% transparency ( ie, opaque ) , specify ff
            // The remaining 6 characters(00ff00) specify the fill color
            //circleOptions.fillColor(Color.parseColor("#F8F4E3"));

            // Border width of the circle
            circleOptions.strokeWidth(150);

           /* circle.remove();
        }*/
            // Adding the circle to the GoogleMap
            circle = mMap.addCircle(circleOptions);

            float currentZoomLevel = getZoomLevel(circle);
            float animateZomm = currentZoomLevel + 5;

            Log.e("Zoom Level:", currentZoomLevel + "");
            Log.e("Zoom Level Animate:", animateZomm + "");

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, animateZomm));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel), 2000, null);
            getAllIncidentList();
        }
    }

    public float getZoomLevel(Circle circle) {
        float zoomLevel = 0;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 400;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel + .5f;
    }

    @Override
    public void onStop() {
        super.onStop();
        //    mMap = null;

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapview != null)
            mapview.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapview != null)
            mapview.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();

    }

    @OnClick({R.id.llFilterIncident, R.id.ivAddPost})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llFilterIncident:
                int[] location = new int[2];

                // Get the x, y location and store it in the location[] array
                // location[0] = x, location[1] = y.
                view.getLocationOnScreen(location);

                //Initialize the Point with x, and y positions
                Point point = new Point();
                point.x = location[0];
                point.y = location[1];
                showIncidentPopup(getActivity(), point);
                break;

            case R.id.ivAddPost:
                addIncidentDialog();
                //gotoAddIncident();
                break;
        }
    }


    public void addIncidentDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.add_incident_location_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnAddLocation=(Button)dialog.findViewById(R.id.btnAddLocation);
        Button btnSelectLocationOnMap=(Button)dialog.findViewById(R.id.btnSelectLocationOnMap);

        ImageView ivCross=(ImageView)dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                isAddLocationOnMap=false;
                if(SharedPreference.getInstance(getActivity()).getBoolean(AppConstants.IS_GUEST_LOGIN)){
                   loginDialog();
                }
                else {
                   gotoAddIncident();
                }
            }
        });
        btnSelectLocationOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                isAddLocationOnMap=true;

                if(SharedPreference.getInstance(getActivity()).getBoolean(AppConstants.IS_GUEST_LOGIN)){
                    loginDialog();
                }
                else {
                    gotoAddIncidentOnMap();
                }
            }
        });

//to set the message
        dialog.show();
    }

    public void loginDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.login_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnFacebook=(Button)dialog.findViewById(R.id.btnFacebook);
        Button btnClose=(Button)dialog.findViewById(R.id.btnClose);

        ImageView ivCross=(ImageView)dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String applicationId = getResources().getString(R.string.facebook_app_id);
                ArrayList<String> permissions = new ArrayList<String>();
                permissions.add("public_profile");
                permissions.add("email");
                FacebookLoginActivity.launch(getActivity(), applicationId, permissions);

            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

//to set the message
        dialog.show();
    }

    void getAllIncidentList(){
        String auth_token= SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();

        iIncidentListPresenter.getAllIncidents(auth_token,latitude,longitude,""+10,""+1);
    }
    void facebookLogin(String token) {
        FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest();
        facebookLoginRequest.setAccessToken(token);
        String auth_token= SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iAuthenticationPresenter.connectGuestUserWithFacebook(auth_token,facebookLoginRequest);
    }
    void setBehavior() {
        ivIncidentArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetBehavior2.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ivIncidentArrow.setImageResource(R.drawable.up_arrow);
                } else if (mBottomSheetBehavior2.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
                    ivIncidentArrow.setImageResource(R.drawable.down_arrow);

                }
            }
        });
    }


    private void showIncidentPopup(final Activity context, Point p) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.incident_pop_up, null);

        // Creating the PopupWindow
        PopupWindow changeStatusPopUp = new PopupWindow(context);
        changeStatusPopUp.setContentView(layout);
        // changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        //changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeStatusPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = 60;

        //Clear the default translucent background
        changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
    }

    void gotoAddIncident(){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(AddIncidentFragment.newInstance(1,latitude,longitude));
        }
    }

    void gotoAddIncidentOnMap(){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(AddIncidentLocationFragment.newInstance(1));
        }
    }
    @Override
    public void getFacebookLoginResponse(LoginResponse response) {
        Log.e("DEBUG", "" + response);
        SharedPreference.getInstance(getActivity()).setUser(AppConstants.LOGIN_USER, response);
        SharedPreference.getInstance(getActivity()).setBoolean(AppConstants.IS_LOGIN,true);
        SharedPreference.getInstance(getActivity()).setBoolean(AppConstants.IS_GUEST_LOGIN,false);
        if(isAddLocationOnMap){
         gotoAddIncidentOnMap();
        }
        else {
            gotoAddIncident();
        }
    }

    @Override
    public void getGuestUserResponse(LoginResponse response) {

    }

    @Override
    public void onSuccessIncidentListResponse(IncidentResponse response) {
        if(response.getData()!=null && response.getData().getData()!=null && response.getData().getData().size()>0) {
            incidentDataResList=response.getData().getData();
            if (mMap != null) {

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

                for (int i = 0; i < incidentDataResList.size(); i++) {
                     mMap.addMarker(new MarkerOptions().position(new LatLng(incidentDataResList.get(i).getAttributes().getLatitude(), incidentDataResList.get(i).getAttributes().getLongitude())).title("" + i).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                    //    markers.put(hamburg.getId(), "http://img.india-forums.com/images/100x100/37525-a-still-image-of-akshay-kumar.jpg");

                }

            }
            adapterIncidentList = new AdapterIncidentHorizontalList(incidentDataResList, getActivity());
              rvIncident.setAdapter(adapterIncidentList);
            tvIncidentCount.setText("" + incidentDataResList.size() + " " + getString(R.string.incident_reported));
        }
    }

    public void gotoIncidentList(){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(IncidentListFragment.newInstance(1,latitude,longitude));
        }
    }


    void gotoAddIncidentFromMap(String latitude, String longitude){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(AddIncidentFragment.newInstance(1,latitude,longitude));
        }
    }
    public void gotoIncidentDescription(String id){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(IncidentDescriptionFragment.newInstance(1,id));
        }
    }
    @Override
    public void onSuccessIncidentDetailsResponse(IncidentDetailResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(getString(R.string.please_wait), getActivity());
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            if (IncidentMapsFragment.this.marker != null
                    && IncidentMapsFragment.this.marker.isInfoWindowShown()) {
                IncidentMapsFragment.this.marker.hideInfoWindow();
                IncidentMapsFragment.this.marker.showInfoWindow();
            }
            return null;
        }
        @Override
        public View getInfoWindow(final Marker marker) {
           Log.e( "DEBUG","pos"+marker.getTitle());
            IncidentMapsFragment.this.marker = marker;

            final ImageView image = ((ImageView) view.findViewById(R.id.ivIncident));
            final TextView tvIncidentDesc = ((TextView) view.findViewById(R.id.tvIncidentDesc));
            final TextView tvHours = ((TextView) view.findViewById(R.id.tvHours));
            final Button btnViewMore = ((Button) view.findViewById(R.id.btnViewMore));

            tvHours.setText(AppUtils.getDate(incidentDataResList.get(Integer.parseInt(marker.getTitle())).getAttributes().getCreatedAt()));
            tvIncidentDesc.setText(incidentDataResList.get(Integer.parseInt(marker.getTitle())).getAttributes().getDescription());
            ImageUtils.setImage(getActivity(),incidentDataResList.get(Integer.parseInt(marker.getTitle())).getAttributes().getImages().get(0),image);
            return view;
        }
    }
}

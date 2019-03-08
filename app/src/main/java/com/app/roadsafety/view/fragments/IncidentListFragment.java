package com.app.roadsafety.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.roadsafety.R;
import com.app.roadsafety.model.incidents.IncidentDataRes;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;
import com.app.roadsafety.presenter.incident.IIncidentListPresenter;
import com.app.roadsafety.presenter.incident.IncidentListPresenterPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;
import com.app.roadsafety.view.MainActivity;
import com.app.roadsafety.view.adapter.incidents.AdapterIncidentList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentListFragment extends BaseFragment implements IIncidentListPresenter.IIncidentView {

    LinearLayoutManager layoutManager;
    AdapterIncidentList adapterIncidentList;
    List<IncidentDataRes> incidentDataResList;
    @BindView(R.id.rvIncident)
    RecyclerView rvIncident;
    Unbinder unbinder;
    AppUtils util;
    IIncidentListPresenter iIncidentListPresenter;
    String latitude,longitude;
    int page = 1, totalPages;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new AppUtils();
        iIncidentListPresenter=new IncidentListPresenterPresenterImpl(this,getActivity());

    }

    public static IncidentListFragment newInstance(int instance, String latitude, String longitude) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        args.putString(AppConstants.LATITUDE, latitude);
        args.putString(AppConstants.LONGITUDE, longitude);
        IncidentListFragment fragment = new IncidentListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public IncidentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.incidents), true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incident_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latitude = getArguments().getString(AppConstants.LATITUDE);
        longitude = getArguments().getString(AppConstants.LONGITUDE);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvIncident.setLayoutManager(layoutManager);
        rvIncident.setHasFixedSize(true);
        rvIncident.addOnScrollListener(recyclerViewOnScrollListener);

        page=1;
        getAllIncidentList( page);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) //check for scroll down
            {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (loading && page <totalPages && totalPages > 1) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        page=page+1;
                        getAllIncidentList( page);
                    }
                }
            }
        }
    };



    void getAllIncidentList(int page){
        String auth_token= SharedPreference.getInstance(getActivity()).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();

        iIncidentListPresenter.getAllIncidents(auth_token,latitude,longitude,AppConstants.DISTANCE,""+page);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccessIncidentListResponse(IncidentResponse response) {
        try {
            if (incidentDataResList != null && incidentDataResList.size() > 0 && page != 1) {
                incidentDataResList.addAll(response.getData().getData());
                adapterIncidentList.notifyDataSetChanged();
            }
            else if(response.getData()==null && response.getErrors()!=null && response.getErrors().size()>0){
                String error="";
                for(int i=0;i<response.getErrors().size();i++){
                    error=error+response.getErrors().get(i)+"\n";
                }
                util.resultDialog(getActivity(),error);
            }
            else {
                if (response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {

                    if (response.getData().getMeta() != null && response.getData().getMeta().getPagination() != null) {
                        totalPages = response.getData().getMeta().getPagination().getTotalPages();
                    }
                    incidentDataResList = response.getData().getData();
                    adapterIncidentList = new AdapterIncidentList(incidentDataResList, getActivity());
                    rvIncident.setAdapter(adapterIncidentList);
                }
            }
            loading = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessIncidentDetailsResponse(IncidentDetailResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showProgress() {

    }
}

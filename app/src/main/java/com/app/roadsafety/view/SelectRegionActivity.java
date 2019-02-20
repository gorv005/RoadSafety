package com.app.roadsafety.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.roadsafety.R;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;
import com.app.roadsafety.model.region.User;
import com.app.roadsafety.presenter.region.IRegionPresenter;
import com.app.roadsafety.presenter.region.RegionPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectRegionActivity extends AppCompatActivity implements IRegionPresenter.IRegionView {

    @BindView(R.id.rlPortugal)
    CardView rlPortugal;
    @BindView(R.id.rlBrazilView)
    CardView rlBrazilView;
    IRegionPresenter iRegionPresenter;
    AppUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_select_region);
        ButterKnife.bind(this);
        iRegionPresenter=new RegionPresenterImpl(this,this);
        utils=new AppUtils();
        changeStatusBarColor();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @OnClick({R.id.rlPortugal, R.id.rlBrazilView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlPortugal:
                gotoGuidelines();
                setRegion(getString(R.string.portugal));
                break;
            case R.id.rlBrazilView:
                setRegion(getString(R.string.brazil));
                break;
        }
    }

    void setRegion(String region) {
        RegionUpdateRequest regionUpdateRequest=new RegionUpdateRequest();
        User user=new User();
        user.setRegion(region);
        regionUpdateRequest.setUser(user);
        String auth_token= SharedPreference.getInstance(this).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iRegionPresenter.setRegion(auth_token,regionUpdateRequest);
    }
    void gotoGuidelines() {
        Intent intent = new Intent(this, GuidlinesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessRegionResponse(LoginResponse response) {
        gotoGuidelines();
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        utils.showDialog(getString(R.string.please_wait), this);
    }

    @Override
    public void hideProgress() {
        utils.hideDialog();
    }
}

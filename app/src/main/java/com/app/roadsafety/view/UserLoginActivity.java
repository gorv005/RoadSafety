package com.app.roadsafety.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.presenter.AuthenticationPresenterImpl;
import com.app.roadsafety.presenter.IAuthenticationPresenter;
import com.app.roadsafety.utility.AppUtils;
import com.greenhalolabs.facebooklogin.FacebookLoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginActivity extends AppCompatActivity implements IAuthenticationPresenter.IAuthenticationView {

    @BindView(R.id.facebook_button)
    Button facebookButton;
    @BindView(R.id.tvGhuestLogin)
    TextView tvGhuestLogin;
    AppUtils util;
    IAuthenticationPresenter iAuthenticationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
        changeStatusBarColor();
        util = new AppUtils();
        iAuthenticationPresenter = new AuthenticationPresenterImpl(this, this);
        dialog();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FacebookLoginActivity.FACEBOOK_LOGIN_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                String accessToken = data.getStringExtra(FacebookLoginActivity.EXTRA_FACEBOOK_ACCESS_TOKEN);
                Log.e("DEBUG", accessToken);
                //Toast.makeText(this, "Access Token: " + accessToken, Toast.LENGTH_LONG).show();
                facebookLogin(accessToken);
            } else {
                String errorMessage = data.getStringExtra(FacebookLoginActivity.EXTRA_ERROR_MESSAGE);
                //Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e("DEBUG", errorMessage);
            }
            gotoSelectRegion();
        }
    }

    void facebookLogin(String token) {
        FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest();
        facebookLoginRequest.setAccessToken(token);
        iAuthenticationPresenter.facebookLogin(facebookLoginRequest);
    }

    void gotoSelectRegion() {
        Intent intent = new Intent(this, SelectRegionActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.facebook_button, R.id.tvGhuestLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.facebook_button:
                String applicationId = getResources().getString(R.string.facebook_app_id);
                ArrayList<String> permissions = new ArrayList<String>();
                permissions.add("public_profile");
                permissions.add("email");
                FacebookLoginActivity.launch(this, applicationId, permissions);

                break;
            case R.id.tvGhuestLogin:
                gotoSelectRegion();
                break;
        }
    }

    void dialog() {

        final Dialog dialog = new Dialog(this, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.alert_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//to set the message
        dialog.show();
    }

    @Override
    public void getFacebookLoginResponse(LoginResponse response) {

    }

    @Override
    public void getGuestUserResponse(LoginResponse response) {

    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void showProgress() {
        util.showDialog(getString(R.string.please_wait), this);
    }

    @Override
    public void hideProgress() {
        util.hideDialog();
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            iAuthenticationPresenter.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.app.roadsafety.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.app.roadsafety.R;
import com.greenhalolabs.facebooklogin.FacebookLoginActivity;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginActivity extends AppCompatActivity {

    @BindView(R.id.facebook_button)
    Button facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.facebook_button)
    public void onViewClicked() {
        String applicationId =  getResources().getString(R.string.facebook_app_id);
        ArrayList<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        FacebookLoginActivity.launch(this, applicationId, permissions);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FacebookLoginActivity.FACEBOOK_LOGIN_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                String accessToken = data.getStringExtra(FacebookLoginActivity.EXTRA_FACEBOOK_ACCESS_TOKEN);
                Toast.makeText(this, "Access Token: " + accessToken, Toast.LENGTH_LONG).show();
            }
            else {
                String errorMessage = data.getStringExtra(FacebookLoginActivity.EXTRA_ERROR_MESSAGE);
                Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }

        }
    }
}

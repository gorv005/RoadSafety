package com.app.roadsafety.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.terms.TermsResponse;
import com.app.roadsafety.presenter.terms.ITermsPresenter;
import com.app.roadsafety.presenter.terms.TermsPresenterImpl;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.sharedprefrences.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsActivity extends AppCompatActivity implements ITermsPresenter.ITermsView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.tvData)
    TextView tvData;
    int type;
    ITermsPresenter iTermsPresenter;
    AppUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <=25) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_terms);
        ButterKnife.bind(this);
        type= getIntent().getIntExtra(AppConstants.TYPE,1);
        iTermsPresenter=new TermsPresenterImpl(this,this);
        utils=new AppUtils();
        initToolbar();
        changeStatusBarColor();
        toolbar.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
        tvData.setMovementMethod(new ScrollingMovementMethod());


        if(type==1){
            toolbarTitle.setText(getString(R.string.terms));
            getTermsAndServices("terms_of_service");
        }
        else {
            toolbarTitle.setText(getString(R.string.privacy));
            getTermsAndServices("privacy_policy");

        }

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  toolbar.setTitleTextColor(Color.parseColor("#475261"));
        final Drawable upArrow =  ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }
    void getTermsAndServices(String name) {
        String auth_token = SharedPreference.getInstance(this).getUser(AppConstants.LOGIN_USER).getData().getAttributes().getAuthToken();
        iTermsPresenter.termsAndServices(auth_token, name);

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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
        utils.showDialog(getString(R.string.please_wait), this);
    }

    @Override
    public void hideProgress() {
        utils.hideDialog();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

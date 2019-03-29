package com.app.roadsafety;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.roadsafety.model.cityhall.CityHallData;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;
import com.app.roadsafety.presenter.createIncident.CreateIncidentPresenterImpl;
import com.app.roadsafety.presenter.createIncident.ICreateIncidentPresenter;
import com.app.roadsafety.utility.AppConstants;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.view.adapter.cityhall.CityHallAdapter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SearchCityHallActivity extends Activity implements ICreateIncidentPresenter.ICreateIncidentView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.llBack)
    LinearLayout llBack;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.lvCityHallList)
    ListView lvCityHallList;
    @BindView(R.id.container)
    LinearLayout container;
    ICreateIncidentPresenter iCreateIncidentPresenter;
    List<CityHallData> cityHallData;
    CityHallAdapter cityHallAdapter;
    AppUtils util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <=25) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_search_city_hall);

        ButterKnife.bind(this);
        util = new AppUtils();
        changeStatusBarColor();

        iCreateIncidentPresenter = new CreateIncidentPresenterImpl(this, this);
        getCityHall();
        etComment.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = etComment.getText().toString().toLowerCase(Locale.getDefault());
                cityHallAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        lvCityHallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(AppConstants.CityHallData, cityHallData.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    void getCityHall() {
        iCreateIncidentPresenter.getCityHall();

    }

    @Override
    public void onSuccessCreateIncidentResponse(CreateIncidentResponse response) {

    }

    @Override
    public void onSuccessCityHallResponse(CityHallResponse response) {
        try {
            if (response != null && response.getData() != null && response.getData().getData() != null && response.getData().getData().size() > 0) {
                cityHallData = response.getData().getData();
                cityHallAdapter = new CityHallAdapter(this, cityHallData);
                lvCityHallList.setAdapter(cityHallAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponseError(String response) {

    }

    @Override
    public void onSuccessUpdateIncidentResponse(CreateIncidentResponse response) {

    }

    @Override
    public void onSuccessReportAbuseIncidentResponse(ReportAbuseIncidentResponse response) {

    }

    @Override
    public void onSuccessDeleteIncidentResponse(ResponseBody response) {

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
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        onBackPressed();
    }
}

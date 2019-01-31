package com.app.roadsafety.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.roadsafety.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectRegionActivity extends AppCompatActivity {

    @BindView(R.id.rlPortugal)
    CardView rlPortugal;
    @BindView(R.id.rlBrazilView)
    CardView rlBrazilView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_select_region);
        ButterKnife.bind(this);
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
                break;
            case R.id.rlBrazilView:
                gotoGuidelines();
                break;
        }
    }


    void gotoGuidelines() {
        Intent intent = new Intent(this, GuidlinesActivity.class);
        startActivity(intent);
    }
}

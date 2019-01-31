package com.app.roadsafety.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.roadsafety.R;
import com.app.roadsafety.model.Guidelines;
import com.app.roadsafety.view.adapter.GuidelinesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuidlinesActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layoutDots)
    LinearLayout layoutDots;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    private ImageView[] dots;
    int size;
    GuidelinesAdapter guidelinesAdapter;
    List<Guidelines> guidelines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_guidlines);
        ButterKnife.bind(this);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        changeStatusBarColor();
        setGuideLines();
    }


    private void addBottomDots(int size, int currentPage) {
        dots = new ImageView[size];
        layoutDots.removeAllViews();
        for (int i = 0; i < size; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            layoutDots.addView(dots[i], params);

        }
        if (dots.length > 0)
            dots[currentPage].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_dot));
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(size, position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    void setGuideLines() {
        guidelines = new ArrayList<>();
        Guidelines g1 = new Guidelines(getString(R.string.watch_out_big_cars), "guideline_1", getString(R.string.title_activity_incident_maps), "#1EB8DD");
        guidelines.add(g1);
        Guidelines g2 = new Guidelines(getString(R.string.watch_out_big_cars), "guideline_2", getString(R.string.title_activity_incident_maps), "#9E89FF");
        guidelines.add(g2);
        Guidelines g3 = new Guidelines(getString(R.string.watch_out_big_cars), "guideline_1", getString(R.string.title_activity_incident_maps), "#FEDB61");
        guidelines.add(g3);
        guidelinesAdapter = new GuidelinesAdapter(getSupportFragmentManager(), guidelines);
        viewPager.setAdapter(guidelinesAdapter);
        size = guidelines.size();
        addBottomDots(size, 0);
    }

    @OnClick(R.id.btn_skip)
    public void onViewClicked() {
        gotoIncidentMaps();
    }
    void gotoIncidentMaps() {
        Intent intent = new Intent(this, IncidentMapsActivity.class);
        startActivity(intent);
    }
}

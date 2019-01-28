package com.app.roadsafety.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.Guidelines;
import com.app.roadsafety.view.adapter.GuidelinesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidlinesActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layoutDots)
    LinearLayout layoutDots;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    private TextView[] dots;
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
        dots = new TextView[size];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
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
        Guidelines g1 = new Guidelines(getString(R.string.watch_out_big_cars), "", getString(R.string.title_activity_incident_maps),"#1EB8DD");
        guidelines.add(g1);
        Guidelines g2 = new Guidelines(getString(R.string.watch_out_big_cars), "", getString(R.string.title_activity_incident_maps),"#9E89FF");
        guidelines.add(g2);
        Guidelines g3 = new Guidelines(getString(R.string.watch_out_big_cars), "", getString(R.string.title_activity_incident_maps),"#FEDB61");
        guidelines.add(g3);
        guidelinesAdapter = new GuidelinesAdapter(getSupportFragmentManager(),guidelines);
        viewPager.setAdapter(guidelinesAdapter);
        addBottomDots(guidelines.size(),0);
    }
}

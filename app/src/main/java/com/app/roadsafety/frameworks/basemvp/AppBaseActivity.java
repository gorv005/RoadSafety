package com.app.roadsafety.frameworks.basemvp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.roadsafety.R;

import butterknife.ButterKnife;

public abstract class AppBaseActivity<T extends IPresenter> extends AppCompatActivity implements IActivityView {
    public Toolbar mToolbar;
    private IPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewToCreate());
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    public abstract int getViewToCreate();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //Activity don't want to setContent View;
        if (layoutResID == -1) {
            return;
        }
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setmToolbar();
        if (mPresenter != null) {
            mPresenter.onViewCreated();
        }
    }


    public void setmToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onViewStarted();
        }
    }

    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    private ProgressDialog progressBar;
    @Override
    public void showProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setIndeterminate(true);
        progressBar.setMessage("Please Wait");
        progressBar.setCancelable(false);
        progressBar.show();
    }

    @Override
    public void hideProgressBar() {
        if(progressBar != null && progressBar.isShowing()) {
            progressBar.hide();
        }
    }

    public abstract T getPresenter();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getPresenter() != null) {
            getPresenter().onActivityResult(requestCode, resultCode, data);
        }
    }


}

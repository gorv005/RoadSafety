package com.app.roadsafety.application;

import android.app.Application;
import android.content.res.Configuration;

import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.FontsOverride;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/calibri.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/calibri.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/calibri.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/calibri.ttf");
        AppUtils.setLocale(getApplicationContext());

    }
}

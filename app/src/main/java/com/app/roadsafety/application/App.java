package com.app.roadsafety.application;

import android.app.Application;

import com.app.roadsafety.utility.FontsOverride;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "fonts/calibri.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/calibri.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/calibri.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/calibri.ttf");
    }
}

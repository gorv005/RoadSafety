package com.app.roadsafety.application;

import android.app.Application;
import android.content.res.Configuration;

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
       // setLocale();

    }
    private void setLocale(){
         Locale locale = new Locale("pt", "PT");
        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
    }
}

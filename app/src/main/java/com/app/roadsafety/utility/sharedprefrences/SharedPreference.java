package com.app.roadsafety.utility.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.roadsafety.model.authentication.LoginResponse;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AB on 08-09-2016.
 */
public class SharedPreference {

    private Context context;
    private static SharedPreference savePreferenceAndData;
    private static SharedPreference savePreferenceNoti;

    public static SharedPreference getInstance(Context context)
    {
        if(savePreferenceAndData==null)
        {
            savePreferenceAndData = new SharedPreference(context);
        }
        return savePreferenceAndData;
    }

    public static void SaveNoti(Context context, String key, boolean value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("Noti", MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getNoti(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences("Noti", MODE_PRIVATE);
       return prefs.getBoolean(key,false);

    }
    public SharedPreference(Context context)
    {
        this.context = context;

    }
    public void setString(String key, String data)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, data).apply();
    }
    public void setInt(String key, int data)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(key, data).apply();
    }
    public int getInt(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getInt(key, 1);
    }
    public void setBoolean(String key, boolean status)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(key, status).apply();
    }
    public boolean getBoolean(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getBoolean(key, false);
    }
    public String getString(String key)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return  prefs.getString(key, null);
    }

    public void clearData()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }


    public LoginResponse getUser(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String data = prefs.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(data, LoginResponse.class);

    }

    public void setUser(String key, LoginResponse adminUser) {
        Gson gson = new Gson();
        String json = gson.toJson(adminUser);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, json).apply();

    }


}

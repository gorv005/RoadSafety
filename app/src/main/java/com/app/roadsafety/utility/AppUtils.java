package com.app.roadsafety.utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.roadsafety.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppUtils {

    ProgressDialog progressDialog=null;

    public void showDialog(String msg, Context context){
        try {

            if (progressDialog == null || !progressDialog.isShowing()) {

                progressDialog = new ProgressDialog(context, R.style.customDialog);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

               /* progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/
              /*  progressDialog.setContentView(R.layout.progress_dialog);
               TextView text = (TextView) progressDialog.findViewById(R.id.tvMsg);
               text.setText(msg);*/
                progressDialog.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void hideDialog(){
        try {

            if (progressDialog!=null &&progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

   public void resultDialog(Context context,String msg) {

        final Dialog dialog = new Dialog(context, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.result_pop_up); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView textView=(TextView)dialog.findViewById(R.id.tvMsg);
        textView.setText(msg);
        Button btnOk=(Button)dialog.findViewById(R.id.btnOk);
        ImageView ivCross=(ImageView)dialog.findViewById(R.id.ivCross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//to set the message
        dialog.show();
    }

   public static String getDate(String utcDate){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
     //   SimpleDateFormat output = new SimpleDateFormat("MMM d, yyyy, h:mm a");
       SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = null;
        String formatted="";
        try
        {
            d = input.parse(utcDate);
             formatted = output.format(d);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return formatted;
    }

    private static Locale locale;

    public static void setLocale(Locale localeIn) {
        locale = localeIn;
        if(locale != null) {
            Locale.setDefault(locale);
        }
    }
    public static void setConfigChange(Context ctx){
        if(locale != null){
            Locale.setDefault(locale);

            Configuration configuration = ctx.getResources().getConfiguration();
            DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
            configuration.locale=locale;

            ctx.getResources().updateConfiguration(configuration, displayMetrics);
        }
    }
    public static boolean isInternetOn(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public Address getAddress(Context context,double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    public  static void setAddress(Address locationAddress, EditText editText) {
        String address = locationAddress.getAddressLine(0);
        String address1 = locationAddress.getAddressLine(1);
        String city = locationAddress.getLocality();
        String state = locationAddress.getAdminArea();
        String country = locationAddress.getCountryName();
        String postalCode = locationAddress.getPostalCode();

        String currentLocation;

        if (!TextUtils.isEmpty(address)) {
            currentLocation = address;

            if (!TextUtils.isEmpty(address1))
                currentLocation += " " + address1;

            if (!TextUtils.isEmpty(city)) {
                currentLocation += " " + city;

                if (!TextUtils.isEmpty(postalCode))
                    currentLocation += " - " + postalCode;
            } else {
                if (!TextUtils.isEmpty(postalCode))
                    currentLocation += " " + postalCode;
            }

            if (!TextUtils.isEmpty(state))
                currentLocation += " " + state;

            if (!TextUtils.isEmpty(country))
                currentLocation += " " + country;

            editText.setText(currentLocation);
        }
    }

    public  static void setAddress(Address locationAddress, TextView textView) {
        String address = locationAddress.getAddressLine(0);
        String address1 = locationAddress.getAddressLine(1);
        String city = locationAddress.getLocality();
        String state = locationAddress.getAdminArea();
        String country = locationAddress.getCountryName();
        String postalCode = locationAddress.getPostalCode();

        String currentLocation;

        if (!TextUtils.isEmpty(address)) {
            currentLocation = address;

            if (!TextUtils.isEmpty(address1))
                currentLocation += " " + address1;

            if (!TextUtils.isEmpty(city)) {
                currentLocation += " " + city;

                if (!TextUtils.isEmpty(postalCode))
                    currentLocation += " - " + postalCode;
            } else {
                if (!TextUtils.isEmpty(postalCode))
                    currentLocation += " " + postalCode;
            }

            if (!TextUtils.isEmpty(state))
                currentLocation += " " + state;

            if (!TextUtils.isEmpty(country))
                currentLocation += " " + country;

            textView.setText(currentLocation);
        }
    }

}

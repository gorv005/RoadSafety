package com.app.roadsafety.utility;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.roadsafety.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat output = new SimpleDateFormat("MMM d, yyyy, h:mm a");

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
}

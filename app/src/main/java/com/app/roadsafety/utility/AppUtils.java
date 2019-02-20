package com.app.roadsafety.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.app.roadsafety.R;

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
}

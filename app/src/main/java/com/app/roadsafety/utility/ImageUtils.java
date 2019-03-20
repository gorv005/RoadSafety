package com.app.roadsafety.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.app.roadsafety.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;


public class ImageUtils {
    public static int getImage(Activity activity, String imageName) {

        int drawableResourceId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());

        return drawableResourceId;
    }

    public static void loadImage(Activity context, String imageName, ImageView imageView) {
        Glide.with(context).load(getImage(context, imageName)).into(imageView);
    }
    public static void setImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                //.placeholder(R.drawable.missing)
                // .error(R.drawable.missing)
                .into(imageView);


    }
    public static void setImage(Context context, String url, int placeholder,ImageView imageView){
        Glide.with(context)
                .load(url).apply(new RequestOptions().placeholder(placeholder))
                // .error(R.drawable.missing)
                .into(imageView);


    }
    public static void loadFromInternalStorage(Context context, String url, ImageView imageView){
        File imgFile = new  File(url);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }


    }

}

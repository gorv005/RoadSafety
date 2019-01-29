package com.app.roadsafety.utility;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {
    public static int getImage(Activity activity, String imageName) {

        int drawableResourceId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());

        return drawableResourceId;
    }

    public static void loadImage(Activity context, String imageName, ImageView imageView) {
        Glide.with(context).load(getImage(context, imageName)).into(imageView);
    }
}

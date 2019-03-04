package com.app.roadsafety.view.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.roadsafety.R;
import com.app.roadsafety.utility.AppConstants;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidentImageViewPager extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    //Declaration of View Present in layout file
    ImageView iv_adds;
    ProgressBar pb_addsprogressBar;

    public IncidentImageViewPager() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPagerAddsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncidentImageViewPager newInstance(int param1, String param2) {
        IncidentImageViewPager fragment = new IncidentImageViewPager();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_incident_image_view_pager, container, false);



        iv_adds=(ImageView)view.findViewById(R.id.iv_adds);
        pb_addsprogressBar=(ProgressBar)view.findViewById(R.id.pb_addsprogressBar);
        if(mParam1== AppConstants.IS_FROM_INTERNAL_STORAGE){
            fromInternalStorage();
        }
        else {
            fromRemote();
        }
        //set the fetched image to imageview with round corner


        // iv_adds.setImageResource(mParam1);

        return view;
    }


    void fromInternalStorage(){
        File imgFile = new  File(mParam2);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv_adds.setImageBitmap(myBitmap);

        };
    }

    void fromRemote(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                // You can pass your own memory cache implementation
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(0))//rounded corner bitmap
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        imageLoader.setDefaultLoadingListener(new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                pb_addsprogressBar.setVisibility(View.VISIBLE);//when loading started progress bar will Visible
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason)
            {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                pb_addsprogressBar.setVisibility(View.GONE);//when loading Completed progress bar will Disappear
                pb_addsprogressBar.destroyDrawingCache();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });

        imageLoader.displayImage(mParam2,iv_adds,options );
    }

}

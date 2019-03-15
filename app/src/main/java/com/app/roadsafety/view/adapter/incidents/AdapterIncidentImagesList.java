package com.app.roadsafety.view.adapter.incidents;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.incidents.IncidentDataRes;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.ImageUtils;
import com.app.roadsafety.view.MainActivity;

import java.util.List;

public class AdapterIncidentImagesList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<String> horizontalList;
    Activity context;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;

        public ItemViewHolder(View view) {
            super(view);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAddImages;
        public FooterViewHolder(View view) {
            super(view);
            ivAddImages = (ImageView) view.findViewById(R.id.ivAddImages);

        }
    }

    public AdapterIncidentImagesList(List<String> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_item, parent, false);

            return new ItemViewHolder(itemView);
        }else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_image_item, parent, false);
            return new FooterViewHolder(itemView);
        }
            return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof ItemViewHolder) {
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                if (horizontalList.get(position)!= null && horizontalList.size() > 0) {

                    ImageUtils.loadFromInternalStorage(context, horizontalList.get(position), itemViewHolder.ivImage);
                }
            }
            else if (holder instanceof FooterViewHolder) {

                final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                if(position>2){
                    footerViewHolder.ivAddImages.setVisibility(View.GONE);
                }
                footerViewHolder.ivAddImages.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)context).addImage();

                    }
                });
            }


    }
    @Override
    public int getItemViewType(int position) {

        if (position==(horizontalList.size())) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return horizontalList.size()+1;
    }

}

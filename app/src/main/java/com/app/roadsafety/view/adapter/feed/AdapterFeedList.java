package com.app.roadsafety.view.adapter.feed;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.utility.ImageUtils;
import com.joooonho.SelectableRoundedImageView;


import java.util.List;

public class AdapterFeedList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<Feed> horizontalList;
    Activity context;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFeedTitle, tvFeedDesc;
        public ImageView ivIncident;
        RelativeLayout rlFeed;

        public ItemViewHolder(View view) {
            super(view);
            tvFeedTitle = (TextView) view.findViewById(R.id.tvFeedTitle);
            tvFeedDesc = (TextView) view.findViewById(R.id.tvFeedDesc);
            ivIncident = (ImageView) view.findViewById(R.id.ivIncident);
            rlFeed = (RelativeLayout) view.findViewById(R.id.rlFeed);
        }
    }

    public AdapterFeedList(List<Feed> horizontalList, Activity context) {
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
                    .inflate(R.layout.feed_item, parent, false);

            return new ItemViewHolder(itemView);
        } else
            return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvFeedTitle.setText(horizontalList.get(position).getHeading());
            itemViewHolder.tvFeedDesc.setText(horizontalList.get(position).getDesc());
            ImageUtils.loadImage(context, horizontalList.get(position).getImage(), itemViewHolder.ivIncident);

        }

    }
    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

}

package com.app.roadsafety.view.adapter.feed;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.feed.FeedListData;
import com.app.roadsafety.utility.AppUtils;
import com.app.roadsafety.utility.ImageUtils;
import com.app.roadsafety.view.MainActivity;

import java.util.List;

public class AdapterFeedList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<FeedListData> horizontalList;
    Activity context;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFeedTitle, tvFeedDesc,tvHours;
        public ImageView ivIncident;
        RelativeLayout rlFeed;

        public ItemViewHolder(View view) {
            super(view);
            tvFeedTitle = (TextView) view.findViewById(R.id.tvFeedTitle);
            tvFeedDesc = (TextView) view.findViewById(R.id.tvFeedDesc);
            ivIncident = (ImageView) view.findViewById(R.id.ivIncident);
            rlFeed = (RelativeLayout) view.findViewById(R.id.rlFeed);
            tvHours = (TextView) view.findViewById(R.id.tvHours);

        }
    }

    public AdapterFeedList(List<FeedListData> horizontalList, Activity context) {
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
            itemViewHolder.tvFeedTitle.setText(horizontalList.get(position).getAttributes().getTitle());
            itemViewHolder.tvFeedDesc.setText(horizontalList.get(position).getAttributes().getDescription());
            itemViewHolder.tvHours.setText(AppUtils.getDate(horizontalList.get(position).getAttributes().getPublishedAt()));

            ImageUtils.setImage(context, horizontalList.get(position).getAttributes().getImageUrl(), itemViewHolder.ivIncident);
            itemViewHolder.rlFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).gotoWebPage(horizontalList.get(position).getAttributes().getUrl());
                }
            });
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

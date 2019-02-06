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
import com.app.roadsafety.model.feed.Feed;
import com.app.roadsafety.utility.ImageUtils;

import java.util.List;

public class AdapterIncidentList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<Feed> horizontalList;
    Activity context;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView  tvIncidentDesc,tvHours;
        public ImageView ivIncident;
        RelativeLayout rlIncident;

        public ItemViewHolder(View view) {
            super(view);
            tvIncidentDesc = (TextView) view.findViewById(R.id.tvIncidentDesc);
            tvHours = (TextView) view.findViewById(R.id.tvHours);
            ivIncident = (ImageView) view.findViewById(R.id.ivIncident);
            rlIncident = (RelativeLayout) view.findViewById(R.id.rlIncident);
        }
    }

    public AdapterIncidentList(List<Feed> horizontalList, Activity context) {
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
                    .inflate(R.layout.incident_item, parent, false);

            return new ItemViewHolder(itemView);
        } else
            return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvIncidentDesc.setText(horizontalList.get(position).getDesc());
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

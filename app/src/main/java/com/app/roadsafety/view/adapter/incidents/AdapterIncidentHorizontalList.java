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

public class AdapterIncidentHorizontalList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<IncidentDataRes> horizontalList;
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
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        View rlIncident;
        public FooterViewHolder(View view) {
            super(view);
            rlIncident = (View) view.findViewById(R.id.rlIncident);

        }
    }

    public AdapterIncidentHorizontalList(List<IncidentDataRes> horizontalList, Activity context) {
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
                    .inflate(R.layout.incident_horizontal_item, parent, false);

            return new ItemViewHolder(itemView);
        }  else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more_incident, parent, false);
            return new FooterViewHolder(itemView);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvIncidentDesc.setText(horizontalList.get(position).getAttributes().getDescription());
            if(horizontalList.get(position).getAttributes().getImages()!=null && horizontalList.get(position).getAttributes().getImages().size()>0) {
                ImageUtils.setImage(context, horizontalList.get(position).getAttributes().getImages().get(0), itemViewHolder.ivIncident);
            }

            itemViewHolder.tvHours.setText(AppUtils.getDate(horizontalList.get(position).getAttributes().getCreatedAt()));
            itemViewHolder.rlIncident.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // ((MainActivity)context).gotoIncidentList();

                }
            });
        }
        else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.rlIncident.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).gotoIncidentList();

                }
            });
        }

    }
    @Override
    public int getItemViewType(int position) {

        if (position == horizontalList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        if(horizontalList.size()>=10) {
            return horizontalList.size()+1;
        }
        else {
            return horizontalList.size();
        }
    }

}

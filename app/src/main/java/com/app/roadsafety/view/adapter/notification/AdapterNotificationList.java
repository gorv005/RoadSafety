package com.app.roadsafety.view.adapter.notification;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.notification.NotificationData;
import com.app.roadsafety.utility.AppUtils;

import java.util.List;

public class AdapterNotificationList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private List<NotificationData> horizontalList;
    Activity context;
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView  tvNotificationDesc,tvDate;
        RelativeLayout rlNotifications;

        public ItemViewHolder(View view) {
            super(view);
            tvNotificationDesc = (TextView) view.findViewById(R.id.tvNotificationDesc);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            rlNotifications = (RelativeLayout) view.findViewById(R.id.rlNotifications);
        }
    }

    public AdapterNotificationList(List<NotificationData> horizontalList, Activity context) {
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
                    .inflate(R.layout.notification_item, parent, false);

            return new ItemViewHolder(itemView);
        } else
            return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tvNotificationDesc.setText(context.getString(R.string.notification_recieved));
            itemViewHolder.tvDate.setText(AppUtils.getDate(horizontalList.get(position).getAttributes().getCreatedAt()));
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

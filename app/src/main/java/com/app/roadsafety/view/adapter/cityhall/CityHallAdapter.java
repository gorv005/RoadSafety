package com.app.roadsafety.view.adapter.cityhall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.roadsafety.R;
import com.app.roadsafety.model.cityhall.CityHallData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CityHallAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<CityHallData> cityHallDataList = null;
    private ArrayList<CityHallData> arraylist;

    public CityHallAdapter(Context context, List<CityHallData> cityHallDataList) {
        mContext = context;
        this.cityHallDataList = cityHallDataList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<CityHallData>();
        this.arraylist.addAll(cityHallDataList);
    }

    public class ViewHolder {
        TextView city_hall_name;

    }

    @Override
    public int getCount() {
        return cityHallDataList.size();
    }

    @Override
    public CityHallData getItem(int position) {
        return cityHallDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.city_hall_item, null);
            // Locate the TextViews in listview_item.xml
            holder.city_hall_name = (TextView) view.findViewById(R.id.city_hall_name);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.city_hall_name.setText(cityHallDataList.get(position).getAttributes().getName());




        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        cityHallDataList.clear();
        if (charText.length() == 0) {
            cityHallDataList.addAll(arraylist);
        }
        else
        {
            for (CityHallData wp : arraylist)
            {
                if (wp.getAttributes().getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    cityHallDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

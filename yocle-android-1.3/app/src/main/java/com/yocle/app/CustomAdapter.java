package com.yocle.app;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.PorterDuff.Mode;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;

    CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    private class ViewHolder {
        ImageView icon;
        TextView title;
        TextView counter;
    }

    public void setListData(List<RowItem> data){
        rowItem = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        convertView = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.counter = (TextView) convertView.findViewById(R.id.counter);

            RowItem row_pos = rowItem.get(position);

            // setting the image resource and title
            holder.icon.setImageResource(row_pos.getIcon());
            holder.title.setText(row_pos.getTitle());
            if(row_pos.getCounter().equals("0"))
                holder.counter.setText("");
            else holder.counter.setText(row_pos.getCounter());
            convertView.setTag(holder);

            int iconColor = Color.GRAY;
            holder.icon.setColorFilter(iconColor, Mode.MULTIPLY);
        }
        return convertView;

    }


    @Override
    public int getCount() {
        return rowItem.size();
    }


    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }


    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }



}

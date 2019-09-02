package com.github.machadowma.commentsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<Comment> listData;
    private LayoutInflater layoutInflater;

    static class ViewHolder {
        TextView textViewComment,textViewNota;
        ImageView imageViewRating;
    }

    public CustomListAdapter(Context aContext, ArrayList<Comment> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.textViewComment = (TextView) convertView.findViewById(R.id.textViewComment);
            holder.imageViewRating = (ImageView) convertView.findViewById(R.id.imageViewRating);
            holder.textViewNota = (TextView) convertView.findViewById(R.id.textViewNota);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewComment.setText(listData.get(position).getComment());
        holder.textViewNota.setText("Nota: "+listData.get(position).getRating());
        switch (listData.get(position).getRating()){
            case 0:
                holder.imageViewRating.setImageResource(R.drawable.ruim);
                break;
            case 1:
                holder.imageViewRating.setImageResource(R.drawable.ruim);
                break;
            case 2:
                holder.imageViewRating.setImageResource(R.drawable.medio);
                break;
            case 3:
                holder.imageViewRating.setImageResource(R.drawable.medio);
                break;
            case 4:
                holder.imageViewRating.setImageResource(R.drawable.bom);
                break;
            case 5:
                holder.imageViewRating.setImageResource(R.drawable.bom);
                break;
            default:
                break;

        }

        return convertView;
    }

}

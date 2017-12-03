package com.example.android.simpleloginvolley;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hesham on 12/3/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Contact> mArrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Contact> arrayList) {
        mArrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textId.setText(String.valueOf(mArrayList.get(position).getUserId()));
        holder.textMeterId.setText(mArrayList.get(position).getMeterId());
        holder.textValue.setText(String.valueOf(mArrayList.get(position).getValue()));
        holder.textTimestamp.setText(mArrayList.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView textId;
        TextView textMeterId;
        TextView textValue;
        TextView textTimestamp;

        public MyViewHolder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.us_id);
            textMeterId = itemView.findViewById(R.id.us_meter_id);
            textValue = itemView.findViewById(R.id.us_value);
            textTimestamp = itemView.findViewById(R.id.us_timestamp);
        }
    }
}

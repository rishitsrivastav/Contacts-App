package com.developerrishit.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {
    Context context;
    ArrayList<CallModel> callList;
    private int lastPosition = -1;

    public CallAdapter(Context context, ArrayList<CallModel> arrayList) {
        this.context = context;
        this.callList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.call_log_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(callList.get(position).getName());
        if(callList.get(position).getName().equals("")){
            holder.name.setText(callList.get(position).getNumber());
        }
        holder.calltype.setText(callList.get(position).getCalltype());
        holder.duration.setText(callList.get(position).getCallduration());
        holder.date.setText(callList.get(position).getTime());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = callList.get(position).getNumber();
                Intent in = new Intent(Intent.ACTION_CALL);
                in.setData(Uri.parse("tel:"+number));
                context.startActivity(in);
            }
        });
        setAnimation(holder.itemView,position);

    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,calltype,duration,date;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            calltype=itemView.findViewById(R.id.calltype);
            duration=itemView.findViewById(R.id.duration);
            date=itemView.findViewById(R.id.date);
            imageView=itemView.findViewById(R.id.call_karo);
        }
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animation.setDuration(150);
        viewToAnimate.startAnimation(animation);
        lastPosition = position;

    }
}

package com.developerrishit.contactsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Model> modelArrayList;
    private int lastPosition = -1;

    public Adapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(modelArrayList.get(position).getName());
        holder.number.setText(modelArrayList.get(position).getNumber());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = modelArrayList.get(position).getNumber();
                Intent in = new Intent(Intent.ACTION_CALL);
                in.setData(Uri.parse("tel:"+number));
                context.startActivity(in);
            }
        });
        setAnimation(holder.itemView,position);
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText message = new EditText(v.getContext());
                message.setHint("Type Your Message...");
                final AlertDialog.Builder sending_sms= new AlertDialog.Builder(v.getContext());
                sending_sms.setIcon(R.drawable.message)
                        .setTitle("SEND TO : "+modelArrayList.get(position).getName())
                        .setView(message)
                        .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendSMS(modelArrayList.get(position).getNumber(),message.getText().toString());
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                sending_sms.show();
            }
        });

    }
    public void filterlist(List<Model> filterlist){
        modelArrayList = (ArrayList<Model>) filterlist;
        notifyDataSetChanged();
    }
    private void sendSMS(String phnumber,String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phnumber, null, message, null, null);
            Toast.makeText(context, "Message sent to : " + phnumber, Toast.LENGTH_SHORT).show();
        }
        catch (Exception exception){
            Toast.makeText(context, "Something went Wrong..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,number;
        ImageView call;
        ImageView sms;
        FloatingActionButton DialPad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.calltype);
            call = itemView.findViewById(R.id.call_karo);
            sms = itemView.findViewById(R.id.id_sms);
            DialPad =itemView.findViewById(R.id.DialPad);
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

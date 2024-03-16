package com.developerrishit.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class recentCallFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<CallModel> callModelArrayList;
    CallAdapter callAdapter;


 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_recent_call, null);
        recyclerView=v.findViewById(R.id.recycler_recent);
        callModelArrayList = new ArrayList<>();
        callAdapter= new CallAdapter(getContext(),callModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        getCallDetails(getContext(),callModelArrayList,callAdapter);

        return v;
    }
    private static void getCallDetails(Context context,ArrayList<CallModel>arrayList,CallAdapter callAdapter) {
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            String phname = cursor.getString(name);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String time = format.format(callDayTime);
            String callDuration = cursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";

                    break;
            }

           int min = Integer.parseInt(callDuration)/60;
            int sec= Integer.parseInt(callDuration)-min*60;
            arrayList.add(new CallModel(phname,phNumber,dir,String.valueOf(min)+":"+String.valueOf(sec),time));
        }
        callAdapter.notifyDataSetChanged();
        cursor.close();

    }
}
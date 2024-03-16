package com.developerrishit.contactsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class ContactFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Model> arrayList;
    Adapter adapter;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, null);
        recyclerView = view.findViewById(R.id.recyclerContacts);
        editText = view.findViewById(R.id.editTextPersonName);
        arrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        readContacts();

       /* ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView)*/;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;
    }

    void readContacts() {
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            arrayList.add(new Model(name, phoneNumber));

        }
        adapter.notifyDataSetChanged();
        phones.close();

    }

    private void filter(String tostring) {
        List<Model> filterlist = new ArrayList<>();

        for (Model item : arrayList) {
            if (item.getName().toLowerCase().contains(tostring.toLowerCase())) {
                filterlist.add(item);
            }
        }
        adapter.filterlist(filterlist);
    }
}
    /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // Toast.makeText(ListActivity.this, "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
       *//* public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Toast.makeText(ListActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            adapter.call(position);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.green))
                    .addActionIcon(R.drawable.baseline_call_24)
                    .create()
                    .decorate();
        }
    };*/


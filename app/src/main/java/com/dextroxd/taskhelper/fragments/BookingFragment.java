package com.dextroxd.taskhelper.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.activity.BookNow;
import com.dextroxd.taskhelper.activity.User;
import com.dextroxd.taskhelper.adapter.ListAdapter;
import com.dextroxd.taskhelper.login.LoginActivity;
import com.dextroxd.taskhelper.model.BookingModel;
import com.dextroxd.taskhelper.model.ServiceClassModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference database_Users;
    private String uid;
    ProgressBar progressBar;
    List<ServiceClassModel>serviceClassModels;
    ListAdapter listAdapter;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private Button btn;
    private ArrayList<String>name;
    private ArrayList<String>phone;
    private ArrayList<String>address;
    private ArrayList<String>service;
    private ArrayList<Integer>num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking,container,false);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
         uid = auth.getCurrentUser().getUid();
        else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
       recyclerView = view.findViewById(R.id.recycler);
         progressBar = view.findViewById(R.id.progressBar);
        database_Users = FirebaseDatabase.getInstance().getReference("users");

        serviceClassModels=new ArrayList<>();
        name=new ArrayList<>();
        phone=new ArrayList<>();
        address=new ArrayList<>();
        service=new ArrayList<>();
        num = new ArrayList<>();
        linearLayout = view.findViewById(R.id.textviewgone);
        btn = view.findViewById(R.id.explorenow);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        listAdapter = new ListAdapter(serviceClassModels,getActivity());
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter.notifyDataSetChanged();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        auth.getCurrentUser().reload();
        database_Users.child(uid).child("service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                serviceClassModels.clear();
                name.clear();
                address.clear();
                phone.clear();
                service.clear();
                num.clear();
                for(DataSnapshot snap:dataSnapshot.getChildren())
                    {
                        for(DataSnapshot real:snap.getChildren())
                        {
                            if(real.getKey().equals("name"))
                            {
                                name.add(real.getValue(String.class));
                            }
                            if(real.getKey().equals("address"))
                            {
                                address.add(real.getValue(String.class));
                            }
                            if(real.getKey().equals("phoneno"))
                            {
                                phone.add(real.getValue(String.class));
                            }
                            if(real.getKey().equals("servicetype"))
                            {
                                service.add(real.getValue(String.class));
                            }
                            if(real.getKey().equals("i"))
                            {
                                num.add(Integer.parseInt(String.valueOf(real.getValue())));
                            }
                        }
                    }

                    populateListView();
                progressBar.setVisibility(View.INVISIBLE);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void populateListView(){

        if(name.size()==0)
        {
            listAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            btn.setEnabled(true);

        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            btn.setEnabled(false);
        for(int i=0;i<name.size();i++)
        {
            serviceClassModels.add(new ServiceClassModel(name.get(i),address.get(i),phone.get(i),service.get(i),num.get(i)));
            listAdapter.notifyDataSetChanged();

        }}

    }

}

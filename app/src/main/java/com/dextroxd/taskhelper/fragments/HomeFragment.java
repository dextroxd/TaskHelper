package com.dextroxd.taskhelper.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.adapter.GridAdapter;
import com.dextroxd.taskhelper.model.ServicesModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private boolean isOriginal=true;
    private  GridAdapter gridAdapter;
    private ArrayList<ServicesModel> servicesModels = new ArrayList<>();
    private GridView gridView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ImageButton imageButton = view.findViewById(R.id.listg);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOriginal = !isOriginal;
                imageButton.setImageResource(isOriginal ? R.drawable.list18 : R.drawable.grid18);
                int a = gridView.getNumColumns();
                if (a == 1) {
                    gridView.setNumColumns(2);
                } else {
                    gridView.setNumColumns(1);
                }
            }
        }
        );

        gridAdapter = new GridAdapter(getContext(), servicesModels);
        getData();
        gridView = view.findViewById(R.id.grid_view);
        gridView.setAdapter(gridAdapter);
        return view;
    }
    public void getData()
    {
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> desc = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();


        title.add("Washing Machine");
        title.add("Refrigerator");
        title.add("Air Conditioning");
        title.add("Oven");
        title.add("Home Cleaning");
        title.add("Computer and Laptop");

        desc.add("Repair and Maintenance");
        desc.add("Repair and Maintenance");
        desc.add("Repair and Maintenance");
        desc.add("Repair and Maintenance");
        desc.add("Cleaning and Maintenance");
        desc.add("Annual Maintenance");

        img.add("https://res.cloudinary.com/dcw101uvb/image/upload/v1544274636/ifb-washing-machine-repair-in-bangalore.jpg");
        img.add("https://res.cloudinary.com/dcw101uvb/image/upload/v1544274920/refrigerator-maintenance-appliance-repair-atlanta-itisfixed.jpg");
        img.add("https://res.cloudinary.com/dcw101uvb/image/upload/v1544275366/page-ac-maintenance.jpg");
        img.add("https://res.cloudinary.com/dcw101uvb/image/upload/v1544275494/ovenRepair-600x300.jpg");
        img.add("https://res.cloudinary.com/dcw101uvb/image/upload/v1544275597/720A1250.jpg.jpg");
        img.add("https://res.cloudinary.com/dcw101uvb/image/upload/v1544275850/laptop-repair-01.jpg");

        for(int i=0;i<title.size();i++)
        {
            servicesModels.add(new ServicesModel(title.get(i),desc.get(i),img.get(i)));
        }
        gridAdapter.notifyDataSetChanged();
    }
}

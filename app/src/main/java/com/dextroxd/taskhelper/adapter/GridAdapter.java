package com.dextroxd.taskhelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.activity.ServiceActivity;
import com.dextroxd.taskhelper.model.ServicesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<ServicesModel> {

    public GridAdapter(@NonNull Context context, @NonNull ArrayList<ServicesModel> objects) {
        super(context,0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View GridItemView = convertView;
        if (GridItemView == null) {
            GridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_layout, parent, false);
        }
        final ServicesModel servicesModel = getItem(position);
        ImageView imageView = GridItemView.findViewById(R.id.imageincard);
        Picasso.get().load(servicesModel.getmImg()).into(imageView);
        TextView textView = GridItemView.findViewById(R.id.texttitle);
        textView.setText(servicesModel.getmTitle());
        TextView textView1 = GridItemView.findViewById(R.id.textdesc);
        textView1.setText(servicesModel.getmDesc());
        GridItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = servicesModel.getmImg();
                String title = servicesModel.getmTitle();
                Intent in = new Intent(getContext(),ServiceActivity.class);
                in.putExtra("image",image);
                in.putExtra("title",title);
                getContext().startActivity(in);
                }
        });

        return GridItemView;

    }
}

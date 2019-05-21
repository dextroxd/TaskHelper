package com.dextroxd.taskhelper.adapter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.activity.CancellingActivity;
import com.dextroxd.taskhelper.automatedEmail.GMailSender;
import com.dextroxd.taskhelper.model.BookingModel;
import com.dextroxd.taskhelper.model.ServiceClassModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    List<ServiceClassModel>serviceClassModels;
    Activity activity;
    private FirebaseAuth auth;
    private DatabaseReference database_Users,database_Users1;
    String uid,name,phone,title1,imageurl,info;

    public ListAdapter(List<ServiceClassModel> serviceClassModels1, Activity activity) {
        this.serviceClassModels=new ArrayList<>();
        this.serviceClassModels = serviceClassModels1;
        this.activity = activity;
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        database_Users = FirebaseDatabase.getInstance().getReference("users").child(uid);
//        database_Users1 = FirebaseDatabase.getInstance().getReference("users");
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,cancelbutton,name,phone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_Booking);
            title = itemView.findViewById(R.id.title_booking);
            cancelbutton=itemView.findViewById(R.id.cancelbutton);
            name=itemView.findViewById(R.id.nameofperson);
            phone=itemView.findViewById(R.id.phoneofperson);

        }
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.list_view, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapter.MyViewHolder myViewHolder, final int i)
    {
        final ServiceClassModel serviceClassModel = serviceClassModels.get(i);
        myViewHolder.title.setText(serviceClassModel.getServicetype());
        myViewHolder.name.setText(serviceClassModel.getName());
        myViewHolder.phone.setText(serviceClassModel.getPhoneno());
        switch (serviceClassModel.getServicetype())
        {
                        case "Washing Machine":
                            imageurl="https://res.cloudinary.com/dcw101uvb/image/upload/v1544274636/ifb-washing-machine-repair-in-bangalore.jpg";
                            break;
                        case "Refrigerator":
                            imageurl="https://res.cloudinary.com/dcw101uvb/image/upload/v1544274920/refrigerator-maintenance-appliance-repair-atlanta-itisfixed.jpg";
                            break;
                        case "Air Conditioning":
                            imageurl="https://res.cloudinary.com/dcw101uvb/image/upload/v1544275366/page-ac-maintenance.jpg";
                            break;
                        case "Oven":
                            imageurl="https://res.cloudinary.com/dcw101uvb/image/upload/v1544275494/ovenRepair-600x300.jpg";
                            break;
                        case "Home Cleaning":
                            imageurl="https://res.cloudinary.com/dcw101uvb/image/upload/v1544275597/720A1250.jpg.jpg";
                            break;
                        case "Computer and Laptop":
                            imageurl="https://res.cloudinary.com/dcw101uvb/image/upload/v1544275850/laptop-repair-01.jpg";
                            break;

        }
        Picasso.get().load(imageurl).into(myViewHolder.imageView);
        myViewHolder.cancelbutton.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
                                                             serviceClassModels.remove(serviceClassModels.get(i));
                                                             notifyItemRemoved(i);
                                                             title1= serviceClassModel.getServicetype();
                                                             info="Hey my name is "+serviceClassModel.getName()+".\nMy address is "+serviceClassModel.getAddress()+".\nMy phone no. is"+serviceClassModel.getPhoneno();
                                                             sendMail(info,title1);
                                                             database_Users.child("service").child(String.valueOf(serviceClassModel.getI())).removeValue();

                                                         }
                                                     });

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        int i=0;
        try {
            i= serviceClassModels.size();
            }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return i;
    }
    public void sendMail(final String info, final String title) {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            public void run() {

                try {

                    GMailSender sender = new GMailSender(

                            "taskhelper2019@gmail.com",

                            "taskhelper12345@2019");


                    sender.sendMail("Cancelling of "+title+" service", info,

                            "taskhelper2019@gmail.com",

                            "thetaskhelper@gmail.com");


                } catch (Exception e) {

                    Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_LONG).show();


                }

            }

        }).start();

    }
}



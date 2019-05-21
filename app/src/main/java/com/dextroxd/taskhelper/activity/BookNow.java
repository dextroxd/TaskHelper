package com.dextroxd.taskhelper.activity;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.activity.MainActivity;
import com.dextroxd.taskhelper.activity.User;
import com.dextroxd.taskhelper.automatedEmail.GMailSender;
import com.dextroxd.taskhelper.fragments.BookingFragment;
import com.dextroxd.taskhelper.fragments.HomeFragment;
import com.dextroxd.taskhelper.model.ServiceClassModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookNow extends AppCompatActivity {
    private EditText addressline1, addressline2,name1,phone1;
    private Spinner spinner;
    private CardView cardView;
    private FirebaseAuth auth;
    private DatabaseReference database_Users, database_Users1;
    private ProgressBar progressBar;
    String name, prevNumb, phone, uid, city, title, first_address, second_address,ac;
    String information;
    private int previousnumber;
    private ServiceClassModel serviceClassModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent in = getIntent();
        title = in.getStringExtra("keys");
        ac = in.getStringExtra("ac");
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        database_Users = FirebaseDatabase.getInstance().getReference("users").child(uid);
        database_Users1 = FirebaseDatabase.getInstance().getReference("users");
        setContentView(R.layout.activity_book_now);
        progressBar = findViewById(R.id.progressBarbook);
        addressline1 = findViewById(R.id.address1);
        addressline2 = findViewById(R.id.address2);
        name1 = findViewById(R.id.name);
        phone1 = findViewById(R.id.phone);
        spinner = findViewById(R.id.spinnerforcities);
        cardView = findViewById(R.id.submitButton);
        getValue();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first_address = addressline1.getText().toString();
                second_address = addressline2.getText().toString();
                name=name1.getText().toString();
                phone=phone1.getText().toString();
                if (TextUtils.isEmpty(first_address)) {
                    Toast.makeText(BookNow.this, "Please Enter the Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(BookNow.this, "Please Enter the Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(BookNow.this, "Please Enter the phone", Toast.LENGTH_SHORT).show();
                    return;
                }

                city = first_address + " " + second_address + " " + spinner.getSelectedItem().toString();
                database_Users.child("address_USer").setValue(city);
                previousnumber++;
                prevNumb=String.valueOf(previousnumber);
                database_Users.child("services").setValue(previousnumber);
                information = "Hey my name is "+name+".\nMy address is "+city+".\nMy contact number is "+phone+"extra"+ac;
                sendMail(information,title);
                serviceClassModel = new ServiceClassModel(name,city,phone,title,previousnumber);
                database_Users.child("service").child(prevNumb).setValue(serviceClassModel);
                Toast.makeText(getApplicationContext(), "You have booked " + title + " service and we will call you back shortly", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookNow.this,MainActivity.class));
                finish();



            }
        });


    }

    public void getValue() {
        cardView.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        database_Users1.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (userSnapshot.getKey().equals("name_User")) {
                        name = userSnapshot.getValue(String.class);
                    }
                    if (userSnapshot.getKey().equals("phone_User")) {
                        phone = userSnapshot.getValue(String.class);
                    }
                    if(userSnapshot.getKey().equals("services"))
                    {
                        previousnumber=Integer.parseInt(userSnapshot.getValue().toString());
                    }

                }

                name1.setText(name);
                phone1.setText(phone);
                progressBar.setVisibility(View.INVISIBLE);
                cardView.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendMail(final String info, final String title) {
        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            public void run() {

                try {

                    GMailSender sender = new GMailSender(

                            "taskhelper2019@gmail.com",

                            "taskhelper12345@2019");


                    sender.sendMail("Ordering of "+title+" service", info,

                            "taskhelper2019@gmail.com",

                            "thetaskhelper@gmail.com");


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();


                }

            }

        }).start();

    }

}




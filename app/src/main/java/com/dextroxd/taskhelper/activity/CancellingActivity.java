package com.dextroxd.taskhelper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.automatedEmail.GMailSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CancellingActivity extends AppCompatActivity {
    private EditText addressline1, addressline2,name1,phone1;
    private Spinner spinner;
    private CardView cardView;
    private FirebaseAuth auth;
    private DatabaseReference database_Users, database_Users1;
    String name, address, email, phone, uid, city, title, first_address, second_address;
    String information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelling);
        final Intent in = getIntent();
        title = in.getStringExtra("keys");
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        database_Users = FirebaseDatabase.getInstance().getReference("users").child(uid);
        database_Users1 = FirebaseDatabase.getInstance().getReference("users");
        setContentView(R.layout.activity_book_now);
        addressline1 = findViewById(R.id.address1);
        addressline2 = findViewById(R.id.address2);
        name1 = findViewById(R.id.name);
        phone1 = findViewById(R.id.phone);
        spinner = findViewById(R.id.spinnerforcities);
        cardView = findViewById(R.id.submitButton);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_address = addressline1.getText().toString();
                second_address = addressline2.getText().toString();
                name=name1.getText().toString();
                phone=phone1.getText().toString();
                if (TextUtils.isEmpty(first_address)) {
                    Toast.makeText(CancellingActivity.this, "Please Enter the Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText( CancellingActivity.this, "Please Enter the Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(CancellingActivity.this, "Please Enter the phone", Toast.LENGTH_SHORT).show();
                    return;
                }


                city = first_address + " " + second_address + " " + spinner.getSelectedItem().toString();
                database_Users.child("address_USer").setValue(city);
                information = "Hey my name is "+name+".\nMy address is "+city+".\nMy contact number is "+phone;
                switch(title)
                {
                    case "Washing Machine":
                        database_Users.child("washing_Machine").setValue(false);
                        Toast.makeText(CancellingActivity.this,"You have canceled booked "+title+" service",Toast.LENGTH_SHORT).show();
                        sendMail(information,title);
                        startActivity(new Intent(CancellingActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "Refrigerator":
                        database_Users.child("refrigerator").setValue(false);
                        Toast.makeText(CancellingActivity.this,"You have canceled booked "+title+" service",Toast.LENGTH_SHORT).show();
                        sendMail(information,title);
                        startActivity(new Intent(CancellingActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "AirConditioning":
                        database_Users.child("air_Conditioning").setValue(false);
                        Toast.makeText(CancellingActivity.this,"You have canceled booked "+title+" service",Toast.LENGTH_SHORT).show();
                        sendMail(information,title);
                        startActivity(new Intent(CancellingActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "Oven":
                        database_Users.child("oven_Micro").setValue(false);
                        Toast.makeText(CancellingActivity.this,"You have canceled booked "+title+" service",Toast.LENGTH_SHORT).show();
                        sendMail(information,title);
                        startActivity(new Intent(CancellingActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "House Sanitation":
                        database_Users.child("house_Sanitiation").setValue(false);
                        Toast.makeText(CancellingActivity.this,"You have canceled booked "+title+" service",Toast.LENGTH_SHORT).show();
                        sendMail(information,title);
                        startActivity(new Intent(CancellingActivity.this,MainActivity.class));
                        finish();
                        break;
                    case "Computer and Laptop":
                        database_Users.child("computer_Laptop").setValue(true);
                        Toast.makeText(CancellingActivity.this,"You have canceled booked "+title+" service",Toast.LENGTH_SHORT).show();
                        sendMail(information,title);
                        startActivity(new Intent(CancellingActivity.this,MainActivity.class));
                        finish();
                        break;
                }

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


                    sender.sendMail("Cancelling of "+title+" service", info,

                            "taskhelper2019@gmail.com",

                            "divyanshu171299@gmail.com");


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();


                }

            }

        }).start();

    }
}

package com.dextroxd.taskhelper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ServiceActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private String repairmen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        auth=FirebaseAuth.getInstance();
        Intent in = getIntent();
        String image = in.getStringExtra("image");
        final String title = in.getStringExtra("title");
        repairmen = " repairmen";
        if(title.equals("Home Cleaning"))
        {
            repairmen = " labour";
        }
        String title1 = "Skilled and verified "+repairmen ;
        String title2 = "Transparent and reasonable pricing";
        String title3 ="Complete customer satisfaction";
        String desc1 = "Every "+title+repairmen+" is skill checked, background verified and continuously evaluated.";
        String desc2 = "Reasonable hourly rates for all your service needs.";
        String desc3 = "30 days service warranty.";
        CardView cardView = findViewById(R.id.Booknow);
        CardView cardView1=findViewById(R.id.split);
        CardView cardView2=findViewById(R.id.window);
        LinearLayout linearLayout = findViewById(R.id.ac);
        ImageView imageView = findViewById(R.id.serviceimage);
        Picasso.get().load(image).into(imageView);
        TextView textView = findViewById(R.id.servicetext);
        textView.setText(title);
        TextView textView_title1=findViewById(R.id.texttitlese1);
        textView_title1.setText(title1);
        TextView textView_desc1=findViewById(R.id.textdescriptionse1);
        textView_desc1.setText(desc1);
        TextView textView_title2=findViewById(R.id.texttitlese2);
        textView_title2.setText(title2);
        TextView textView_desc2=findViewById(R.id.textdescriptionse2);
        textView_desc2.setText(desc2);
        TextView textView_title3=findViewById(R.id.texttitlese3);
        textView_title3.setText(title3);
        TextView textView_desc3=findViewById(R.id.textdescriptionse3);
        textView_desc3.setText(desc3);
        TextView basecost = findViewById(R.id.text_for_basecharge);
        if(title.equals("Home Cleaning"))
        {
            basecost.setVisibility(View.GONE);
        }
        if(title.equals("Air Conditioning"))
        {
            cardView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent split = new Intent(ServiceActivity.this, Airconditioning.class);
               split.putExtra("key","Split Ac");
               startActivity(split);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent window = new Intent(ServiceActivity.this, Airconditioning.class);
                window.putExtra("key","Window Ac");
                startActivity(window);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auth.getCurrentUser()!=null)
                {
                    Log.e(title,title);
                    Intent in = new Intent(ServiceActivity.this, BookNow.class);
                    in.putExtra("keys",title);
                    startActivity(in);

                }
                else
                {
                    startActivity(new Intent(ServiceActivity.this, LoginActivity.class));
                    Toast.makeText(ServiceActivity.this,"You must login first",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}

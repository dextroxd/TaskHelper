package com.dextroxd.taskhelper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;

public class Airconditioning extends AppCompatActivity {
    boolean a=false,b=false,c=false,d=false,e=false;
    String mail,editbox,type;
    CheckBox ac1,ac2,ac3,ac4,ac5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airconditioning);
        Intent in = getIntent();
        type = in.getStringExtra("key");
        ac1 = findViewById(R.id.ac1);
        ac2 = findViewById(R.id.ac2);
        ac3 = findViewById(R.id.ac3);
        ac4 = findViewById(R.id.ac4);
        ac5 = findViewById(R.id.ac5);
        final EditText others = findViewById(R.id.otheredit);
        CardView cardView = findViewById(R.id.submitButton);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a=ac1.isChecked();
                b=ac2.isChecked();
                c=ac3.isChecked();
                d=ac4.isChecked();
                e=ac5.isChecked();
                editbox = others.getText().toString().trim();
                if(!a&&!b&&!c&&!d&&!e&&(TextUtils.isEmpty(others.getText().toString())))
                {
                    Toast.makeText(Airconditioning.this,"You haven't selected anything!",Toast.LENGTH_SHORT).show();
                    return;
                }
                mail = "\n"+"Ac type:"+type+"\n"+getString(R.string.ac1)+":"+a+"\n"+getString(R.string.ac2)+":"+b+"\n"+getString(R.string.ac3)+":"+c+"\n"+getString(R.string.ac4)+":"+d+"\n"+getString(R.string.ac5)+":"+e+"\n";
                mail = mail+"\n"+"others"+editbox;
                Intent in = new Intent(Airconditioning.this, BookNow.class);
                in.putExtra("keys","Air Conditioning");
                in.putExtra("ac",mail);
                startActivity(in);
            }
        });

    }
}

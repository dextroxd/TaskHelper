package com.dextroxd.taskhelper.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.login.LoginActivity;
import com.dextroxd.taskhelper.login.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference,database_Users;
    private String uid,name,phone,emailid;
    private EditText editText1,editText2,editText3;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
        { uid = auth.getCurrentUser().getUid();}
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
        database_Users = FirebaseDatabase.getInstance().getReference("users");
        progressBar = findViewById(R.id.progressBar);
        editText1=findViewById(R.id.edittext1);
        editText2=findViewById(R.id.edittext2);
//        editText3=findViewById(R.id.edittext3);
        textView = findViewById(R.id.resetpassword);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ResetPasswordActivity.class));
            }
        });
        getValue();
        Button imageButton = findViewById(R.id.onsubmit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }
    public void  getValue()
    {
        progressBar.setVisibility(View.VISIBLE);
        database_Users.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (userSnapshot.getKey().equals("name_User")) {
                        name= userSnapshot.getValue().toString();

                    }
                    if (userSnapshot.getKey().equals("phone_User")) {
                        phone= userSnapshot.getValue().toString();

                    }
                    if (userSnapshot.getKey().equals("email_User")) {
                        emailid= userSnapshot.getValue().toString();

                    }

                }
                editText1.setText(name);
                editText2.setText(phone);
//                editText3.setText(emailid);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void updateData()
    {
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child("name_User").setValue(editText1.getText().toString());
        databaseReference.child("phone_User").setValue(editText2.getText().toString());
//        databaseReference.child("email_User").setValue(editText3.getText().toString()).addOnCompleteListener(EditProfileActivity.this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                Toast.makeText(EditProfileActivity.this,"Successfully updated the values",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(EditProfileActivity.this,"Unable to update the values",Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//        FirebaseAuth.getInstance().getCurrentUser().updateEmail(editText3.getText().toString());
    }
}

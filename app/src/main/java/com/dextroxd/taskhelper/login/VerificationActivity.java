package com.dextroxd.taskhelper.login;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.activity.MainActivity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener mAuthListener;
    TextView textView;
    String phone;
    String codeSent;
    String verificationId;
    private PhoneAuthProvider.ForceResendingToken mToken;
    EditText editText;
    boolean verify,phonecodeverify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null)
        {
            user=auth.getCurrentUser();
        }
        if(user.isEmailVerified())
        {
            Toast.makeText(VerificationActivity.this,"You have been verified",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(VerificationActivity.this, MainActivity.class));
            finish();
        }
        Button button1=findViewById(R.id.getmein);
        progressBar=findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);
        verificationEmail(user);
        progressBar.setVisibility(View.INVISIBLE);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.reload();
                verify=user.isEmailVerified();
                progressBar.setVisibility(View.VISIBLE);
                if(verify)
                {   progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(VerificationActivity.this,MainActivity.class));
                    finish();

                }
                if(!verify)
                {   progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(VerificationActivity.this,"Your email isn't verified!",Toast.LENGTH_SHORT).show();

                }




            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().getCurrentUser().reload();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().getCurrentUser().reload();

    }

    private void verificationEmail(FirebaseUser user)
    {

        if(user!=null)
        {
            user.sendEmailVerification().addOnCompleteListener(VerificationActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(VerificationActivity.this,"Verification mail is sent",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(VerificationActivity.this,"Failed to Verify!Check your connection",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        user.delete();
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).removeValue();
        startActivity(new Intent(VerificationActivity.this,SignupActivity.class));
        finish();
    }
}

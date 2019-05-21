package com.dextroxd.taskhelper.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.login.SignupActivity;
import com.dextroxd.taskhelper.login.VerificationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    private PrefManager prefManager;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
//        prefManager = new PrefManager(this);
//        if (prefManager.isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() != null)
                {
                    if(auth.getCurrentUser().isEmailVerified())
                    {Intent in = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(in);}
                    else
                    {
                        Intent in = new Intent(SplashScreen.this,VerificationActivity.class);
                        startActivity(in);
                    }
                }
                else
                { Intent in = new Intent(SplashScreen.this,SignupActivity.class);
                    startActivity(in);}

                finish();
            }
        },1000);
        ImageView iv =  findViewById(R.id.imagesplash);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(iv,"translationY",100,0);
        objectAnimator1.setDuration(600);
        objectAnimator1.start();

    }


}
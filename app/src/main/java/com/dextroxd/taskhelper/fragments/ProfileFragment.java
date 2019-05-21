package com.dextroxd.taskhelper.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dextroxd.taskhelper.ContactUs;
import com.dextroxd.taskhelper.activity.AboutUs;
import com.dextroxd.taskhelper.activity.EditProfileActivity;
import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.login.LoginActivity;
import com.dextroxd.taskhelper.login.SignupActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {
    private FirebaseAuth auth;
    private String name;
    private DatabaseReference database_Users;
    private TextView textView;
    String uid,phoneNumber="8684889061";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile,container,false);
        LinearLayout linearLayout = view.findViewById(R.id.HelpCenter);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), ContactUs.class));
            }
        });
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null)
        {
            startActivity(new Intent(getActivity(), SignupActivity.class));
        }
        database_Users = FirebaseDatabase.getInstance().getReference("users");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            user.reload();
            String email = user.getEmail();
            TextView textView1 = view.findViewById(R.id.emailidlogedin);
            textView1.setText(email);

            textView= view.findViewById(R.id.nampelace);
            getValue();

        ImageButton imageButton = view.findViewById(R.id.editprofile);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });
        LinearLayout linearLayout1 = view.findViewById(R.id.aboutus1);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutUs.class));
            }
        });
        LinearLayout linearLayout2 = view.findViewById(R.id.logout1);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    auth.signOut();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
                else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }

            }
        });
        LinearLayout linearLayout3 = view.findViewById(R.id.sharelink);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setChooserTitle("Send via")
                        .setText("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())
                        .startChooser();
            }
        });
        LinearLayout linearLayout4 = view.findViewById(R.id.rateus);
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });

        return view;
    }


    public void  getValue()
    {

        database_Users.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (userSnapshot.getKey().equals("name_User")) {
                        name= userSnapshot.getValue().toString();

                    }

                }
                textView.setText("Hey "+name+", your id is:");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

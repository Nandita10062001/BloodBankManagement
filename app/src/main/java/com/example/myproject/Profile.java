package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    private TextView profileName, profileEmail, profileBg, profilePhoneNo, profileCity;
    private Button back;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        profileName = findViewById(R.id.enter_fullName);
        profileEmail = findViewById(R.id.enter_email);
        profileBg = findViewById(R.id.inputBloodGroup);
        profilePhoneNo = findViewById(R.id.inputMobile);
        profileCity = findViewById(R.id.inputCity);
        back = findViewById(R.id.btn_back);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null)
                {
                    String fullName = userProfile.FullName;
                    String Email = userProfile.email;
                    String BloodGroup = userProfile.bloodGroup;
                    String Mobile = userProfile.Phone;
                    String City = userProfile.address;

                    profileEmail.setText(Email);
                    profileBg.setText(BloodGroup);
                    profileCity.setText(City);
                    profilePhoneNo.setText(Mobile);
                    profileName.setText(fullName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
             Toast.makeText(Profile.this,"Something went wrong", Toast.LENGTH_LONG).show();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Home.class);
                startActivity(intent);
            }
        });

    }
}
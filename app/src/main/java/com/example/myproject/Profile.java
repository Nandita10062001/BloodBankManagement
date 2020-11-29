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
    private FirebaseAuth fAuth;
    private Button update, back;
    private String userId;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("profile") != null) {
                Toast.makeText(getApplicationContext(), "data" + bundle.getString("profile")
                        , Toast.LENGTH_SHORT).show();
            }
        }
        profileName = findViewById(R.id.enter_fullName);
        profileEmail = findViewById(R.id.enter_email);
        profileBg = findViewById(R.id.inputBloodGroup);
        profilePhoneNo = findViewById(R.id.inputMobile);
        profileCity = findViewById(R.id.inputCity);
        fAuth = FirebaseAuth.getInstance();
        update = findViewById(R.id.btn_update);
        back = findViewById(R.id.btn_back);
        userId = user.getUid();
        user = fAuth.getInstance().getCurrentUser();
        reference = firebaseDatabase.getReference(fAuth.getUid());
        firebaseDatabase = FirebaseDatabase.getInstance();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                profileName.setText(user.getFullName());
                profileEmail.setText(user.getEmail());
                profileBg.setText(user.getBloodGroup());
                profilePhoneNo.setText(user.getPhone());
                profileCity.setText(user.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this,error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditProfile.class);
                //i.putExtra("fullName",profileName .getText().toString());
                //i.putExtra("Email", profileEmail.getText().toString());
                //i.putExtra("BloodGroup", profileBg.getText().toString());
                //i.putExtra("Phone",profilePhoneNo.getText().toString());
                //i.putExtra("City", profileCity.getText().toString());
                startActivity(i);
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
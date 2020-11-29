package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText pFullName,pEmail,pMobile,pCity,pBloodGroup;
    private FirebaseAuth fAuth;
    private FirebaseDatabase database;


    Button saveBtn;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        pFullName = findViewById(R.id.in_fullName);
        pEmail = findViewById(R.id.in_Email);
        pCity = findViewById(R.id.in_City);
        pMobile = findViewById(R.id.in_Mobile);
        pBloodGroup = findViewById(R.id.in_BloodGroup);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        saveBtn = findViewById(R.id.button_save);
        final DatabaseReference reference = database.getReference(fAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                pFullName.setText(user.getFullName());
                pEmail.setText(user.getEmail());
                pBloodGroup.setText(user.getBloodGroup());
                pMobile.setText(user.getPhone());
                pCity.setText(user.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this,error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = pFullName.getText().toString();
                String email = pEmail.getText().toString();
                String Bg = pBloodGroup.getText().toString();
                String phone = pMobile.getText().toString();
                String city = pCity.getText().toString();

                User user = new User(email,name,city,phone,Bg);
                reference.setValue(user);
                finish();
            }
        });
    }
}
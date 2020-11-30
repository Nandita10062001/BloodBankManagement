package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
   private FirebaseAuth mAuth;
   private EditText fullName,Email,createPassword,City,PhoneNo,BloodGroup;
   private TextView Login;
   private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fullName = findViewById(R.id.FullName);
        Email = findViewById(R.id.Email);
        createPassword = findViewById(R.id.Password);
        City = findViewById(R.id.city);
        PhoneNo = findViewById(R.id.Phone);
        BloodGroup = findViewById(R.id.BG);
        Login = findViewById(R.id.Login);
        Login.setOnClickListener(this);
        Register =findViewById(R.id.REGISTER);
        Register.setOnClickListener(this);
    }

    public void onClick(View v)
    {
       int Id = v.getId();
       if(Id == R.id.Login)
       {
           startActivity(new Intent(this, Login.class));
       }

       else if(Id == R.id.REGISTER)
       {
           Register();
       }
    }

    private void Register()
    {
        final String email = Email.getText().toString().trim();
        final String FullName = fullName.getText().toString().trim();
        String Password = createPassword.getText().toString().trim();
        final String city = City.getText().toString().trim();
        final String Phone = PhoneNo.getText().toString().trim();
        final String bloodGroup = BloodGroup.getText().toString().trim();

        if(email.isEmpty())
        {
            Email.setError("Email is Required!");
            Email.requestFocus();
            return;
        }

        if(FullName.isEmpty())
        {
            fullName.setError("Please fill in your Name...");
            fullName.requestFocus();
            return;
        }

        if(Password.isEmpty())
        {
            createPassword.setError("Password is Required!");
            createPassword.requestFocus();
            return;
        }
        if(city.isEmpty())
        {
            City.setError("Please mention your city");
            City.requestFocus();
            return;
        }
        if(Phone.isEmpty())
        {
            PhoneNo.setError("Phone Number is Required!");
            PhoneNo.requestFocus();
            return;
        }
        if(bloodGroup.isEmpty())
        {
            BloodGroup.setError("Entering your Blood Group is Mandatory... ");
            BloodGroup.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Please Provide a Valid Email!");
            Email.requestFocus();
            return;
        }
        if(Password.length()<6)
        {
            createPassword.setError("Password should be of minimum 6 characters!");
            createPassword.requestFocus();
            return;
        }
        if(Phone.length()<10){
            PhoneNo.setError("Not a valid Phone Number");
            PhoneNo.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(email,FullName,city,Phone,bloodGroup);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Home.class));
                            }
                            else{
                                Toast.makeText(Register.this,"Failed to register! Try Again!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Register.this,"Failed to Register!Try Again!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
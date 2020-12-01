package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Post extends AppCompatActivity {
    ProgressDialog pd;
    private Button Post;
    EditText name,bloodGroup,contact,city;
    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseAuth auth;

    Calendar cal;
    String uid;
    String Time,Date;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Post =findViewById(R.id.postbtn);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);


        name=findViewById(R.id.getName);
        contact = findViewById(R.id.getContact);
        bloodGroup = findViewById(R.id.getBG);
        city = findViewById(R.id.getPlace);
        cal = Calendar.getInstance();

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        month+=1;
        Time = "";
        Date = "";
        String ampm="AM";

        if(cal.get(Calendar.AM_PM) ==1)
        {
            ampm = "PM";
        }

        if(hour<10)
        {
            Time += "0";
        }
        Time += hour;
        Time +=":";

        if(min<10) {
            Time += "0";
        }

        Time +=min;
        Time +=(" "+ampm);
        Date = day+"/"+month+"/"+year;



        FirebaseUser user=auth.getInstance().getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(Post.this,Login.class));
        }else
        {
            uid=user.getUid();
        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("posts");

        try {
            Post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Query findName = db.getReference("Users").child(uid);
                    if(name.getText().length()==0)
                    {
                        Toast.makeText(Post.this,"Enter Your Name!",Toast.LENGTH_LONG).show();
                    }
                    else if(bloodGroup.getText().length()==0)
                    {
                        Toast.makeText(Post.this,"Enter your Blood Group!",Toast.LENGTH_LONG).show();
                    }
                    else if(contact.getText().length()==0)
                    {
                        Toast.makeText(Post.this,"Enter Your Contact Number!",Toast.LENGTH_LONG).show();
                    }
                    else if(city.getText().length()==0)
                    {
                        Toast.makeText(Post.this,"Enter the name of your City!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        findName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    ref.child(uid).child("FullName").setValue(snapshot.getValue(User.class).FullName);
                                    ref.child(uid).child("Phone").setValue(snapshot.getValue(User.class).Phone);
                                    ref.child(uid).child("bloodGroup").setValue(snapshot.getValue(User.class).bloodGroup);
                                    ref.child(uid).child("address").setValue(snapshot.getValue(User.class).address);
                                    ref.child(uid).child("Time").setValue(Time);
                                    ref.child(uid).child("Date").setValue(Date);
                                    Toast.makeText(Post.this,"Your Post has been created successfully",Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(Post.this,Home.class));
                                }else{
                                    Toast.makeText(getApplicationContext(),"Database Error occurred",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("User",error.getMessage());
                            }
                        });
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        pd.dismiss();
    }

}
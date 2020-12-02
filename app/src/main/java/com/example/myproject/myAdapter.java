package com.example.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<User,myAdapter.myviewholder>
{

    public myAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull User user)
    {
        holder.Name.setText(user.FullName);
        holder.Contact.setText(user.Phone);
        holder.BloodGroup.setText(user.bloodGroup);
        holder.City.setText(user.address);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView Name,Contact,City,BloodGroup;

        public myviewholder(@NonNull View view)
        {
            super(view);
            Name = view.findViewById(R.id.donorName);
            Contact=view.findViewById(R.id.donorContact);
            City = view.findViewById(R.id.donorCity);
            BloodGroup=view.findViewById(R.id.donorBloodGrp);
        }
    }
}
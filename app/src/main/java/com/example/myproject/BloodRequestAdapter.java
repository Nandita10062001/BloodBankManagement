package com.example.myproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.PostHolder>
{
private List<CustomUserData> postLists;

public class PostHolder extends RecyclerView.ViewHolder
{
    TextView FullName, bloodGroup,address,Phone,Posted;

    public PostHolder(@NonNull View itemView)
    {
        super(itemView);
        FullName = itemView.findViewById(R.id.requestUser);
        bloodGroup = itemView.findViewById(R.id.blood);
        address = itemView.findViewById(R.id.requestLocation);
        Phone = itemView.findViewById(R.id.CN);
        Posted = itemView.findViewById(R.id.post);
    }
}

public BloodRequestAdapter(List<CustomUserData> postLists)
{
    this.postLists = postLists;
}

    @Override
    public PostHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View listitem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.request_list, viewGroup, false);

        return new PostHolder(listitem);
    }

    @Override
    public void onBindViewHolder(PostHolder postHolder, int i) {

        if(i%2==0)
        {
            postHolder.itemView.setBackgroundColor(Color.parseColor("#C13F31"));
        }
        else
        {
            postHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        CustomUserData customUserData = postLists.get(i);
        postHolder.FullName.setText("Posted by: "+customUserData.getFullName());
        postHolder.address.setText("From: "+customUserData.getAddress());
        postHolder.bloodGroup.setText("Needs "+customUserData.getBloodGroup());
        postHolder.Posted.setText("Posted on:"+customUserData.getTime()+", "+customUserData.getDate());
        postHolder.Phone.setText(customUserData.getPhone());

    }

    @Override
    public int getItemCount() {
        return postLists.size();
    }
}


package com.example.myproject;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
  private View view;
  private RecyclerView Posts;
  private DatabaseReference ref;
  FirebaseAuth auth;
  private BloodRequestAdapter requestAdapter;
  private List<CustomUserData> postLists;
  private ProgressDialog pd;
  SearchView searchView;
  public HomeFragment()
  {

  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Posts =(RecyclerView)view.findViewById(R.id.recyclePosts);
        Posts.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = (SearchView)view.findViewById(R.id.searchView);
        ref = FirebaseDatabase.getInstance().getReference();
        postLists = new ArrayList<>();

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();
        getActivity().setTitle("Blood Bank");

        requestAdapter = new BloodRequestAdapter(postLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        Posts.setLayoutManager(layoutManager);
        Posts.setItemAnimator(new DefaultItemAnimator());
        Posts.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        Posts.setAdapter(requestAdapter);

        AddPosts();
        return view;
    }

    private void AddPosts() {
        Query allPosts = ref.child("posts");
        allPosts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot single : snapshot.getChildren()) {
                        CustomUserData customUserData = single.getValue(CustomUserData.class);
                        postLists.add(customUserData);
                        requestAdapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(getActivity(), "Database is empty now!", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Users", error.getMessage());
            }
        });
        if(searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String str)
    {
        ArrayList<CustomUserData> myList = new ArrayList<>();
        for(CustomUserData object : postLists)
        {
            if(object.getBloodGroup().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }

        requestAdapter = new BloodRequestAdapter(myList);
        Posts.setAdapter(requestAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
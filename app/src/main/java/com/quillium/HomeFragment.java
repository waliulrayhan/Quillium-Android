package com.quillium;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//==================================================================================================
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth, auth;
    private FirebaseUser currentUser;
    DatabaseReference userRef;
//==================================================================================================

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Assuming you have UI elements in fragment_home.xml
        TextView textViewName = view.findViewById(R.id.newsfeed_textViewName);
        TextView textViewDateTime = view.findViewById(R.id.newsfeed_textViewDateTime);
        TextView textViewPost = view.findViewById(R.id.newsfeed_textViewPost);
        ImageView imageViewPostPhoto = view.findViewById(R.id.newsfeed_imageViewPostPhoto);

        // Attach a listener to read the data at the "posts" node
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                // Assuming you have a node in your database named "users"
                DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");

                // A new post has been added
                // Retrieve data from the snapshot and update UI
                // Inside your onChildAdded method
                if (dataSnapshot.exists()) {
                    String userId = dataSnapshot.child("userId").getValue(String.class);
                    String postText = dataSnapshot.child("postText").getValue(String.class);
                    String timestamp = dataSnapshot.child("timestamp").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    // Retrieve user's full name from the "users" node
                    usersReference.child(userId).child("fullname").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if (userSnapshot.exists()) {
                                String fullName = userSnapshot.getValue(String.class);

                                // Update UI with post data
                                if (textViewName != null && textViewDateTime != null && textViewPost != null && imageViewPostPhoto != null) {
                                    textViewName.setText(fullName);
                                    textViewDateTime.setText(formatDate(timestamp));
                                    textViewPost.setText(postText);
                                    // Load the image using Glide
                                    if (imageUrl != null && !imageUrl.isEmpty()) {
                                        Glide.with(requireContext()).load(imageUrl).into(imageViewPostPhoto);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors
                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // A child (post) has been updated
                // Handle the data retrieval and UI update here
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // A child (post) has been removed
                // Handle the UI update or other actions here
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // A child (post) has changed position
                // Handle the UI update or other actions here
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }

    private String formatDate(String timestamp) {
        // Convert timestamp to a human-readable date format
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - hh:mm a");
            Date date = new Date(Long.parseLong(timestamp));
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return timestamp;
        }
    }
}
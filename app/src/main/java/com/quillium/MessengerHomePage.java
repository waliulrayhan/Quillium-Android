package com.quillium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.quillium.databinding.ActivityMessengerHomePageBinding;
import com.quillium.utils.Constants;
import com.squareup.picasso.Picasso;

public class MessengerHomePage extends AppCompatActivity {

    private ActivityMessengerHomePageBinding binding;
    DatabaseReference userRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessengerHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        loadUserData();
//        updateToken();
    }

//    private void updateToken() {
//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = firestore.collection(Constants.KEY_COLLECTION_USERS).document()
//    }

    private void loadUserData() {
        userRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    String profilePhotoUrl = user.getProfilePhotoUrl();

                    // Load Profile photo using Picasso or any other image loading library
                    Picasso.get()
                            .load(profilePhotoUrl)
                            .placeholder(R.drawable.profile_photo_placeholder)
                            .into(binding.messageImageViewID);

                    String fullname = user.getFullname();
                    String email = user.getEmail();

                    // Set the fullname and email to the TextViews
                    binding.chatsTextID.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });
    }
}
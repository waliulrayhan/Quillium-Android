package com.quillium.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quillium.R;
import com.quillium.User;
import com.quillium.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    ImageView coverPhoto, cPhoto, profile,profilePhoto;
    TextView name, id;
    FragmentProfileBinding binding;
    FirebaseStorage storage;
    FirebaseDatabase database;
    private Uri imageUri;
    DatabaseReference userRef, databaseReference;



    private static final int PICK_IMAGE_REQUEST_PROFILE = 1;
    private static final int PICK_IMAGE_REQUEST_COVER = 2;
    private static final int RESULT_OK = -1; // or Activity.RESULT_OK



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        coverPhoto = view.findViewById(R.id.changeCoverPhoto);
        cPhoto = view.findViewById(R.id.coverPhoto);
        profile = view.findViewById(R.id.changeProfilePhoto);
        profilePhoto = view.findViewById(R.id.profile_picture_image);
        name = view.findViewById(R.id.username);
        id = view.findViewById(R.id.userId);


        userRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    String coverPhotoUrl = user.getCoverPhotoUrl();
                    String profilePhotoUrl = user.getProfilePhotoUrl();

                    // Load cover photo using Picasso or any other image loading library
                    Picasso.get()
                            .load(coverPhotoUrl)
                            .placeholder(R.drawable.pexels1)
                            .into(cPhoto);

                    // Load Profile photo using Picasso or any other image loading library
                    Picasso.get()
                            .load(profilePhotoUrl)
                            .placeholder(R.drawable.man)
                            .into(profilePhoto);

                    String fullname = user.getFullname();
                    String email = user.getEmail();

                    // Set the fullname and email to the TextViews
                    name.setText(fullname);
                    id.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForProfile();
            }
        });

        coverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryForCoverPhoto();
            }
        });

        return view;
    }

    private void openGalleryForProfile() {
        // Open gallery for profile photo
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_PROFILE);
    }

    private void openGalleryForCoverPhoto() {
        // Open gallery for cover photo
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_COVER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (requestCode == PICK_IMAGE_REQUEST_PROFILE) {
                // Set the selected image to the profile photo
                profilePhoto.setImageURI(imageUri);
                // Upload the profile photo to Firebase if needed
                 uploadProfilePhotoToFirebase();
            } else if (requestCode == PICK_IMAGE_REQUEST_COVER) {
                // Set the selected image to the cover photo
                cPhoto.setImageURI(imageUri);
                // Upload the cover photo to Firebase if needed
                 uploadCoverPhotoToFirebase();
            }
        }
    }

    private void uploadCoverPhotoToFirebase() {
        if (imageUri != null) {
            final StorageReference reference = storage.getReference().child("cover_photo")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Image uploaded successfully
                            Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                            // Get the download URL of the uploaded image
                            reference.getDownloadUrl().addOnSuccessListener(uri -> {
                                // Update user data in the database with the image URL
                                updateCoverPhotoInDatabase(uri.toString());
                            });
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors that occurred during the upload
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });

        }
    }
    private void updateCoverPhotoInDatabase(String imageUrl) {
        // Get the reference to the user's data in the database
        userRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid());

        // Create a Map to update the user's data
        Map<String, Object> updates = new HashMap<>();
        updates.put("coverPhotoUrl", imageUrl);

        // Update the user's data in the database
        userRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    // Database update successful
                    Toast.makeText(getActivity(), "Cover Photo updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occurred during the database update
                    Toast.makeText(getActivity(), "Failed to update Cover Photo", Toast.LENGTH_SHORT).show();
                });
    }

    private void uploadProfilePhotoToFirebase() {
        if (imageUri != null) {
            final StorageReference reference = storage.getReference().child("profile_photo")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(getActivity(), "Profile photo uploaded successfully", Toast.LENGTH_SHORT).show();

                        // Get the download URL of the uploaded image
                        reference.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Update user data in the database with the profile photo URL
                            updateProfilePhotoInDatabase(uri.toString());
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Failed to upload profile photo", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void updateProfilePhotoInDatabase(String imageUrl) {
        userRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid());

        Map<String, Object> updates = new HashMap<>();
        updates.put("profilePhotoUrl", imageUrl);

        userRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Profile photo updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to update profile photo", Toast.LENGTH_SHORT).show());
    }
}
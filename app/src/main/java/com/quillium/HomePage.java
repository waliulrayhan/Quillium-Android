package com.quillium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    FloatingActionButton fab;
    TextView fullName, studentEmail;
    FirebaseAuth auth;
    DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home_button) {
                    openFragment(new HomeFragment());
                    return true;
                } else if (id == R.id.friends_button) {
                    openFragment(new FriendFragment());
                    return true;
                } else if (id == R.id.notifications_button) {
                    openFragment(new NotificationFragment());
                    return true;
                } else if (id == R.id.profile_button) {
                    openFragment(new ProfileFragment());
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeFragment());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });


//==================================================================================================
        // Get the header view of the NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Find the TextViews within the header view
        fullName = headerView.findViewById(R.id.nav_full_name);
        studentEmail = headerView.findViewById(R.id.nav_student_email);

        // Initialize Firebase Auth and Database references
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("users");

        // Load user data
        loadUserData();
//==================================================================================================


    }

    private void loadUserData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Data exists, retrieve the values
                        String name = dataSnapshot.child("fullname").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);

                        // Update the TextViews with actual data
                        fullName.setText(name);
                        studentEmail.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                    Log.e("FirebaseData", "Error reading user data: " + databaseError.getMessage());
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.nav_home) {
//            openFragment(new HomeFragment());
            Toast.makeText(HomePage.this, "Home is Clicked", Toast.LENGTH_LONG).show();
        } else if (itemID == R.id.nav_settings) {
//            openFragment(new FriendFragment());
            Toast.makeText(HomePage.this, "Settings is Clicked", Toast.LENGTH_LONG).show();
        } else if (itemID == R.id.nav_share) {
//            openFragment(new NotificationFragment());
            Toast.makeText(HomePage.this, "Share is Clicked", Toast.LENGTH_LONG).show();
        } else if (itemID == R.id.nav_about) {
//            openFragment(new ProfileFragment());
            Toast.makeText(HomePage.this, "About is Clicked", Toast.LENGTH_LONG).show();
        } else if (itemID == R.id.nav_logout) {
            Toast.makeText(this, "Logout is Successful", Toast.LENGTH_LONG).show();

            auth.signOut();

            // Redirect the user to the login screen or perform any other necessary actions
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity to prevent the user from going back
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout createPostLayout = dialog.findViewById(R.id.createPost);
        LinearLayout createStoryLayout = dialog.findViewById(R.id.createStory);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        createPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(HomePage.this, "Create a Post is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        createStoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(HomePage.this, "Create a Story is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_search) {
//            openFragment(new HomeFragment());
            Toast.makeText(HomePage.this, "Search is Clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_messenger) {
//            openFragment(new FriendFragment());
            Toast.makeText(HomePage.this, "Messenger is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
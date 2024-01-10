package com.quillium.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quillium.Adapter.PostAdapter;
import com.quillium.Adapter.StoryAdapter;
import com.quillium.Model.Post;
import com.quillium.Model.StoryModel;
import com.quillium.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    RecyclerView storyRV, dashboardRV;
    ArrayList<StoryModel> list;
    ArrayList<Post> PostList;
    FirebaseDatabase database;
    FirebaseAuth auth;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        storyRV = view.findViewById(R.id.storyRV);

        list = new ArrayList<>();
        list.add(new StoryModel(R.drawable.person,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1050,R.drawable.baseline_add_24,R.drawable.baseline_home_24,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1050,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1222,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));

        StoryAdapter adapter = new StoryAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);
        storyRV.setAdapter(adapter);


//        dashboardRV = view.findViewById(R.id.dashboardRv);
//        dashboardList = new ArrayList<>();
////        dashboardList.add(new Post(R.drawable.img_1191,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
////        dashboardList.add(new Post(R.drawable.img_1050,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
////        dashboardList.add(new Post(R.drawable.img_1222,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
////        dashboardList.add(new Post(R.drawable.img20231103163126,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
////        dashboardList.add(new Post(R.drawable.img20231103163131,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
//
//        PostAdapter postAdapter = new PostAdapter(dashboardList,getContext());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        dashboardRV.setLayoutManager(layoutManager);
//        dashboardRV.setNestedScrollingEnabled(false);
//        dashboardRV.setAdapter(postAdapter);
//        return view;

        dashboardRV = view.findViewById(R.id.dashboardRv);
        PostList = new ArrayList<>();

        PostAdapter postAdapter = new PostAdapter(PostList,getContext());
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
//        dashboardRV.setLayoutManager(linearLayoutManager1);
//        dashboardRV.addItemDecoration(new DividerItemDecoration(dashboardRV.getContext(), DividerItemDecoration));


        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    PostList.add(post);
                }
//                PostAdapter.noti
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

}
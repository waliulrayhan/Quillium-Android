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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quillium.Adapter.PostAdapter;
import com.quillium.Adapter.StoryAdapter;
import com.quillium.Model.Post;
import com.quillium.Model.Story;
import com.quillium.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    RecyclerView storyRV, dashboardRV;
    ArrayList<Story> list;
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
//        list.add(new Story(R.drawable.person,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
//        list.add(new Story(R.drawable.img_1050,R.drawable.baseline_add_24,R.drawable.baseline_home_24,"Waliul"));
//        list.add(new Story(R.drawable.img_1050,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
//        list.add(new Story(R.drawable.img_1222,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));

        StoryAdapter adapter = new StoryAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);
        storyRV.setAdapter(adapter);

//      Dashboard Recycler View
        dashboardRV = view.findViewById(R.id.dashboardRv);
        PostList = new ArrayList<>();
        PostAdapter postAdapter = new PostAdapter(PostList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.addItemDecoration(new DividerItemDecoration(dashboardRV.getContext(), DividerItemDecoration.HORIZONTAL));
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(postAdapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    post.setPostId(dataSnapshot.getKey());
                    PostList.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

//        dashboardRV = view.findViewById(R.id.dashboardRv);
//        PostList = new ArrayList<>();

//        PostAdapter postAdapter = new PostAdapter(PostList,getContext());
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
//        dashboardRV.setLayoutManager(linearLayoutManager1);
//        dashboardRV.addItemDecoration(new DividerItemDecoration(dashboardRV.getContext(), DividerItemDecoration));


//        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Post post = dataSnapshot.getValue(Post.class);
//                    PostList.add(post);
//                }
////                PostAdapter.noti
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        return view;
    }

}
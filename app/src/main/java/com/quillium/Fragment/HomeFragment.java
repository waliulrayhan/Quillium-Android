package com.quillium.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quillium.Adapter.DashboardAdapter;
import com.quillium.Adapter.StoryAdapter;
import com.quillium.Model.Post;
import com.quillium.Model.StoryModel;
import com.quillium.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    RecyclerView storyRV, dashboardRV;
    ArrayList<StoryModel> list;
    ArrayList<Post> dashboardList;


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


        storyRV = view.findViewById(R.id.storyRV);

        list = new ArrayList<>();
        list.add(new StoryModel(R.drawable.person,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1050,R.drawable.baseline_add_24,R.drawable.baseline_home_24,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1050,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1191,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));
        list.add(new StoryModel(R.drawable.img_1222,R.drawable.avatar_person,R.drawable.active_chat_bac,"Waliul"));

        StoryAdapter adapter = new StoryAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);
        storyRV.setAdapter(adapter);


        dashboardRV = view.findViewById(R.id.dashboardRv);
        dashboardList = new ArrayList<>();
//        dashboardList.add(new Post(R.drawable.img_1191,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
//        dashboardList.add(new Post(R.drawable.img_1050,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
//        dashboardList.add(new Post(R.drawable.img_1222,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
//        dashboardList.add(new Post(R.drawable.img20231103163126,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));
//        dashboardList.add(new Post(R.drawable.img20231103163131,R.drawable.img20231103163126,R.drawable.aklogo_1,"Waliul Islam","Food Blogger","500","15","10"));

        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(dashboardAdapter);
        return view;
    }

}
package com.quillium.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quillium.Model.Story;
import com.quillium.Model.UserStories;
import com.quillium.R;
import com.quillium.User;
import com.quillium.databinding.StoryRvDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder>{


    ArrayList<Story> list;
    Context context;

    public StoryAdapter(ArrayList<Story> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_rv_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Story story = list.get(position);
/*
        UserStories lastStory = story.getStories().get(story.getStories().size()-1);
        Picasso.get()
                .load(lastStory.getImage())
                .into(holder.binding.storyPostImage);

        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(story.getStoryBy()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfilePhotoUrl())
                                .into(holder.binding.profileImagePicture);
                        holder.binding.name.setText(user.getFullname());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

//        holder.storyImg.setImageResource(model.getStory());
//        holder.profile.setImageResource(model.getProfile());
//        holder.storyType.setImageResource(model.getStoryType());
//        holder.name.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

//        ImageView storyImg, profile, storyType;
//        TextView name;

        StoryRvDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

//            storyImg = itemView.findViewById(R.id.postImage);
//            profile = itemView.findViewById(R.id.profile_image_picture);
//            storyType = itemView.findViewById(R.id.storyType);
//            name = itemView.findViewById(R.id.name);

            binding = StoryRvDesignBinding.bind(itemView);

        }
    }
}

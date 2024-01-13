package com.quillium.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.quillium.Model.Follow;
import com.quillium.R;
import com.quillium.User;
import com.quillium.databinding.UserSampleBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{

    Context context;
    ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User user = list.get(position);

        Picasso.get()
                .load(user.getProfilePhotoUrl())
                .into(holder.binding.profilePictureImage);

        holder.binding.textView4.setText(user.getFullname());
        holder.binding.textView5.setText(user.getEmail());

//        holder.binding.followBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Follow follow = new Follow();
//                follow.setFollowedBy(FirebaseAuth.getInstance().getTenantId());
//                follow.getFollowedAt(new Date().getTime());
//
//                FirebaseDatabase.getInstance().getReference()
//                        .child("users")
//                        .child(user.getUserId())
//                        .child("followers")
//                        .child(FirebaseAuth.getInstance().getUid())
//                        .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                FirebaseDatabase.getInstance().getReference()
//                                        .child("users")
//                                        .child(user.getUserId())
//                                        .child("followerCount")
//                                        .setValue(user.getFollowerCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                Toast.makeText(context,"You Followed "+user.getFullname(),Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            }
//                        });
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        UserSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = UserSampleBinding.bind(itemView);
        }
    }
}

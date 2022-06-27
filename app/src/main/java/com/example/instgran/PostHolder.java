package com.example.instgran;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostHolder extends RecyclerView.ViewHolder {
    CircleImageView iv_userImgPost;
    TextView tv_username, tv_contentPost, tv_date, tv_numberLikes, tv_numberComments;
    Button bt_like, bt_comment;
    ImageView iv_iconLike, iv_iconComment;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        iv_userImgPost = itemView.findViewById(R.id.iv_itemImage);
        tv_username = itemView.findViewById(R.id.tv_usernmaeItemPost);
        tv_contentPost = itemView.findViewById(R.id.tv_contentPost);
        tv_date = itemView.findViewById(R.id.tv_date);
        tv_numberLikes = itemView.findViewById(R.id.tv_numberLike);
        tv_numberComments = itemView.findViewById(R.id.tv_numberComment);
        bt_like = itemView.findViewById(R.id.ib_like);
        bt_comment = itemView.findViewById(R.id.ib_comment);
        iv_iconLike = itemView.findViewById(R.id.iv_iconLike);
        iv_iconComment = itemView.findViewById(R.id.iv_iconComment);

        tv_numberLikes.setVisibility(View.INVISIBLE);
        iv_iconLike.setVisibility(View.INVISIBLE);
    }

    public void countLikes(String postKey, String uid, DatabaseReference likeRef) {
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalLikes = (int) snapshot.getChildrenCount();
                    tv_numberLikes.setText(totalLikes + "");
                    tv_numberLikes.setVisibility(View.VISIBLE);
                    iv_iconLike.setVisibility(View.VISIBLE);

                } else {
                    tv_numberLikes.setVisibility(View.INVISIBLE);
                    iv_iconLike.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists()) {

                    bt_like.setTextColor(Color.parseColor("#4267B2"));
                    bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#4267B2")));

                } else {
                    bt_like.setTextColor(Color.parseColor("#7A7A7A"));
                    bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#7A7A7A")));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

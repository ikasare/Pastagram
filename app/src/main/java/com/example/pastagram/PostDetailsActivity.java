package com.example.pastagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {
    private TextView tvDeatilsUsername;
    private TextView tvDetailsTime;
    private ImageView ivDetailsPost;
    private ImageButton ibHeart;
    private ImageButton ibComment;
    private TextView tvLikeCount;
    RecyclerView rvComments;
    CommentsAdapter adapter;
    Post post;


    @Override
    protected void onRestart() {
        super.onRestart();

        refreshComments();
    }

    void refreshComments(){

        ParseQuery<Comment> query = ParseQuery.getQuery("Comment");
        query.whereEqualTo(Comment.KEY_POST, post);
        query.orderByDescending("createdAt");
        query.include(Comment.KEY_AUTHOR);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if(e != null){
                    Log.e("Failed to get comments", e.getMessage());
                    return;
                }
                adapter.mComments.clear();
                adapter.mComments.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvDeatilsUsername = findViewById(R.id.tvUsername);
        tvDetailsTime = findViewById(R.id.tvDate);
        ivDetailsPost = findViewById(R.id.ivPhoto);
        ibHeart = findViewById(R.id.ibHeart);
        ibComment = findViewById(R.id.ibCooment);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        rvComments = findViewById(R.id.rvComments);
        post = getIntent().getParcelableExtra("post");
        ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostDetailsActivity.this, ComposeCommentActivity.class);
                i.putExtra("post", post);

                startActivity(i);
            }
        });
        ibHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ParseUser> likedBy = post.getLikedBy();
                if(post.isLikedByCurrentUser()){
                    // unlike
                    post.unlike();
                    ibHeart.setBackgroundResource(R.drawable.ufi_heart);
                }else{
                    // like
                    post.like();
                    ibHeart.setBackgroundResource(R.drawable.ufi_heart_active);
                }

                tvLikeCount.setText(post.getLikeCount());
            }
        });

        rvComments = findViewById(R.id.rvComments);
        adapter = new CommentsAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(adapter);

        tvDeatilsUsername.setText(post.getUser().getUsername());

        if(post.getLikedBy().contains(ParseUser.getCurrentUser())){
            ibHeart.setBackgroundResource(R.drawable.ufi_heart_active);
        }else {
            ibHeart.setBackgroundResource(R.drawable.ufi_heart);
        }

        tvLikeCount.setText(post.getLikeCount());

        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(PostDetailsActivity.this).load(image.getUrl()).into(ivDetailsPost);
        }
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvDetailsTime.setText(timeAgo);

        // load all comments in post
        refreshComments();
    }

}
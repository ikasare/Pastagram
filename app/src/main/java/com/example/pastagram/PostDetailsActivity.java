package com.example.pastagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

public class PostDetailsActivity extends AppCompatActivity {
    private TextView tvDeatilsUsername;
    private TextView tvDetailsDescription;
    private TextView tvDetailsTime;
    private ImageView ivDetailsPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvDeatilsUsername = findViewById(R.id.tvDetailUsername);
        tvDetailsDescription = findViewById(R.id.tvDetailsDescription);
        tvDetailsTime = findViewById(R.id.tvTime);
        ivDetailsPost = findViewById(R.id.ivDetailsImage);

        Post post = getIntent().getParcelableExtra("post");

        tvDeatilsUsername.setText(post.getUser().getUsername());
        tvDetailsDescription.setText(post.getDescription());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(PostDetailsActivity.this).load(image.getUrl()).into(ivDetailsPost);
        }


    }
}
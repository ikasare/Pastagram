package com.example.pastagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ComposeCommentActivity extends AppCompatActivity {

    Post post;
    Button btnSave;
    EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_comment);

        post = getIntent().getParcelableExtra("post");

        Toast.makeText(this, post.getDescription(), Toast.LENGTH_SHORT).show();

        btnSave = findViewById(R.id.btnSave);
        etBody = findViewById(R.id.etBody);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post the new comment to parse
                String body = etBody.getText().toString();
                // schema: comment.java
                Comment comment = new Comment();
                comment.setAuthor(ParseUser.getCurrentUser());
                comment.setBody(body);
                comment.setPost(post);

                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Log.e("ComposeComment", e.getMessage());
                            return;
                        }
                        finish();
                    }
                });
            }
        });
    }
}
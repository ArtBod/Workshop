package com.example.workshop;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Workshop details class .
 */
public class WorkshopDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshop_detail);
        VideoView videoView = (VideoView)findViewById(R.id.videoview);
        ImageView imageView=findViewById(R.id.author_image_id);
        TextView name = findViewById(R.id.author_name_id);
        TextView description = findViewById(R.id.description_id);
        TextView text = findViewById(R.id.text_id);

        Bundle bundle =getIntent().getExtras();
        String mName= bundle.getString("name");
        String mImage= bundle.getString("image");
        String mDescription= bundle.getString("description");
        String mText= bundle.getString("text");
        String mVideo= bundle.getString("video");

        //set value
        Uri vidUri = Uri.parse(mVideo);
        videoView.setVideoURI(vidUri);
        Glide.with(this).load(mImage).apply(RequestOptions.circleCropTransform()).into(imageView);
        name.setText(mName);
        description.setText(mDescription);
        text.setText(mText);

        //play the video
        videoView.start();


    }
}

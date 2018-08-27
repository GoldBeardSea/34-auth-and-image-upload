package com.gbbeard.instabran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedActivity extends AppCompatActivity {

    public static final String TAG = "FIREBASE: ";

    @BindView(R.id.feed) RecyclerView feed;
    private LinearLayoutManager linearLayoutManager;
    private PostAdapter postAdapter;

    List<Post> allPostItems;

    DatabaseReference mPublishedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Log.d("FEED_ACTIVITY", "OnCreate being hit from the Feed Activity");
        ButterKnife.bind(this);

//      Read from the database
        mPublishedPhotos =  FirebaseDatabase.getInstance().getReference();
        mPublishedPhotos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> photoRef = dataSnapshot.child("photos").getChildren();
                List<Post> photoItems = new ArrayList<>();

                for (DataSnapshot photo : photoRef){
                    String key = photo.getKey();
                    String imageUrl = photo.child("imageUrl").getValue(String.class);
                    String description = photo.child("description").getValue(String.class);
                    String username = photo.child("username").getValue(String.class);

                    Post feedStatus = new Post(key, imageUrl, description, username);
                    photoItems.add(feedStatus);
                }
                postAdapter.replaceList(photoItems);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        linearLayoutManager = new LinearLayoutManager(this);

        allPostItems = new ArrayList<>();
        postAdapter = new PostAdapter(allPostItems);

        feed.setLayoutManager(linearLayoutManager);
        feed.setAdapter(postAdapter);

    }

    @OnClick(R.id.post)
    public void takePicture() {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }
}
package com.example.snc.instademo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog mProgressDialog;
    FirebaseStorage storage;
    StorageReference mStorageRef;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;

    static final int GALLERY_REQUEST = 10;
    Button addBTN;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button view
        addBTN = (Button)findViewById(R.id.upload_btn);
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("allPosts");
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview_home);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this,3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Sharing.....");

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST );
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

                startPosting(data);
        }
    }

    private void startPosting(Intent data){

        Uri filepath = data.getData();
        mStorageRef.child("photos").child(filepath.getLastPathSegment())
                .putFile(filepath).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri downloaduri = taskSnapshot.getDownloadUrl();
                DatabaseReference newpost = mDatabase.push();
                newpost.child("image").setValue(downloaduri.toString());
                newpost.child("uid").setValue(firebaseAuth.getCurrentUser().getUid());
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Post, PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.row, PostViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.setImage(getApplicationContext(), model.getImage());
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static  class PostViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public PostViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setImage(Context context, String image)
        {
            ImageView post_img = (ImageView) mView.findViewById(R.id.card_post_imageview);
            Picasso.with(context).load(image).resize(100,100).centerCrop().into(post_img);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumyaccount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i1 = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(i1);
        return true;
    }
}

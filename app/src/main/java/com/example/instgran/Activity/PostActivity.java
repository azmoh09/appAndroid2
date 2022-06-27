package com.example.instgran.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.example.instgran.Home;
import com.example.instgran.Model.ModelUser;
import com.example.instgran.databinding.ActivityPostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PostActivity extends AppCompatActivity {
    ActivityPostBinding binding;

    String name;
    FirebaseDatabase database;
    ModelUser modelUser;
    DatabaseReference reference;
    String stringFileImageUrl;
    String profile;
    FirebaseUser mUser;
    //    long time, long now, long minResolution, int flags
    long time, now, minResolution;
    int flags = DateUtils.FORMAT_ABBREV_RELATIVE;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm+/*_(&^%$@!ABCDEFGHIGKLMNOPQRSTUVWXYZ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = FirebaseDatabase.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("posts");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();

            binding.tvUserName.setText(name);
        }

        Intent i = getIntent();
        profile = i.getStringExtra("url");
        Picasso.get().load(profile).into(binding.ivMPost);


        if (Locale.getDefault().getLanguage() == "en") {
            setTitle("Create Post");
        } else {
            setTitle("إنشاء منشور");
        }


        binding.btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etCPost.getText().toString().isEmpty()) {
                    if (Locale.getDefault().getLanguage() == "en") {
                        Toast.makeText(PostActivity.this, "please write post in correct place", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PostActivity.this, "يرجى كتابة المنشور في المكان الصحيح", Toast.LENGTH_SHORT).show();

                    }
                } else {


                    modelUser = new ModelUser();
                    String idPost = getRandomString(20);
                    modelUser.setUserImg(profile);
                    modelUser.setIdPost(idPost);
                    modelUser.setUserName(name);
                    modelUser.setContent(binding.etCPost.getText().toString());
//                    long time, long now, long minResolution, int flags


                    SimpleDateFormat format = new SimpleDateFormat("yyyy MM-dd HH:mm:ss");

                    Date now = new Date();
//                    String Date = calTimeAgo(format.format(now));


                    String date = format.format(now);
                    modelUser.setDate(date);


                    DatabaseReference reference = database.getReference("posts")
                            .push();
                    modelUser.setId(reference.getKey());
                    reference.setValue(modelUser);
//                    onBackPressed();
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                    finish();
                }
            }
        });


    }


    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }


}
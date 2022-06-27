package com.example.instgran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.instgran.Activity.PostActivity;
import com.example.instgran.Model.ModelUser;
import com.example.instgran.ViewPager.ViewPagerLoginSignUp;
import com.example.instgran.databinding.ActivityHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    //    private SessionManager session;
    ModelUser modelUser;
    Uri uri;
    FirebaseDatabase database;
    String stringFileImageUrl;

    FirebaseRecyclerAdapter<ModelUser, PostHolder> adapter;

    DatabaseReference reference, LikeRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    ArrayList<ModelUser> data;

    String num = "";
    int i = 0;

    String color = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.like);


//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if (isOnline()) {
//
////                    binding.tvStateConnention.setVisibility(View.VISIBLE);
//                    binding.tvStateConnention.setBackgroundColor(Color.rgb(0,128,0));
//                    binding.tvStateConnention.setText("Connection Available");
//                    // Online
//                    // connection available
//                    //green
//                } else {
////                    binding.tvStateConnention.setVisibility(View.VISIBLE);
//                    binding.tvStateConnention.setBackgroundColor(Color.rgb(220,20,60));
//                    binding.tvStateConnention.setText("No Connection Available");
//                    // Disconnected
//                    //No connection available
//                    //red
//                }
//
//            }
//        };
//        Handler handler = new Handler();
//        handler.postDelayed(runnable,3000);
//        binding.tvStateConnention.setVisibility(View.GONE);

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Do some stuff
//                    }
//                });
//            }
//        };
//        thread.start();


        binding.etPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PostActivity.class);
                i.putExtra("url", stringFileImageUrl);
                startActivity(i);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        LikeRef = FirebaseDatabase.getInstance().getReference().child("likes");


        database = FirebaseDatabase.getInstance();
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("posts")
                .orderByChild("posts/date")
                .limitToLast(50);


        FirebaseRecyclerOptions<ModelUser> options =
                new FirebaseRecyclerOptions.Builder<ModelUser>()
                        .setQuery(query, ModelUser.class)
                        .build();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                    assert modelUser != null;
                    Log.d("TODO", modelUser.getUserImg() + " is is");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<ModelUser, PostHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull ModelUser model) {

                String postKey = getRef(position).getKey();
                try {
                    if (model.getUserName() != null) {
                        holder.tv_username.setText(model.getUserName());
                    }
                    if (model.getContent() != null) {
                        holder.tv_contentPost.setText(model.getContent());
                    }
                    if (model.getDate() != null) {
                        String timeAgo = postTimeAgo(model.getDate());
//                        long time = sdf.parse(model.getDate()).getTime();
//                        long now = System.currentTimeMillis();
                        long timeInMillis = sdf.parse(model.getDate()).getTime();
                        String text = TimeAgo.DateDifference(timeInMillis);
                        Log.d("TODO", text + " time Post");
                        holder.tv_date.setText(text);
//                        holder.tv_date.setText(timeAgo);

                    }
                    if (model.getUserImg() != null) {
                        Picasso.get().load(model.getUserImg()).into(holder.iv_userImgPost);
//                        Log.d("TODO", "123 " + model.getUserImg() + " 321");


                    }


                } catch (Exception e) {
                    Log.d("TODO", e.getLocalizedMessage());
                }

//                SharedPreferences sp = getSharedPreferences("color", MODE_PRIVATE);
//                String color = sp.getString("bg", "a");
//                if (!Objects.equals(color, "a")) {
//                    holder.bt_like.setTextColor(Color.parseColor(color));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
//                    }
//
//                }

//                if(!holder.bt_like.isPressed()){
//                    holder.tv_numberLikes.setVisibility(View.GONE);
//                    holder.iv_iconLike.setVisibility(View.GONE);
//
//                }else{
//                    holder.tv_numberLikes.setVisibility(View.VISIBLE);
//                    holder.iv_iconLike.setVisibility(View.VISIBLE);
//
//
//                }

                holder.countLikes(postKey, mUser.getUid(), LikeRef);


//                holder.bt_like.setTextColor(Color.parseColor("#7A7A7A"));
                holder.bt_like.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        LikeRef.child(postKey).child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    LikeRef.child(postKey).child(mUser.getUid()).removeValue();
//                                    String color = "#7A7A7A";
//                                    holder.bt_like.setTextColor(Color.parseColor(color));
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
//                                    }


                                    notifyDataSetChanged();
                                    mp.start();

                                } else {
                                    LikeRef.child(postKey).child(mUser.getUid()).setValue("like");
//                                    String color = "#4267B2";
//                                    holder.bt_like.setTextColor(Color.parseColor(color));
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                        holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
//                                    }


                                    notifyDataSetChanged();
                                    mp.start();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("TODO", error.getMessage() + "error like ");

                            }
                        });
//                        if(holder.bt_like.isPressed()){
//                            mp.start();
//
//                        }else {
//                            num = holder.tv_numberLikes.getText().toString();
//                            i = Integer.parseInt(num);
//                            holder.tv_numberComments.setText((i -1) +"");
//
//
//                        }

//                        holder.bt_like.setFocusable(true);
//                        holder.bt_like.setFocusableInTouchMode(true);
//                        holder.bt_like.requestFocus();


//                        holder.bt_like.setTextColor(Color.parseColor("#4267B2"));
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            Log.d("TODO", holder.bt_like.getTextColors().getDefaultColor() + " this is color button like");
////                            Log.d("TODO", holder.bt_like.getCompoundDrawableTintList().getDefaultColor() + " this is color 2 button like");
//                            if (((holder.bt_like.getTextColors().getDefaultColor() == -8750470) ||
//                            (holder.bt_like.getCompoundDrawableTintList().getDefaultColor() == -8750470))) {
////                                 color = "#7A7A7A";
//                                 color = "#4267B2";
//                                holder.bt_like.setTextColor(Color.parseColor(color));
//                                holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
//
////                                holder.bt_like.setTextColor(Color.parseColor(color));
////                                holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
////                                SharedPreferences preferences = getSharedPreferences("color", MODE_PRIVATE);
////                                SharedPreferences.Editor editor = preferences.edit();
////                                editor.putString("bg", color);
////                                editor.commit();
////                                mp.start();
////                                num = holder.tv_numberLikes.getText().toString();
////                                Log.d("TODO",holder.bt_like.isClickable() + " is click ");
////                                i = Integer.parseInt(num);
////
////                                String numLikeIn = (i - 1) + "";
////
////                                model.setNumberLike(numLikeIn);
//
//
////                                holder.tv_numberLikes.setText((i +1 )+"");
//
//
//                            } else {
////                                color = "#4267B2";
//                                color = "#7A7A7A";
//                                holder.bt_like.setTextColor(Color.parseColor(color));
//                                holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
////                                holder.bt_like.setTextColor(Color.parseColor(color));
////                                holder.bt_like.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(color)));
////                                SharedPreferences preferences = getSharedPreferences("color", MODE_PRIVATE);
////                                SharedPreferences.Editor editor = preferences.edit();
////                                editor.putString("bg", color);
////                                editor.commit();
////                                mp.start();
////                                num = holder.tv_numberLikes.getText().toString();
////                                i = Integer.parseInt(num);
////                                String numLikeDe = (i - 1) + "";
//
//
////                                holder.tv_numberLikes.setText();
//
//                            }
////
////
//                        }
                    }
                });


            }

            @NonNull
            @Override
            public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

                return new PostHolder(v);
            }
        };

        if (Locale.getDefault().getLanguage() == "en") {
            setTitle("Instgran");
        } else {
            setTitle("انستجران");
        }


        binding.rvListPost.setAdapter(adapter);


    }

    SimpleDateFormat sdf;

    private String postTimeAgo(String date) {
        sdf = new SimpleDateFormat("yyyy MM-dd HH:mm:ss");


        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onStart() {
        super.onStart();


        if (mUser == null) {
            Intent intent = new Intent(getApplicationContext(), ViewPagerLoginSignUp.class);
            startActivity(intent);
            finish();
        } else {


            reference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        stringFileImageUrl = snapshot.child("imageProfile").getValue().toString();
                        Picasso.get().load(stringFileImageUrl).into(binding.ivImg);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            binding.rvListPost.getRecycledViewPool().clear();
            adapter.notifyDataSetChanged();
            adapter.startListening();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_log_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mLogOut) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), ViewPagerLoginSignUp.class);
            startActivity(i);
            finish();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


    }

//    public boolean isOnline() {
//        boolean connected = false;
//        try {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().
//                    getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
//            return connected;
//        } catch (Exception e) {
//            Log.e("TODO", e.getMessage() + " state connection ");
//        }
//        return connected;
//    }


}
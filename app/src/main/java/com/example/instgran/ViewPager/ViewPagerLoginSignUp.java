package com.example.instgran.ViewPager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.instgran.Fragment.FragmentAdapter;
import com.example.instgran.R;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerLoginSignUp extends AppCompatActivity  {
    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_log_in);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tag_layout);

        PagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);









        getSupportActionBar().hide();



    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }
}
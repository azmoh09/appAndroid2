package com.example.instgran.OfflineData;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Ofline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

                FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}

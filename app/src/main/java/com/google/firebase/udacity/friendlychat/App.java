package com.google.firebase.udacity.friendlychat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        Setting persistence to true allows for faster updates when the message list loads.

        But it also means that the push receiver cannot refresh data from the server
        without timing hacks, as a single query will always return cached data.

        If the push receiver needs to be able to access updated data directly then
        keep this value false.
         */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}

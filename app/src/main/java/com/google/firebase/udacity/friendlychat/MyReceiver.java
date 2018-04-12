package com.google.firebase.udacity.friendlychat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mobi.glowworm.utils.debug.Dbug;

import static com.google.firebase.udacity.friendlychat.MainActivity.CHILD_MESSAGES;
import static com.google.firebase.udacity.friendlychat.MainActivity.DEFAULT_MSG_COUNT;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "PUSH";
    private static final int TIMER_CLEANUP_DELAY_MILLIS = 2000;

    private static Query mMessagesQuery;
    private static ValueEventListener eventListener;
    private static Handler cleanupTimer;
    private static Runnable cleanupRunnable = new Runnable() {
        @Override
        public void run() {
            Dbug.logWithTag(TAG, "removing listener");

            mMessagesQuery.removeEventListener(eventListener);
            eventListener = null;
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Dbug.logWithTag(TAG, "!");

        boolean needQuery = mMessagesQuery == null;
        boolean needEventListener = eventListener == null;

        if (needQuery) {
            Dbug.logWithTag(TAG, "creating query");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mMessagesDatabaseReference = database.getReference().child(CHILD_MESSAGES);
            mMessagesQuery = mMessagesDatabaseReference.limitToLast(DEFAULT_MSG_COUNT);
        }

        if (needEventListener) {
            Dbug.logWithTag(TAG, "creating listener");

            eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        FriendlyMessage message = data.getValue(FriendlyMessage.class);
                        if (message != null) {
                            Dbug.logWithTag(TAG, message.getText());
                        }
                    }

                    // restart the timer when new data comes in
                    restartCleanupTimer();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Dbug.logWithTag(TAG, "Whhhoooaaoaoa!!!");
                }
            };
        }

        if (needQuery || needEventListener) {
            Dbug.logWithTag(TAG, "adding listener");

            mMessagesQuery.addValueEventListener(eventListener);
        }

        // restart the timer when a new push message comes in
        restartCleanupTimer();
    }

    private void restartCleanupTimer() {
        if (cleanupTimer == null) {
            Dbug.logWithTag(TAG, "creating timer");

            cleanupTimer = new Handler();
        }

        Dbug.logWithTag(TAG, "starting timer");

        cleanupTimer.removeCallbacks(cleanupRunnable);
        cleanupTimer.postDelayed(cleanupRunnable, TIMER_CLEANUP_DELAY_MILLIS);
    }
}

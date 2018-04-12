package com.google.firebase.udacity.friendlychat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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

    private Query mMessagesQuery;

    @Override
    public void onReceive(Context context, Intent intent) {
        Dbug.logWithTag(TAG, "!");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mMessagesDatabaseReference = database.getReference().child(CHILD_MESSAGES);
        mMessagesQuery = mMessagesDatabaseReference.limitToLast(DEFAULT_MSG_COUNT);
        mMessagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    FriendlyMessage message = data.getValue(FriendlyMessage.class);
                    if (message != null) {
                        Dbug.logWithTag(TAG, message.getText());
                    }
                }
                mMessagesQuery.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Dbug.logWithTag(TAG, "Whhhoooaaoaoa!!!");
                mMessagesQuery.removeEventListener(this);
            }
        });
    }
}

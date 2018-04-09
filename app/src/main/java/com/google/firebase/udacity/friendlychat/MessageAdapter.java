package com.google.firebase.udacity.friendlychat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    @NonNull
    private final List<FriendlyMessage> mMessages;

    public MessageAdapter(@Nullable List<FriendlyMessage> messages) {
        if (messages == null) {
            mMessages = new ArrayList<>();
        } else {
            mMessages = new ArrayList<>(messages);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        FriendlyMessage message = mMessages.get(position);
        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            holder.messageTextView.setVisibility(View.GONE);
            holder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(holder.photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(holder.photoImageView);
        } else {
            holder.messageTextView.setVisibility(View.VISIBLE);
            holder.photoImageView.setVisibility(View.GONE);
            holder.messageTextView.setText(message.getText());
        }
        holder.authorTextView.setText(message.getName());
    }

    public void add(FriendlyMessage message) {
        int newPosition = mMessages.size();
        mMessages.add(message);
        notifyItemInserted(newPosition);
    }

    public void clear() {
        int count = mMessages.size();
        if (count == 0) {
            return;
        }

        mMessages.clear();
        notifyItemRangeRemoved(0, count);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView photoImageView;
        final TextView messageTextView;
        final TextView authorTextView;

        ViewHolder(View view) {
            super(view);
            photoImageView = (ImageView) view.findViewById(R.id.photoImageView);
            messageTextView = (TextView) view.findViewById(R.id.messageTextView);
            authorTextView = (TextView) view.findViewById(R.id.nameTextView);
        }
    }
}

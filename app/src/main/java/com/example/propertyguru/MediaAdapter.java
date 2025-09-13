package com.example.propertyguru;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_PHOTO = 0;
    private static final int TYPE_VIDEO = 1;

    private final List<Uri> mediaList;
    private final OnMediaDeleteListener deleteListener;

    public interface OnMediaDeleteListener {
        void onDelete(Uri uri);
    }

    public MediaAdapter(List<Uri> mediaList, OnMediaDeleteListener deleteListener) {
        this.mediaList = mediaList;
        this.deleteListener = deleteListener;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mediaList.get(position).toString();
        return type.contains("video") ? TYPE_VIDEO : TYPE_PHOTO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIDEO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
            return new VideoHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
            return new PhotoHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Uri uri = mediaList.get(position);
        if (holder instanceof PhotoHolder) {
            PhotoHolder photoHolder = (PhotoHolder) holder;
            Glide.with(holder.itemView.getContext()).load(uri).into(photoHolder.imageView);

            photoHolder.deleteBtn.setOnClickListener(v -> {
                if (deleteListener != null) deleteListener.onDelete(uri);
            });
        } else if (holder instanceof VideoHolder) {
            VideoHolder videoHolder = (VideoHolder) holder;
            videoHolder.videoView.setVideoURI(uri);
            videoHolder.videoView.seekTo(100);

            videoHolder.deleteBtn.setOnClickListener(v -> {
                if (deleteListener != null) deleteListener.onDelete(uri);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    static class PhotoHolder extends RecyclerView.ViewHolder {
        ImageView imageView, deleteBtn;
        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagePreview);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
        }
    }

    static class VideoHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView deleteBtn;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoPreview);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
        }
    }
}
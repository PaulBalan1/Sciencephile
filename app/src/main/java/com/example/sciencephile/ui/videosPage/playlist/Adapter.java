package com.example.sciencephile.ui.videosPage.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sciencephile.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.PreviewViewHolder> {

    private ArrayList<ThumbnailItem> previewList;
    private Fragment fragment;

    public static class PreviewViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public PreviewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnailView);
            textView = itemView.findViewById(R.id.titleView);
        }
    }

    public Adapter(ArrayList<ThumbnailItem> previewList, Fragment fragment){
        this.previewList = previewList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        PreviewViewHolder previewViewHolder = new PreviewViewHolder(v);
        return previewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewViewHolder holder, int position) {
        ThumbnailItem currentItem = previewList.get(position);
        holder.textView.setText(currentItem.getTitle());
        Glide.with(fragment).load(currentItem.getThumbnailUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return previewList.size();
    }
}

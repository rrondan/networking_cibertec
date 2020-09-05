package com.rrondan.clasenetworking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rrondan.clasenetworking.R;
import com.rrondan.clasenetworking.models.Post;
import com.rrondan.clasenetworking.utils.OnAdapterItemClick;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{

    private List<Post> postList;
    private OnAdapterItemClick onAdapterItemClick;

    public PostAdapter(List<Post> postList, OnAdapterItemClick onAdapterItemClick) {
        this.postList = postList;
        this.onAdapterItemClick = onAdapterItemClick;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewBody.setText(post.getText());
        //holder.itemView.setOnClickListener(v -> onAdapterItemClick.onClick(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdapterItemClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

class PostViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewTitle;
    public TextView textViewBody;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewBody = itemView.findViewById(R.id.textViewBody);
    }
}
package com.siddhu.capp.ui.circular;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siddhu.capp.R;

import java.util.List;


/**
 * Created by baji_g on 5/31/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {


    private List<Comments> commentsList;
    private Context context;
    public CommentsAdapter(List<Comments> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }



    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.siddhu_row_layout,parent,false);
        CommentsViewHolder commentsViewHolder = new CommentsViewHolder(view);
        return commentsViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.CommentsViewHolder holder, int position) {
        holder.postid.setText(Integer.toString(commentsList.get(position).getPostId()));
        holder.id.setText(Integer.toString(commentsList.get(position).getId()));
        holder.name.setText(commentsList.get(position).getName());
        holder.email.setText(commentsList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        private TextView postid;
        private TextView id;
        private TextView name;
        private TextView email;
        public CommentsViewHolder(View itemView) {
            super(itemView);
            postid = (TextView) itemView.findViewById(R.id.member_name);
            email = (TextView) itemView.findViewById(R.id.member_email);
            name = (TextView) itemView.findViewById(R.id.date);
            id = (TextView) itemView.findViewById(R.id.date1);
        }
    }
}

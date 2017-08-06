package com.siddhu.capp.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.models.Circular;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.ui.activities.DashBoardActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by baji_g on 5/30/2017.
 */

public class CircularsAdapter extends RecyclerView.Adapter<CircularsAdapter.CircularViewHolder> {


    private List<CircularsResponse> mCircularsList;
    private Context context;
    private OnCircularItemClickedListener listener;
    private DashBoardActivity dashBoardActivity;



    public CircularsAdapter(List<CircularsResponse> mCirculars, Context context, OnCircularItemClickedListener listener) {
        this.mCircularsList = mCirculars;
        this.context = context;
        this.listener = listener;
        dashBoardActivity = (DashBoardActivity)context;
    }

    @Override
    public CircularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circular_row_layout,parent,false);
        CircularViewHolder circularViewHolder = new CircularViewHolder(view);
        return circularViewHolder;
    }

    @Override
    public void onBindViewHolder(CircularViewHolder holder, final int position) {
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
       // holder.cardView.setBackgroundColor(randomAndroidColor);

        if(!dashBoardActivity.is_in_action_mode){
            holder.checkBox.setVisibility(View.GONE);
        }else{
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
        holder.name_tv.setText(mCircularsList.get(position).getTitle());
        holder.email_tv.setText(mCircularsList.get(position).getSubject());
        if(mCircularsList.get(position).getImage().length() > 8) {
            Glide.with(dashBoardActivity).load(mCircularsList.get(position).getImage())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.pic_iv);
        }else{
            Glide.with(context).load(R.drawable.student_logo)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.pic_iv);
        }
       // holder.pic_iv.setImageResource(mCircularsList.get(position).get());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(mCircularsList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mCircularsList == null ? 0 :  mCircularsList.size();
    }

    public class CircularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private TextView name_tv;
        private TextView email_tv;
        private ImageView pic_iv;
        private CheckBox checkBox;

        public CircularViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name_tv = (TextView) itemView.findViewById(R.id.member_name);
            email_tv = (TextView) itemView.findViewById(R.id.member_email);
            pic_iv = (ImageView) itemView.findViewById(R.id.profile_pic);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            cardView.setOnLongClickListener(dashBoardActivity);
            checkBox.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            dashBoardActivity.prepareselection(v,getAdapterPosition());
        }
    }

      public interface OnCircularItemClickedListener{
        void onItemClicked(CircularsResponse circular);
    }
    public void updateAdapter(ArrayList<Circular> list)
    {
        for(Circular contact:list)
        {
            mCircularsList.remove(contact);
        }
        notifyDataSetChanged();
    }
}

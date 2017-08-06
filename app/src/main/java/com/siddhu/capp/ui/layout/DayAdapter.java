package com.siddhu.capp.ui.layout;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhu.capp.R;


/**
 * Created by dhiman_da on 09/08/16.
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private int[] mDays;
    private DateSelectionListener mListener;

    public DayAdapter(int[] days, DateSelectionListener listener) {
        mDays = days;
        mListener = listener;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.adapter_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, final int position) {
        holder.mTextView.setText(String.valueOf(mDays[position]));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDaySelected(mDays[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDays == null ? 0 : mDays.length;
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTextView;

        public DayViewHolder(View itemView) {
            super(itemView);

            mTextView = (AppCompatTextView) itemView.findViewById(R.id.adapter_day_text_view);
        }
    }
}

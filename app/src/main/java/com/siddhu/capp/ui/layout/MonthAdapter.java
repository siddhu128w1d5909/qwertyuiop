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
public class MonthAdapter  extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> {
    private Month[] mMonths;
    private DateSelectionListener mListener;

    public MonthAdapter(final Month[] months, final DateSelectionListener listener) {
        mMonths = months;
        mListener = listener;
    }

    @Override
    public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.adapter_months, parent, false);
        return new MonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonthViewHolder holder, final int position) {
        holder.mTextView.setText(mMonths[position].getName());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onMonthSelected(mMonths[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMonths == null ? 0 : mMonths.length;
    }

    public static class MonthViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTextView;

        public MonthViewHolder(View itemView) {
            super(itemView);

            mTextView = (AppCompatTextView) itemView.findViewById(R.id.adapter_month_text_view);
        }
    }
}

package com.siddhu.capp.ui.layout;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhu.capp.R;


public class YearAdapter  extends RecyclerView.Adapter<YearAdapter.YearViewHolder> {
    private int[] mYears;
    private DateSelectionListener mListener;

    public YearAdapter(int[] years, final DateSelectionListener listener) {
        mYears = years;
        mListener = listener;
    }

    @Override
    public YearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.adapter_year, parent, false);
        return new YearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YearViewHolder holder, final int position) {
        holder.mTextView.setText(String.valueOf(mYears[position]));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onYearSelected(mYears[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mYears == null ? 0 : mYears.length;
    }

    public static class YearViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView mTextView;

        public YearViewHolder(View itemView) {
            super(itemView);

            mTextView = (AppCompatTextView) itemView.findViewById(R.id.adapter_year_text_view);
        }
    }
}

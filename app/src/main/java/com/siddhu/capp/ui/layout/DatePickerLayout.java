package com.siddhu.capp.ui.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.siddhu.capp.R;
import com.siddhu.capp.utils.Logger;
import com.siddhu.capp.utils.Utility;

import java.util.Calendar;

/**
 * Created by dhiman_da on 09/08/16.
 */
public class DatePickerLayout extends LinearLayout implements DateSelectionListener {
    private static int MONTHDURATION = 10;
    private static final String TAG = DatePickerLayout.class.getSimpleName();
    private int mCurrentMonth;
    private int mCurrentDay;
    private int mCurrentYear;

    private int mCurrentSelecedMonth;
    private int mCurrentSelectedDay;
    private int mCurrentSelectedYear;

    private RecyclerView mMonthRecyclerView;
    private TextView mMonthTextView;

    private RecyclerView mDayRecylerView;
    private TextView mDayTextView;

    private RecyclerView mYearRecyclerView;
    private TextView mYearTextView;

    private Month[] mMonths = new Month[12];

    private DateSelectedListener mDateSelectedListener;

    public DatePickerLayout(Context context) {
        super(context);
    }

    public DatePickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DatePickerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setupMonthDayYear();
        inflateLayout();
        setupViews();
    }

    private void setupMonthDayYear() {
        final Calendar calendar = Calendar.getInstance();

        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentYear = calendar.get(Calendar.YEAR);
    }

    private void inflateLayout() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.include_date_picker, this, false);
        removeAllViews();
        addView(view);

        mMonthRecyclerView = (RecyclerView) view.findViewById(R.id.month_recycler_view);
        mMonthRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mMonthTextView = (TextView) view.findViewById(R.id.month_text_view);
        mMonthTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setMonthSelectionMode();
            }
        });

        mDayRecylerView = (RecyclerView) view.findViewById(R.id.day_recycler_view);
        mDayRecylerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mDayTextView = (TextView) view.findViewById(R.id.day_text_view);
        mDayTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setDaySelectionMode();
            }
        });

        mYearRecyclerView = (RecyclerView) view.findViewById(R.id.year_recycler_view);
        mYearRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mYearTextView = (TextView) view.findViewById(R.id.year_text_view);
        mYearTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setYearSelectionMode();
            }
        });
    }

    private void setupViews() {
        setMonthSelectionMode();
    }


    private void setMonthSelectionMode() {
        mMonthTextView.setVisibility(GONE);

        mMonthRecyclerView.setAdapter(new MonthAdapter(getMonths(), this));
        mMonthRecyclerView.setVisibility(VISIBLE);

        mDayTextView.setVisibility(GONE);
        mDayRecylerView.setVisibility(GONE);

        mYearTextView.setVisibility(GONE);
        mYearRecyclerView.setVisibility(GONE);

        if (mDateSelectedListener != null) {
            mDateSelectedListener.onDateDeSelected();
        }
    }


    private void setDaySelectionMode() {
        mMonthTextView.setVisibility(GONE);
        mMonthRecyclerView.setVisibility(GONE);

        if (mDateSelectedListener != null)
            //mDateSelectedListener.onDateSelected(Utility.theMonth(mCurrentSelecedMonth));

        mDayTextView.setVisibility(GONE);
        mDayRecylerView.setAdapter(new DayAdapter(getDays(), this));
        mDayRecylerView.setVisibility(VISIBLE);

        mYearTextView.setVisibility(GONE);
        mYearRecyclerView.setVisibility(GONE);
        if (mDateSelectedListener != null) {
            mDateSelectedListener.onDateDeSelected();
        }
    }

    private void setYearSelectionMode() {
        mMonthTextView.setVisibility(VISIBLE);
        mMonthRecyclerView.setVisibility(GONE);

        mDayTextView.setVisibility(VISIBLE);
        mDayRecylerView.setVisibility(GONE);

        mYearTextView.setVisibility(GONE);
        mYearRecyclerView.setAdapter(new YearAdapter(getYears(), this));
        mYearRecyclerView.setVisibility(VISIBLE);
        if (mDateSelectedListener != null) {
            mDateSelectedListener.onDateDeSelected();
        }
    }

    private Month[] getMonths() {
        for (int i = 0; i < 12; i++) {
            mMonths[i] = new Month(i, getMonthsFromCurrentMonth(i));
        }

        return mMonths;
    }

    private String getMonthsFromCurrentMonth(int monthCode) {
        switch (monthCode) {
            case 0:
                return "Jan";

            case 1:
                return "Feb";

            case 2:
                return "Mar";

            case 3:
                return "Apr";

            case 4:
                return "May";

            case 5:
                return "Jun";

            case 6:
                return "Jul";

            case 7:
                return "Aug";

            case 8:
                return "Sep";

            case 9:
                return "Oct";

            case 10:
                return "Nov";

            case 11:
                return "Dec";

            default:
                return "";
        }
    }

    private int getNumberOfDaysInMonth() {
        switch (mCurrentSelecedMonth) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return getDaysInFebMonth();
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;

            default:
                return -1;
        }
    }

    private int[] getDays() {
        if (mCurrentSelecedMonth == mCurrentMonth) {
            final int[] days = new int[getNumberOfDaysInMonth() - mCurrentDay];

            int currentDay = mCurrentDay + 1;
            for (int i = 0; i < getNumberOfDaysInMonth() - mCurrentDay; i++) {
                days[i] = currentDay;
                currentDay++;
            }

            return days;
        } else {
            final int[] days = new int[getNumberOfDaysInMonth()];

            for (int i = 0; i < getNumberOfDaysInMonth(); i++) {
                days[i] = i + 1;
            }

            return days;
        }
    }

    public int getDiffYears() {
        Calendar calendarOne = Calendar.getInstance();
        calendarOne.set(Calendar.MONTH, mCurrentSelecedMonth);

        Calendar calendarTwo = Calendar.getInstance();
        calendarOne.set(Calendar.MONTH, mCurrentSelecedMonth + MONTHDURATION);

        return calendarTwo.get(Calendar.YEAR) - calendarOne.get(Calendar.YEAR);
    }

    private int[] getYears() {
        if (mCurrentSelecedMonth < mCurrentMonth) {
            final int[] years = new int[1];
            years[0] = mCurrentYear + 1;
            return years;
        } else {
            final int[] years = new int[1];
            years[0] = mCurrentYear;
            return years;
        }
    }

    @Override
    public void onMonthSelected(Month month) {
        mCurrentSelecedMonth = month.getCode();
        mMonthTextView.setText(month.getName());
        setDaySelectionMode();
    }

    @Override
    public void onDaySelected(int day) {
        mCurrentSelectedDay = day;
        mDayTextView.setText(String.valueOf(mCurrentSelectedDay));
        setYearSelectionMode();
        if (getYears().length == 1) {
            onYearSelected(getYears()[0]);
        }
    }

    @Override
    public void onYearSelected(int year) {
        mCurrentSelectedYear = year;
        //mYearTextView.setText(String.valueOf(mCurrentSelectedYear));
        //mYearTextView.setVisibility(VISIBLE);
        mYearRecyclerView.setVisibility(GONE);

       // String newDate = Utility.theMonth(mCurrentSelecedMonth) +" " +String.valueOf(mCurrentSelectedDay) + "," + String.valueOf(mCurrentSelectedYear);
        if (mDateSelectedListener != null)
           // mDateSelectedListener.onDateSelected(newDate);

        mMonthTextView.setVisibility(GONE);
        mDayTextView.setVisibility(GONE);
        mYearTextView.setVisibility(GONE);

        if (mDateSelectedListener != null) {
            mDateSelectedListener.onDateSelected(mCurrentSelectedDay,
                    mCurrentSelecedMonth, mCurrentSelectedYear);
        }
    }

    public void setDateSelectedListner(DateSelectedListener listner) {
        mDateSelectedListener = listner;
    }

    public void setDefaultDate(Calendar cl) {
        try {
            onMonthSelected(new Month(cl.get(Calendar.MONTH), getMonthsFromCurrentMonth(cl.get(Calendar.MONTH))));
            onDaySelected(cl.get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            Logger.log(TAG, e);
        }

    }

    public int getDaysInFebMonth() {
        if ((mCurrentYear + (mCurrentMonth > 1 ? 1 : 0)) % 4 == 0) {
            return 29;
        } else {
            return 28;
        }
    }

    public void setUpDafaultViews(){
        setupViews();
    }
    public interface DateSelectedListener {
        void onDateSelected(int dayOfMonth, int monthIndex, int year);

        void onDateDeSelected();

        void onDateSelected(String date);
    }
}

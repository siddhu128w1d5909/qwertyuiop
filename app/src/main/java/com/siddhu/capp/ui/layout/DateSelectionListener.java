package com.siddhu.capp.ui.layout;

/**
 * Created by dhiman_da on 09/08/16.
 */
public interface DateSelectionListener {
    void onMonthSelected(Month month);
    void onDaySelected(int day);
    void onYearSelected(int year);
}

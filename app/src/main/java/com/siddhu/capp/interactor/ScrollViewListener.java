package com.siddhu.capp.interactor;


import com.siddhu.capp.ui.views.ObservableScrollView;

/**
 * Created by baji_g on 1/6/2017.
 */

public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}

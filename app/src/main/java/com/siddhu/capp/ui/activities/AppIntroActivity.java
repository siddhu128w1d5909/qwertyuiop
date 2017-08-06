package com.siddhu.capp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.siddhu.capp.R;
import com.siddhu.capp.ui.fragments.Tutorial0Fragment;
import com.siddhu.capp.ui.fragments.Tutorial1Fragment;
import com.siddhu.capp.ui.fragments.Tutorial2Fragment;
import com.siddhu.capp.ui.fragments.Tutorial3Fragment;
import com.siddhu.capp.utils.PreferenceUtil;


/**
 * Created by baji_g on 4/5/2017.
 */

public class AppIntroActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NUM_PAGES = 4;
    private LinearLayout mCirclesLayout;
    private AppCompatButton mDoneBtn,mnextBtn,mSkipButton;
    private ViewPager mTutorialViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_tutorial);
        mDoneBtn = (AppCompatButton) findViewById(R.id.done_btn);
        mnextBtn = (AppCompatButton) findViewById(R.id.next_btn);
        mSkipButton = (AppCompatButton) findViewById(R.id.skip_btn);

        mCirclesLayout = (LinearLayout) findViewById(R.id.tutorial_page_circles);

        mTutorialViewPager = (ViewPager) findViewById(R.id.tutorial_view_pager);
        mTutorialViewPager.setOffscreenPageLimit(1);
        final TutorialPageAdapter tutorialPageAdapter = new TutorialPageAdapter(getSupportFragmentManager());
        mTutorialViewPager.setAdapter(tutorialPageAdapter);


        mDoneBtn.setOnClickListener(this);
        mnextBtn.setOnClickListener(this);
        mSkipButton.setOnClickListener(this);

        buildCircles();
        mTutorialViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == NUM_PAGES - 1) {
                    mnextBtn.setVisibility(View.GONE);
                    mDoneBtn.setVisibility(View.VISIBLE);
                    mSkipButton.setVisibility(View.GONE);

                } else {
                    mnextBtn.setVisibility(View.VISIBLE);
                    mDoneBtn.setVisibility(View.GONE);
                    mSkipButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.skip_btn) {
            PreferenceUtil.getInstance(getApplicationContext()).saveBooleanParam(PreferenceUtil.IS_FIRST_TIME, true);
            final Intent intent = new Intent(this, UserLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else if (v.getId() == R.id.done_btn) {
            PreferenceUtil.getInstance(getApplicationContext()).saveBooleanParam(PreferenceUtil.IS_FIRST_TIME, true);
            final Intent intent = new Intent(this, UserLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();

        } else if (v.getId() == R.id.next_btn) {
            if (mTutorialViewPager.getCurrentItem() <= NUM_PAGES - 1) {
                mTutorialViewPager.setCurrentItem(mTutorialViewPager.getCurrentItem() + 1);
            }
        }
    }

    /**
     * Draws circles (one for each view). The current view position is filled and
     * others are only stroked.
     */
    private void buildCircles() {
        final float scale = getResources().getDisplayMetrics().density;
        final int padding = (int) (5 * scale + 0.5f);

        for (int i = 0; i <= NUM_PAGES - 1; i++) {
            final ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.disable_circle);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            mCirclesLayout.addView(circle);
        }
        setIndicator(0);
    }

    /**
     * <p>Set the current page of both the ViewPager and indicator.</p>
     * <p/>
     * <p>This <strong>must</strong> be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).</p>
     *
     * @param index
     */
    private void setIndicator(int index) {
        if (index < NUM_PAGES) {
            for (int i = 0; i <= NUM_PAGES - 1; i++) {
                final ImageView circle = (ImageView) mCirclesLayout.getChildAt(i);
                if (i == index) {
                    circle.setImageResource(R.drawable.enabled__circle);
                } else {
                    circle.setImageResource(R.drawable.disable_circle);
                }
            }
        }
    }

    private class TutorialPageAdapter extends FragmentStatePagerAdapter {


        /**
         * Instantiates a new Tutorial page adapter.
         *
         * @param fm the fm
         */
        public TutorialPageAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            Fragment tutorialFragment = null;
            switch (position) {
                case 0:
                    tutorialFragment = new Tutorial0Fragment();
                    break;
                case 1:
                    tutorialFragment = new Tutorial1Fragment();
                    break;
                case 2:
                    tutorialFragment = new Tutorial2Fragment();
                    break;
                case 3:
                    tutorialFragment = new Tutorial3Fragment();
                    break;
            }
            return tutorialFragment;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}

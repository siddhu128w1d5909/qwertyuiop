package com.siddhu.capp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.siddhu.capp.R;
import com.siddhu.capp.utils.PreferenceUtil;


/**
 * Created by baji_g on 4/6/2017.
 */

public class SplashActivity extends Activity {

    private static final long SPLASH_SCREEN_TIMER = 3000;
    private final Handler handler = new Handler();

    /**
     * When the application is launched, it checks in the preference whether there exists any already loggedIn user
     * in this device or not. If exists and session is not expired for that user, then our app will navigate to
     * DashBoard screen. Else, Login screen will be displayed. If the application is launched for the first time
     * in this device then tutorial screen will be shown.
     */
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            final Intent intent = new Intent(MplSplashScreenActivity.this, MplThankyouActivity.class);
//            startActivity(intent);
            if (PreferenceUtil.getInstance(getApplicationContext()).getBooleanParam(PreferenceUtil.IS_FIRST_TIME)) {
                final Intent intent = new Intent(SplashActivity.this, LoginTypeActivity.class);
                startActivity(intent);
            } else {
                final Intent intent = new Intent(SplashActivity.this, AppIntroActivity.class);
                startActivity(intent);
            }
            finish();
        }
    };

    /**
     * onCreate(Bundle) is where you initialize your activity. Most importantly, here you will usually
     * call setContentView(int) with a layout resource defining your UI, and using findViewById(int) to
     * retrieve the widgets in that UI that you need to interact with programmatically.
     * <p/>
     * When the application is launched for the first time this screen will be displayed.
     * The application will display the splash screen and build version also.
     *
     * @param savedInstanceState Constructs a new, empty Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler.postDelayed(runnable, SPLASH_SCREEN_TIMER);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     */
    @Override
    protected void onResume() {
        handler.postDelayed(runnable, SPLASH_SCREEN_TIMER);
        super.onResume();

    }
}
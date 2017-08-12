 package com.siddhu.capp.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.siddhu.capp.R;
import com.siddhu.capp.app.CollegeApplicationClass;
import com.siddhu.capp.models.Circular;
import com.siddhu.capp.ui.adapters.CircularAdapter;
import com.siddhu.capp.ui.fragments.BusyFreeFragment;
import com.siddhu.capp.ui.fragments.ChangePwdFragment;
//import com.siddhu.capp.ui.fragments.CircularsPostFragment;
import com.siddhu.capp.ui.fragments.CircularsPostFragment;
import com.siddhu.capp.ui.fragments.ClassEngagedDetailsFragment;
import com.siddhu.capp.ui.fragments.DropBoxFragment;
import com.siddhu.capp.ui.fragments.ExaminationFragment;
import com.siddhu.capp.ui.fragments.LeaveFragment;
import com.siddhu.capp.ui.fragments.MaterialsFragment;
import com.siddhu.capp.ui.fragments.ProjectFragment;
import com.siddhu.capp.ui.fragments.ScheduleFragment;
import com.siddhu.capp.ui.fragments.StaffAttendanceFragment;
import com.siddhu.capp.ui.fragments.StudentAttendenceFragment;
import com.siddhu.capp.ui.fragments.StudentBookSearchFragment;
import com.siddhu.capp.ui.fragments.StudentHomeFragment;
import com.siddhu.capp.ui.fragments.StudentMarksFragment;
import com.siddhu.capp.ui.fragments.StudentSocialShareFragment;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.AppConstants;
import com.siddhu.capp.utils.PreferenceUtil;
import com.siddhu.capp.utils.Utility;

import java.util.ArrayList;

import static com.siddhu.capp.ui.fragments.StudentHomeFragment.adapter;
import static com.siddhu.capp.ui.fragments.StudentHomeFragment.circularList;


 public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnLongClickListener {

    String mLoginUser;
    NavigationView navigationView;
    public boolean is_in_action_mode = false;
    ArrayList<Circular> selectionList = new ArrayList<Circular>();
    int counter = 0;
    Toolbar toolbar;
    private TextView counterTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash_board);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        counterTextView = (TextView) findViewById(R.id.cnt_text);
        counterTextView.setVisibility(View.GONE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mLoginUser = PreferenceUtil.getInstance(this).getStringParam(AppConstants.LOGIN_USER_TYPE);
        //Toast.makeText(this,User,Toast.LENGTH_LONG).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
       // Menu menu = navigationView.getMenu();
        mLoginUser = PreferenceUtil.getInstance(this).getStringParam(AppConstants.LOGIN_USER_TYPE);
        //Toast.makeText(this,User,Toast.LENGTH_LONG).show();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(CollegeApplicationClass.getInstance().getmLoginUserType().equalsIgnoreCase(AppConstants.HOD)) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_hod_dash_board_drawer);
        }else if(CollegeApplicationClass.getInstance().getmLoginUserType().equalsIgnoreCase(AppConstants.STUDENT)){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_student_dash_board_drawer);
        }else if(CollegeApplicationClass.getInstance().getmLoginUserType().equalsIgnoreCase(AppConstants.STAFF)){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.staff_dash_board_drawer);
        }

        if (navigationView != null) {
            Menu menu = navigationView.getMenu();

            /*if(PreferenceUtil.getInstance(this).getStringParam(AppConstants.LOGIN_USER_TYPE).equalsIgnoreCase(AppConstants.HOD)){
                menu.findItem(R.id.nav_home).setVisible(false);
                menu.findItem(R.id.hod_nav_home).setVisible(true);
            }
            else{
                menu.findItem(R.id.hod_nav_home).setVisible(false);
            }*/

            navigationView.setNavigationItemSelectedListener(this);
        }
        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);
    }



    @Override
    public void onBackPressed() {

        if(is_in_action_mode)
        {
            clearActionM();
            adapter.notifyDataSetChanged();
        }
        else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                String message = "Are you sure you want to exit?";
                Utility.showAlertMessage(this,message,"Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_dash_board, menu);


        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;


    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new StudentHomeFragment();
                break;
            case R.id.nav_studentattendence:
                fragment = new StudentAttendenceFragment();
                break;
            case R.id.nav_staffattendence:
                fragment = new StaffAttendanceFragment();
                break;
            case R.id.nav_schedule:
                fragment = new ScheduleFragment();
                break;
            case R.id.nav_classengaged:
                fragment = new CircularsPostFragment();
                break;
            case R.id.nav_busy:
                fragment = new BusyFreeFragment();
                break;
            case R.id.nav_dropbox:
                //fragment = new DropBoxFragment();
                fragment = new StudentAttendenceFragment();
                break;
            case R.id.nav_results:
                fragment = new ExaminationFragment();
                break;
            case R.id.nav_projects:
                fragment = new ProjectFragment();
                break;
            case R.id.nav_materials:
                fragment = new MaterialsFragment();
                break;
            case R.id.nav_leave:
                fragment = new LeaveFragment();
                break;
            case R.id.nav_work:
                fragment = new StudentMarksFragment();
                break;
            case R.id.nav_checklib:
                fragment = new StudentBookSearchFragment();
                break;
            case R.id.nav_changpwd:
                fragment = new ChangePwdFragment();
                break;
            case R.id.nav_logout:
                String message = "Are you sure you want to Logout?";
                Utility.showAlertMessage(this,message,"Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(DashBoardActivity.this,StudentActivity.class));
                        finish();
                    }
                });

                break;
            case R.id.nav_share:
                fragment = new StudentSocialShareFragment();
                break;

           // case R.id.nav_post_circular:
           //     fragment = new CircularsPostFragment();
           //     break;

            case R.id.nav_send:
                fragment = new StudentSocialShareFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onLongClick(View v) {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_action_mode);
        is_in_action_mode = true;
        counterTextView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        // home button on action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        if(item.getItemId()==R.id.it_delete)
        {

            CircularAdapter recyclerAdapter = (CircularAdapter) adapter;
            recyclerAdapter.updateAdapter(selectionList);
            clearActionM();
        }
        else if(item.getItemId() == android.R.id.home)
        {
            clearActionM();
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    public void clearActionM()
    {
        is_in_action_mode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.student_dash_board);
        //remove home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        counterTextView.setVisibility(View.GONE);
        counterTextView.setText("0 item selected");
        counter = 0;
        selectionList.clear();
    }
    public void prepareselection(View view, int adapterPosition) {

        //change view to checkbox
        if(((CheckBox)view).isChecked())
        {
            selectionList.add(circularList.get(adapterPosition));
            counter++;
            updateCnt(counter);
        }
        else {
            selectionList.remove(circularList.get(adapterPosition));
            counter--;
            updateCnt(counter);
        }
    }
    public void updateCnt(int counter)
    {
        if(counter==0)
        {
            counterTextView.setText("0 item selected");
        }
        else {
            counterTextView.setText(counter + " item selected");
        }
    }

    public static Intent getDashBoardIntent(Context context) {
        Intent lIntent = new Intent(context, DashBoardActivity.class);
        lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(lIntent);
        return lIntent;
    }
}

package com.siddhu.capp.ui.fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.models.Circular;
import com.siddhu.capp.ui.activities.CircularDetailsActivity;
import com.siddhu.capp.ui.adapters.CircularAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.siddhu.capp.ui.fragments.StudentHomeFragment.circularList;

public class StudentAttendenceFragment extends BaseFragment implements AdapterView.OnItemSelectedListener,CircularAdapter.OnCircularItemClickedListener {

    View view;
    private Spinner mBrchSpinner,mSemesterSpinner,myearSpinner;
    private ArrayAdapter<CharSequence> mBranchAdapter,mSemesterAdapter;
    private RecyclerView mAttendenceRecycler;
    LinearLayoutManager layoutManager;
    private ArrayAdapter<CharSequence> mYearAdapter;
    private String[] names;
    private TypedArray profile_pics;
    private String[] emails;
    public  List<Circular> circularList = new ArrayList<>();
    private CircularAdapter circularAdapter;
    private String mBranchSelectedValue,mSemesterSelectedValue,myearSelectedValue;
    AppCompatButton mSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_attendence,container,false);
        initUI(view);
        return view;

    }

    private void initUI(View view) {

        mBrchSpinner = (Spinner) view.findViewById(R.id.stu_att_branch_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        mBranchAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.branch_array, R.layout.custom_spinner);
// Specify the layout to use when the list of choices appears
        mBranchAdapter.setDropDownViewResource(R.layout.custom_spinner);
// Apply the adapter to the spinner
        mBrchSpinner.setAdapter(mBranchAdapter);

        mBrchSpinner.setOnItemSelectedListener(this);

        mSemesterSpinner = (Spinner) view.findViewById(R.id.stu_att_semester_spinner);
        mSemesterAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.sem_array, R.layout.custom_spinner);
        mSemesterAdapter.setDropDownViewResource(R.layout.custom_spinner);
        mSemesterSpinner.setAdapter(mSemesterAdapter);
        mSemesterSpinner.setOnItemSelectedListener(this);

        myearSpinner = (Spinner) view.findViewById(R.id.stu_att_year_spinner);
        mYearAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.year_array, R.layout.custom_spinner);
        mYearAdapter.setDropDownViewResource(R.layout.custom_spinner);
        myearSpinner.setAdapter(mYearAdapter);
        myearSpinner.setOnItemSelectedListener(this);

        mSubmit = (AppCompatButton)view.findViewById(R.id.submit_btn) ;
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAttendenceRecycler.setAdapter(circularAdapter);
            }
        });


        names = getActivity().getResources().getStringArray(R.array.names);
        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
        emails = getResources().getStringArray(R.array.email);

        for (int i = 0; i < names.length; i++) {
            Circular member = new Circular(names[i], emails[i], profile_pics.getResourceId(i, -1));
            circularList.add(member);
        }

        mAttendenceRecycler = (RecyclerView)view.findViewById(R.id.list_attendence);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAttendenceRecycler.setLayoutManager(layoutManager);
        mAttendenceRecycler.setHasFixedSize(true);
        circularAdapter = new CircularAdapter(circularList,getActivity(), this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Attendence");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected=parent.getItemAtPosition(position).toString();

        if(id == R.id.stu_att_branch_spinner)
        {
            mBranchSelectedValue = sSelected;
            Toast.makeText(getActivity(),mBranchSelectedValue,Toast.LENGTH_SHORT).show();
        }else if(id == R.id.stu_att_semester_spinner)
        {
            mSemesterSelectedValue = sSelected;
            Toast.makeText(getActivity(),mSemesterSelectedValue,Toast.LENGTH_SHORT).show();
        }else if(id == R.id.stu_att_year_spinner)
        {
            myearSelectedValue = sSelected;
            Toast.makeText(getActivity(),myearSelectedValue,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onItemClicked(Circular circular) {
        Toast.makeText(getActivity(), circular.getName()+circular.getEmail(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(),CircularDetailsActivity.class);
        intent.putExtra("CircularModel",circular);
        startActivity(intent);
    }
}


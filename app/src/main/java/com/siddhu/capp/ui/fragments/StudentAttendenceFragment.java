package com.siddhu.capp.ui.fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.models.Circular;
import com.siddhu.capp.ui.activities.CircularDetailsActivity;
import com.siddhu.capp.ui.adapters.CircularAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by baji_g on 4/3/2017.
 */
public class StudentAttendenceFragment extends BaseFragment implements CircularAdapter.OnCircularItemClickedListener {
    View view;
    Spinner spinner;
    RecyclerView recyclerView;
    TextView mNoBrachSelected;
    private String[] names;
    private TypedArray profile_pics;
    private String[] emails;
    public static List<Circular> circularList;
    public static CircularAdapter adapter;
    LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_attendence, container, false);
         spinner = (Spinner) view.findViewById(R.id.spinner);
        recyclerView = (RecyclerView)view.findViewById(R.id.faculty_att_recycler);
        mNoBrachSelected = (TextView)view.findViewById(R.id.no_branch_text);

        names = getActivity().getResources().getStringArray(R.array.names);
        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
        emails = getResources().getStringArray(R.array.email);

        circularList = new ArrayList<Circular>();
        for (int i = 0; i < names.length; i++) {
            Circular member = new Circular(names[i], emails[i], profile_pics.getResourceId(i, -1));
            circularList.add(member);
        }
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CircularAdapter(circularList, getActivity(),this);
        recyclerView.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.branch_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener( new CustomItemSelectListener());
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Attendence");
    }

    @Override
    public void onItemClicked(Circular circular) {
        Toast.makeText(getActivity(), circular.getName()+circular.getEmail(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(),CircularDetailsActivity.class);
        intent.putExtra("CircularModel",circular);
        startActivity(intent);
    }

    private class CustomItemSelectListener implements AdapterView.OnItemSelectedListener {
        String firstItem = String.valueOf(spinner.getSelectedItem());
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
                // ToDo when first item is selected
                if(parent.getItemAtPosition(position).toString().startsWith("---")){
                    hideRecyclerViews();
                    Toast.makeText(getContext(),
                            "select item from the list  : " + parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_LONG).show();
                }else {
                    showRecyclerViews();
                   // Utility.showProgressDialog(getContext());
                    Toast.makeText(getContext(),
                            "You have selected  : " + parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_LONG).show();
                }
            } else {
                if(parent.getItemAtPosition(position).toString().startsWith("---")){
                  //  hideRecyclerViews();
                    Toast.makeText(getContext(),
                            "select item from the list  : " + parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_LONG).show();
                }else {
                    showRecyclerViews();
                    Toast.makeText(getContext(),
                            "You have selected  : " + parent.getItemAtPosition(position).toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void showRecyclerViews() {
        recyclerView.setVisibility(View.VISIBLE);
        mNoBrachSelected.setVisibility(View.GONE);
    }
    private void hideRecyclerViews() {
        recyclerView.setVisibility(View.GONE);
        mNoBrachSelected.setVisibility(View.VISIBLE);
    }
}

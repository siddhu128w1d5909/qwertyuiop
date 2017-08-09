package com.siddhu.capp.ui.fragments;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.siddhu.capp.R;
import com.siddhu.capp.models.Circular;
import com.siddhu.capp.models.circulars.CircularsResponse;
import com.siddhu.capp.models.error.APIError;
import com.siddhu.capp.presenter.CircularsPresenter;
import com.siddhu.capp.presenter.CircularsPresenterImpl;
import com.siddhu.capp.ui.activities.CircularDetailsActivity;
import com.siddhu.capp.ui.adapters.CircularAdapter;
import com.siddhu.capp.ui.adapters.CircularsAdapter;
import com.siddhu.capp.utils.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by baji_g on 4/3/2017.
 */
public class StudentHomeFragment extends BaseFragment implements CircularsPresenter.CircularsView,CircularsAdapter.OnCircularItemClickedListener {

    private RecyclerView recyclerview;
    private String[] names;
    private TypedArray profile_pics;
    private String[] emails;
    public static List<Circular> circularList;
    public static List<CircularsResponse> circularRespList;
    View view;
    public static CircularAdapter adapter;
    public static CircularsAdapter circularsAdapter;
    LinearLayoutManager layoutManager;
    private CircularsPresenterImpl mCircularsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_circular, container, false);

        names = getActivity().getResources().getStringArray(R.array.names);
        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
        emails = getResources().getStringArray(R.array.email);


        circularList = new ArrayList<Circular>();
        circularRespList = new ArrayList<CircularsResponse>();
        makeCircularsServiceCall();



        return view;
    }

    private void makeCircularsServiceCall() {
        mCircularsPresenter = new CircularsPresenterImpl();
        mCircularsPresenter.getCiculars();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");

    }

  /*  @Override
    public void onItemClicked(Circular circular) {
        Toast.makeText(getActivity(), circular.getName()+circular.getEmail(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(),CircularDetailsActivity.class);
        intent.putExtra("CircularModel",circular);
        startActivity(intent);
    }*/

    @Override
    public void onCircularsSuccessful(List<CircularsResponse> response) {
        Utility.hideProgressDialog();
        Log.e("CircularsError", response.toString());
        if(response != null){
            for(int i = 0; i < response.size();i++){
                CircularsResponse circularsResponse = new CircularsResponse();
                circularsResponse.setTitle(response.get(i).getTitle());
                circularsResponse.setSubject(response.get(i).getSubject());
                circularsResponse.setImage(response.get(i).getImage());
                circularsResponse.setCreatedAt(response.get(i).getCreatedAt());
                for(int j = 0;j < 50;j++) {
                    circularRespList.add(circularsResponse);
                }
            }
            recyclerview = (RecyclerView) view.findViewById(R.id.circular_recyclerview);
            layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(layoutManager);
            circularsAdapter = new CircularsAdapter(circularRespList,getActivity(), (CircularsAdapter.OnCircularItemClickedListener) this);
            recyclerview.setAdapter(circularsAdapter);
            //circularsResponse = response.getTitle();

        }
    }

    @Override
    public void onCircularsFailed(APIError apiError) {
        Utility.hideProgressDialog();
        Log.e("Error", apiError.toString());
    }

    @Override
    public void onCircularsFailed(Throwable throwable) {
        Utility.hideProgressDialog();
        Log.e("Error", throwable.toString());
    }

    @Override
    public void showHideProgress(boolean show) {

    }
    @Override
    public void onStart() {
        super.onStart();
        mCircularsPresenter.attachView(this);
    }



    @Override
    public void onStop() {
        super.onStop();
        mCircularsPresenter.detachView();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onItemClicked(CircularsResponse circular) {
        Toast.makeText(getActivity(), circular.getTitle()+circular.getTitle(),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(),CircularDetailsActivity.class);
        intent.putExtra("CircularModel",circular);
        startActivity(intent);
    }
}

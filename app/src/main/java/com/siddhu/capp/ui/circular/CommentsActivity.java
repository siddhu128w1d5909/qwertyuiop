package com.siddhu.capp.ui.circular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.siddhu.capp.R;
import com.siddhu.capp.network.ApiService;
import com.siddhu.capp.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by baji_g on 5/31/2017.
 */

public class CommentsActivity  extends AppCompatActivity {
    RecyclerView mCommentsRecycler;
    CommentsAdapter commentsAdapter;
    List<Comments> mCommnetsList = new ArrayList<>();
    private ApiService apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siddhu_main);
        apiService = ApiUtils.getApiService();
        initUI();
    }

    private void initUI() {
        mCommentsRecycler = (RecyclerView)findViewById(R.id.circular_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCommentsRecycler.setLayoutManager(linearLayoutManager);

        mCommentsRecycler.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mCommentsRecycler.addItemDecoration(itemDecoration);
       loadComments();
    }

    private void loadComments() {

      apiService.getCommets().enqueue(new Callback<List<Comments>>() {
          @Override
          public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
              for(int i = 0; i < response.body().size(); i++){
                  Comments comments = response.body().get(i);
                  mCommnetsList.add(comments);
              }
              commentsAdapter = new CommentsAdapter(mCommnetsList,CommentsActivity.this);
              mCommentsRecycler.setAdapter(commentsAdapter);
          }

          @Override
          public void onFailure(Call<List<Comments>> call, Throwable t) {

          }
      });
    }
}

package com.siddhu.capp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siddhu.capp.R;
import com.siddhu.capp.models.Circular;
import com.siddhu.capp.models.circulars.CircularsResponse;


/**
 * Created by baji_g on 5/30/2017.
 */
public class CircularDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mCircularImage;
    TextView mSubject,mdate,mHeaderTxt;
    CircularsResponse circular;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_details_activity);
        getCircularObjectFromIntent();
        initUI();
    }

    private void getCircularObjectFromIntent() {
        // Fetching data from a parcelable object passed from MainActivity
        circular = getIntent().getParcelableExtra("CircularModel");
    }

    private void initUI() {
        mCircularImage = (ImageView)findViewById(R.id.circular_image);
        mSubject = (TextView)findViewById(R.id.circular_subject);
        mdate = (TextView)findViewById(R.id.circular_date);
        mHeaderTxt = (TextView)findViewById(R.id.header_txt);
        mHeaderTxt.setText("Circulars");
        Glide.with(this).load(circular.getImage())
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mCircularImage);
        mSubject.setText(circular.getTitle());
        mdate.setText(circular.getSubject());
        LinearLayout headerLayout = (LinearLayout) findViewById(R.id.header_layout);
        headerLayout.setVisibility(View.VISIBLE);
        ImageView backButton = (ImageView) findViewById(R.id.header_back_btn);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.header_back_btn){
            finish();
        }
    }
}

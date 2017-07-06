package com.study.studydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_image)
    ImageView ivImage;
    private ImageLoad imageLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageLoad = new ImageLoad();
    }

    @OnClick(R.id.btn_load)
    public void onViewClicked() {
        imageLoad.displayImage("http://img3x2.ddimg.cn/68/4/1325855192-1_w_2.jpg", ivImage, this);
    }
}

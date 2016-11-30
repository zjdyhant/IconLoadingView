package com.mm.hant.iconloadingview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mm.hant.iconloadingview.widget.IconLoadingView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private IconLoadingView mIconLoadingView1;
    private IconLoadingView mIconLoadingView2;
    private IconLoadingView mIconLoadingView3;
    private IconLoadingView mIconLoadingView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView1 = (TextView) findViewById(R.id.txt_1);
        mTextView2 = (TextView) findViewById(R.id.txt_2);
        mTextView3 = (TextView) findViewById(R.id.txt_3);
        mTextView4 = (TextView) findViewById(R.id.txt_4);
        mIconLoadingView3 = (IconLoadingView) findViewById(R.id.icon_loading_view3);
        mIconLoadingView4 = (IconLoadingView) findViewById(R.id.icon_loading_view4);

        mIconLoadingView1 = new IconLoadingView(this);
        mIconLoadingView1.setForegroundProgressColor(Color.parseColor("#1AE66B"))
                .setBackgroundProgressColor(Color.parseColor("#4D2BD5")).setIcon(R.drawable.tiger);
        mIconLoadingView2 = new IconLoadingView(this);
        mTextView1.post(new Runnable() {
            @Override
            public void run() {
                mIconLoadingView1.attachToView(mTextView1,0,5,0,5);
                mIconLoadingView2.attachToView(mTextView2,0,5,0,5);
            }
        });
        mTextView1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView1.setText("余额：234.00 元");
                mIconLoadingView1.detachFromView();
            }
        },3000);
        mTextView1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView2.setText("总个数：1000");
                mIconLoadingView2.detachFromView();
            }
        },4000);
        mTextView1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView3.setText("布鲁布鲁布鲁");
                mIconLoadingView3.setVisibility(View.GONE);
            }
        },5000);
        mTextView1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTextView4.setText("烫烫烫烫烫烫烫烫烫");
                mIconLoadingView4.setVisibility(View.GONE);
            }
        },6000);

    }
}

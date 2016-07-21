package com.example.xy.demoxy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TransparentMaskActivity extends Activity {


    private final String LOG_TAG = getClass().getSimpleName();

    private TextView tip;
    private Button buttonUp;
    private Button buttonDown;
    private View.OnClickListener mButtonClickListener;
    private TextView maskText;

    private String[] poetry = {
            "滚滚长江东逝水，",
            "浪花淘尽英雄。",
            "是非成败转头空，",
            "青山依旧在，",
            "几度夕阳红，",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_mask);

        init();
        initMask();
    }

    private void initMask() {
        maskText = new TextView(TransparentMaskActivity.this);
        maskText.setTextColor(Color.WHITE);
        maskText.setTextSize(20);
        maskText.setGravity(Gravity.CENTER_VERTICAL);
        maskText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        StringBuffer showText = new StringBuffer();
        for (String s: poetry) {
            showText.append(s).append("\n");
        }
        maskText.setText(showText);
        maskText.setBackgroundColor(Color.parseColor("#88ffff00"));
        maskText.setVisibility(View.VISIBLE);

        FrameLayout view = (FrameLayout) findViewById(R.id.frame_layout);
        view.addView(maskText);
    }

    private void init() {
        tip         = (TextView) findViewById(R.id.tip);
        buttonUp    = (Button) findViewById(R.id.up);
        buttonDown  = (Button) findViewById(R.id.down);

        mButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.up:
                        showTips("Up");
                        buttonDown.callOnClick();
                        break;
                    case R.id.down:
                        showTips("Down");
                        break;
                    default:
                        break;
                }
            }
        };
        buttonUp.setOnClickListener(mButtonClickListener);
        buttonDown.setOnClickListener(mButtonClickListener);
    }

    private void showTips(String button) {
        tip.setText("You touch the " + button + " button");
    }

}

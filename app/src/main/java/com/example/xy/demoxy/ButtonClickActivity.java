package com.example.xy.demoxy;

/*
    android声音调整源代码分析
        http://blog.csdn.net/tdstds/article/details/25533291
*/


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonClickActivity extends Activity {

    private final String LOG_TAG = getClass().getSimpleName();

    private TextView tip;
    private Button buttonUp;
    private Button buttonDown;
    private View.OnClickListener mButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);
        
        init();
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

package com.example.xy.demoxy;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


public class HandlerThreadActivity extends Activity {


    private static final String LOG_TAG = "HandlerThreadActivity";
    private HandlerThread mThread;
    private MyHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);
        
        init();
    }

    private void init() {

        // why will this cause error ?
//        Thread mThread = new HandlerThread("Demo");
//        Handler mHandler = new MyHandler(mThread.getLooper());
//        mThread.start();

        mThread = new HandlerThread("message");
        mHandler = new MyHandler(mThread.getLooper());
        mThread.start();
        mHandler.sendEmptyMessage(1);
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(LOG_TAG, "msg: " + msg);
        }
    }
}

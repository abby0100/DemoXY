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

public class VolumeActivity extends Activity {


    private static final String LOG_TAG = "VolumeActivity";

    private TextView tip;
    private Button buttonUp;
    private Button buttonDown;
    private Button buttonMute;
    private View.OnClickListener mButtonClickListener;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);
        
        init();
    }

    private void init() {
        tip         = (TextView) findViewById(R.id.tip);
        buttonUp    = (Button) findViewById(R.id.up);
        buttonDown  = (Button) findViewById(R.id.down);
        buttonMute  = (Button) findViewById(R.id.mute);

        mButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.up:
                        changeVolume(AudioManager.ADJUST_RAISE);
                        break;
                    case R.id.down:
                        changeVolume(AudioManager.ADJUST_LOWER);
                        break;
                    case R.id.mute:
                        changeVolume(AudioManager.ADJUST_MUTE);
                    default:
                        break;
                }
            }
        };
        buttonUp.setOnClickListener(mButtonClickListener);
        buttonDown.setOnClickListener(mButtonClickListener);
        buttonMute.setOnClickListener(mButtonClickListener);
    }

    private void changeVolume(int volumeDirection) {
        Log.d(LOG_TAG, "changeVolume volumeDirection: " + volumeDirection);

        if ( mAudioManager == null) {
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
        if (volumeDirection == AudioManager.ADJUST_MUTE) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, AudioManager.FLAG_PLAY_SOUND);
//            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
//            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, AudioManager.FLAG_PLAY_SOUND);
            return;
        }
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, volumeDirection, AudioManager.FLAG_PLAY_SOUND);
//        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, volumeDirection, AudioManager.FLAG_SHOW_UI);
//        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, volumeDirection, AudioManager.FLAG_PLAY_SOUND);
    }


}

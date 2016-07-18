package com.example.xy.demoxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GoogleVoiceActivity extends Activity {


    private static final String LOG_TAG = "GoogleVoiceActivity";
    private TextView tip;
    private Button buttonStart;
    private Button buttonStop;
    private View.OnClickListener mButtonClickListener;
    private SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_voice);
        
        init();
    }

    @Override
    protected void onResume() {
        if (sr == null) {
            sr = SpeechRecognizer.createSpeechRecognizer(this);
            sr.setRecognitionListener(new MyVoiceListener());
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        if (sr == null) {
            return;
        }
        sr.stopListening();
        sr = null;

        super.onPause();
    }

    private void init() {

        tip         = (TextView) findViewById(R.id.tip);
        buttonStart = (Button) findViewById(R.id.start);
        buttonStop  = (Button) findViewById(R.id.stop);
        
        mButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.start:
                        startGoogleVoice();
                        break;
                    case R.id.stop:
                        stopGoogleVoice();
                        break;
                    default:
                        break;
                }
            }
        };
        buttonStart.setOnClickListener(mButtonClickListener);
        buttonStop.setOnClickListener(mButtonClickListener);
    }

    private void stopGoogleVoice() {
        if (sr == null) {
            tip.setText(getString(R.string.text_google_voice_error, "speech recognition is null"));
            return;
        }
        sr.stopListening();
        tip.setText(getString(R.string.text_google_voice_stop_tip));
    }

    private void startGoogleVoice() {

        if (sr == null) {
            sr = SpeechRecognizer.createSpeechRecognizer(this);
            sr.setRecognitionListener(new MyVoiceListener());
        }
        tip.setText(getString(R.string.text_google_voice_start_tip));
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra("calling_package", "VoiceIME");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        sr.startListening(intent);
    }

    private class MyVoiceListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(LOG_TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(LOG_TAG, "onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d(LOG_TAG, "onRmsChanged rmsdB: " + rmsdB);
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d(LOG_TAG, "onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(LOG_TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            Log.d(LOG_TAG, "onError error: " + error);

            String s = "";
            switch(error){
                case SpeechRecognizer.ERROR_AUDIO:
                    s = "录音设别错误";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    s = "其他客户端错误";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    s = "权限不足";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    s = "网络连接错误，请连接到Google";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    s = "网络连接超时";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    s = "没有匹配项";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    s = "识别服务繁忙";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    s = "识别服务器错误";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    s = "无语音输入";
                    break;
            }
            s += " 请重试";

            tip.setText("error: " + s);
        }

        @Override
        public void onResults(Bundle results) {
            Log.d(LOG_TAG, "onResults results: " + results);

            String result = "";
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); ++i) {
                Log.d(LOG_TAG, "result " + data.get(i));
                result += data.get(i);
            }
            tip.setText(result);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(LOG_TAG, "onPartialResults");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d(LOG_TAG, "onReadyForSpeech");
        }
    }

}

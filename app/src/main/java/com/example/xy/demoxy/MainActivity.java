package com.example.xy.demoxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageButton buttonGoogleVoice;
    private ImageButton buttonAndroidVolume;
    private View.OnClickListener mImageButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.G_voice:
                        Toast.makeText(MainActivity.this, getString(R.string.text_google_voice), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.example.xy.GOOGLEVOICE");
                        startActivity(intent);
                        break;
                    case R.id.A_volume:
                        Toast.makeText(MainActivity.this, getString(R.string.text_android_volume), Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent("com.example.xy.ANDROIDVOLUME");
                        startActivity(intent2);
                        break;
                }
            }
        };

        buttonGoogleVoice = (ImageButton) findViewById(R.id.G_voice);
        buttonAndroidVolume = (ImageButton) findViewById(R.id.A_volume);

        setUpOnClickListen(buttonGoogleVoice);
        setUpOnClickListen(buttonAndroidVolume);
    }

    private void setUpOnClickListen(ImageButton imageButton) {
        imageButton.setOnClickListener(mImageButtonListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

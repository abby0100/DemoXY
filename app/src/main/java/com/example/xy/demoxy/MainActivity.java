package com.example.xy.demoxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    private final String LOG_TAG = getClass().getSimpleName();

    private GridView gridView;
    private List<HashMap<String, Object>> data_list;
    private SimpleAdapter simpleAdapter;

    private String[] from = {
            "image",
            "text"
    };
    private int[] to = {
            R.id.image,
            R.id.text,
    };
    private int[] icons = {
            R.drawable.demo,
            R.drawable.demo,
            R.drawable.demo,
            R.drawable.demo,
    };
    private String[] iconsName = {
            "A-Volume",
            "B-Click",
            "G-Voice",
            "H-Thread",
    };
    private AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;

            switch (position) {
                case 0:
                    // a-volume
                    break;
                case 1:
//                    intent = new Intent(MainActivity.this, ButtonClickActivity.class);
//                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGridView();
    }

    private void initGridView() {
        Log.d(LOG_TAG, "initGridView");
        gridView = (GridView) findViewById(R.id.grid_view);
        data_list = new ArrayList<>();
        getArrayListData();
        Log.d(LOG_TAG, "data_list lenght: " + data_list.size());
        simpleAdapter = new SimpleAdapter(MainActivity.this, data_list, R.layout.grid_view_item, from, to);
        gridView.setAdapter(simpleAdapter);

        gridView.setOnItemClickListener(mItemListener);
    }

    private List<HashMap<String, Object>> getArrayListData() {
        for (int i=0; i<icons.length; ++i) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(from[0], icons[i]);
            map.put(from[1], iconsName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    private void init() {

    }

}

package com.waynn.xx.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.waynn.xx.myapplication.dialog.AppleDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"第一个", "第二个", "第三个"};
                AppleDialog appleDialog = new AppleDialog.Builder(MainActivity.this).setItems(items, new AppleDialog.ItemOnClickListener() {
                    @Override
                    public void onClick(int index) {
                        Log.e("way", "onClick: " + index);
                    }
                }).create();
                appleDialog.show();
            }
        });
    }
}

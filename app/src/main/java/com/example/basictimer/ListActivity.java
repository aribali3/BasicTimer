package com.example.basictimer;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    ListScreenFragment lstFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        Bundle b1 = getIntent().getExtras();
        String listInfo = b1.getString("listinfo");

        lstFragment = (ListScreenFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);

        lstFragment.setText(listInfo);
    }
}
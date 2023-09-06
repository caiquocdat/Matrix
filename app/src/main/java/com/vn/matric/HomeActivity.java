package com.vn.matric;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vn.matric.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding activityHomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = activityHomeBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        activityHomeBinding.historyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        activityHomeBinding.level1Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("level",1);
                startActivity(intent);
            }
        });
        activityHomeBinding.level2Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("level",2);
                startActivity(intent);
            }
        });

    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }



    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }
}
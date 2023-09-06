package com.vn.matric;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.vn.matric.data.DatabaseHelper;

import com.vn.matric.adapter.OnGameEndListener;
import com.vn.matric.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int timeLeft = 45; // Thời gian bắt đầu là 20s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        if(getData()==1){
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_1);
        }else  if(getData()==2){
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_2);
        }
        setUpTime();
        activityMainBinding.canvasView.setPointRel(activityMainBinding.pointRel);
        activityMainBinding.canvasView.setOnGameEndListener(new OnGameEndListener() {
            @Override
            public void onGameEnd(String check) {
                float length=activityMainBinding.canvasView.getTotalLength();
                if (check.equals("WIN")&&length>1600&&length<2000){
                    Toast.makeText(MainActivity.this,
                            "Bạn đã thắng ở cấp độ "+getData()+" với thời gian: "+
                                    activityMainBinding.timeTv.getText().toString()
                            , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Bạn đã thua ở cấp độ "+getData()+" với thời gian: "+
                                    activityMainBinding.timeTv.getText().toString()
                            , Toast.LENGTH_SHORT).show();
                }
//                Log.d("Test_1", "onCreate: "+activityMainBinding.canvasView.getTotalLength());
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.insertData(check,getData(),activityMainBinding.timeTv.getText().toString());
                if (timerHandler != null && timerRunnable != null) {
                    timerHandler.removeCallbacks(timerRunnable);
                }

            }
        });

    }

    private int getData() {
        Intent intent= getIntent();
        int level=intent.getIntExtra("level",0);
        return level;
    }

    private void setUpTime() {
        timerHandler = new Handler(); // Khởi tạo Handler

        // Khởi tạo và cài đặt Runnable
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    activityMainBinding.timeTv.setText(timeLeft + "s");
                    timerHandler.postDelayed(this, 1000); // Đặt lại Runnable sau 1s
                } else {
                    // Thời gian đã hết, bạn có thể thực hiện các tác vụ khác ở đây
                    Toast.makeText(MainActivity.this, "Bạn đã thua", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Bắt đầu đếm ngược
        timerHandler.postDelayed(timerRunnable, 1000);
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

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
}
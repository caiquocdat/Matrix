package com.vn.matric;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vn.matric.data.DatabaseHelper;
import com.vn.matric.adapter.OnCheckEndListener;
import com.vn.matric.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int remainingTime = 45; // Thời gian bắt đầu là 45s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        hideSystemUI();
        setupTimer();
        int level = getLevel();

        if (level == 1) {
            binding.matricImageView.setImageResource(R.drawable.img_matric_1);
        } else if (level == 2) {
            binding.matricImageView.setImageResource(R.drawable.img_matric_2);
        }

        binding.canvasView.setPointRel(binding.pointRelativeLayout);
        binding.canvasView.setOnCheckEndListener(new OnCheckEndListener() {
            @Override
            public void onCheckEnd(String result) {
                float length = binding.canvasView.getTotalLength();
                Log.d("Test_20", "onCheckEnd: " + remainingTime);
                String resultMessage;

                if (result.equals("WIN") && length > 1100 && length < 2000 && remainingTime < 40) {
                    resultMessage = "Bạn đã thắng ở level " + level + ", trong thời gian: " +
                            binding.timeTextView.getText().toString();
                } else {
                    resultMessage = "Bạn đã thua ở level " + level + ", trong thời gian: " +
                            binding.timeTextView.getText().toString();
                }

                Toast.makeText(MainActivity.this, resultMessage, Toast.LENGTH_SHORT).show();
                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                dbHelper.insertData(result, level, binding.timeTextView.getText().toString());

                if (timerHandler != null && timerRunnable != null) {
                    timerHandler.removeCallbacks(timerRunnable);
                }
            }
        });
    }

    private int getLevel() {
        Intent intent = getIntent();
        return intent.getIntExtra("level", 0);
    }

    private void setupTimer() {
        timerHandler = new Handler(); // Khởi tạo Handler

        // Khởi tạo và cài đặt Runnable
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                    binding.timeTextView.setText(remainingTime + "s");
                    timerHandler.postDelayed(this, 1000); // Đặt lại Runnable sau 1s
                } else {
                    // Thời gian đã hết, bạn có thể thực hiện các tác vụ khác ở đây
                    Toast.makeText(MainActivity.this, "Bạn đã thua", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
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
        // Để trống để không cho phép người dùng bấm nút Back
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
}

package my.caiquocdat.treasurehunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import my.caiquocdat.treasurehunt.adapter.OnGameEndListener;
import my.caiquocdat.treasurehunt.data.DatabaseHelper;
import my.caiquocdat.treasurehunt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int timeLeft = 30; // Thời gian bắt đầu là 20s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        if(getData()==1){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_1);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_1);
        }else  if(getData()==2){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_2);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_2);
        }else  if(getData()==3){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_3);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_3);
        }else  if(getData()==4){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_4);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_4);
        }else  if(getData()==5){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_5);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_5);
        }else  if(getData()==6){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_6);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_6);
        }else  if(getData()==7){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_7);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_7);
        }else  if(getData()==8){
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_8);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_8);
        }else  if(getData()==9){
            int newWidth = (int) getResources().getDimension(R.dimen.new_width);  // Lấy từ res/dimen.xml
            int newHeight = (int) getResources().getDimension(R.dimen.new_height); // Lấy từ res/dimen.xml

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(newWidth, newHeight);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); // Vị trí giữa màn hình

            activityMainBinding.pointRel.setLayoutParams(params);
            activityMainBinding.levelImg.setImageResource(R.drawable.img_level_9);
            activityMainBinding.matricImg.setImageResource(R.drawable.img_matric_9);
        }
        setUpTime();
        activityMainBinding.canvasView.setPointRel(activityMainBinding.pointRel);
        activityMainBinding.canvasView.setOnGameEndListener(new OnGameEndListener() {
            @Override
            public void onGameEnd(String check) {
                float length=activityMainBinding.canvasView.getTotalLength();
                if (check.equals("WIN")&&length>1000&&length<5000){
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
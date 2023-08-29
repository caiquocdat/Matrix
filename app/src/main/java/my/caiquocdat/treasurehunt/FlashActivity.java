package my.caiquocdat.treasurehunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import my.caiquocdat.treasurehunt.databinding.ActivityFlashBinding;
import my.caiquocdat.treasurehunt.databinding.ActivityMainBinding;

public class FlashActivity extends AppCompatActivity {
    private ActivityFlashBinding activityFlashBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFlashBinding = ActivityFlashBinding.inflate(getLayoutInflater());
        View view = activityFlashBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        activityFlashBinding.playImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FlashActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        activityFlashBinding.historyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FlashActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }
}
package com.vn.matric;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vn.matric.databinding.ActivityHomeBinding;
import com.vn.matric.databinding.ActivitySplashBinding;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding activitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = activitySplashBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        if (checkInternet()) {
            checkIP();
        } else {
            Toast.makeText(this, "Thiết bị không có Internet", Toast.LENGTH_SHORT).show();
        }
    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    private boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void checkIP() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://ipinfo.io/json")
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String country = jsonObject.getString("country");

                    if (!"VN".equals(country)) {
                        // Open web browser to https://www.24h.com.vn/
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://shbettpro.online/"));
                        startActivity(browserIntent);
                    } else {
                        // Start another activity
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
        if (checkInternet()) {
            checkIP();
        } else {
            Toast.makeText(this, "Thiết bị không có Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
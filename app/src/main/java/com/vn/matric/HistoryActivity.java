package com.vn.matric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.vn.matric.data.DatabaseHelper;
import com.vn.matric.databinding.ActivityHistoryBinding;
import com.vn.matric.model.HistoryModel;

import java.util.ArrayList;

import com.vn.matric.adapter.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding activityHistoryBinding;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryModel> listHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        View view = activityHistoryBinding.getRoot();
        setContentView(view);
        hideSystemUI();
        listHistory=new ArrayList<>();
        setUpDatRcv();
    }

    private void setUpDatRcv() {
        DatabaseHelper db = new DatabaseHelper(this);
        listHistory = db.getAllData();
        historyAdapter = new HistoryAdapter(listHistory, this);
        activityHistoryBinding.historyRcv.setLayoutManager(new LinearLayoutManager(this));
        activityHistoryBinding.historyRcv.setAdapter(historyAdapter);
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
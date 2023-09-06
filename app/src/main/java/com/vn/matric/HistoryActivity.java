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
    private ActivityHistoryBinding binding;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryModel> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        hideSystemUI();
        historyList = new ArrayList<>();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        historyList = dbHelper.getAllData();
        historyAdapter = new HistoryAdapter(historyList, this);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.historyRecyclerView.setAdapter(historyAdapter);
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

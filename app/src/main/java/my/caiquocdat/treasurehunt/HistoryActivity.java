package my.caiquocdat.treasurehunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import my.caiquocdat.treasurehunt.adapter.HistoryAdapter;
import my.caiquocdat.treasurehunt.data.DatabaseHelper;
import my.caiquocdat.treasurehunt.databinding.ActivityHistoryBinding;
import my.caiquocdat.treasurehunt.databinding.ActivityHomeBinding;
import my.caiquocdat.treasurehunt.model.HistoryModel;

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
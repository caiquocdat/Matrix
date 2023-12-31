package com.vn.matric.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.matric.model.HistoryModel;

import java.util.ArrayList;

import com.vn.matric.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final ArrayList<HistoryModel> historyList;
    private final Context context;

    public HistoryAdapter(ArrayList<HistoryModel> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_rcv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel historyModel = historyList.get(position);
        holder.checkStatusTv.setText(historyModel.getCheckStatus());
        holder.levelTv.setText("Level " + historyModel.getLevel());
        holder.timeLeftTv.setText(historyModel.getTimeLeft());
        if (position % 2 == 0) {
            holder.itemRel.setBackgroundColor(0xE6000000); // Màu trắng cho vị trí chẵn
        } else {
            holder.itemRel.setBackgroundColor(0xE6FFFFFF); // Màu xám nhạt cho vị trí lẻ
        }
        if (holder.checkStatusTv.getText().equals("WIN")){
            holder.checkStatusTv.setTextColor(0xFF00FF38);
        }else{
            holder.checkStatusTv.setTextColor(0xFFFF0000);
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView checkStatusTv;
        final TextView levelTv;
        final TextView timeLeftTv;
        final RelativeLayout itemRel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkStatusTv = itemView.findViewById(R.id.checkTextView);
            levelTv = itemView.findViewById(R.id.levelTextView);
            timeLeftTv = itemView.findViewById(R.id.timeTextView);
            itemRel = itemView.findViewById(R.id.itemRelativeLayout);
        }
    }
}

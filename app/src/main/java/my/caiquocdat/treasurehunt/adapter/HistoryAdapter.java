package my.caiquocdat.treasurehunt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.caiquocdat.treasurehunt.R;
import my.caiquocdat.treasurehunt.model.HistoryModel;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel historyModel = historyList.get(position);
        holder.checkStatusTv.setText(historyModel.getCheckStatus());
        holder.levelTv.setText("Level " + historyModel.getLevel());
        holder.timeLeftTv.setText(historyModel.getTimeLeft());
        if (position % 2 == 0) {
            holder.itemRel.setBackgroundColor(0x33000000); // Màu trắng cho vị trí chẵn
        } else {
            holder.itemRel.setBackgroundColor(0x33FFFFFF); // Màu xám nhạt cho vị trí lẻ
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
            checkStatusTv = itemView.findViewById(R.id.checkTv);
            levelTv = itemView.findViewById(R.id.levelTv);
            timeLeftTv = itemView.findViewById(R.id.timeTv);
            itemRel = itemView.findViewById(R.id.itemRel);
        }
    }
}

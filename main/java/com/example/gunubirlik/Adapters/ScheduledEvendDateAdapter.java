package com.example.gunubirlik.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gunubirlik.Models.ScheduledEventDate;
import com.example.gunubirlik.R;
import com.example.gunubirlik.ViewModels.ScheduledEventDateViewModel;

import java.util.List;

public class ScheduledEvendDateAdapter extends RecyclerView.Adapter<ScheduledEvendDateAdapter.ScheduledEventHolder> {
    private final List<ScheduledEventDate> scheduledEventDates;
    private final Context context;
    private final ScheduledEventDateViewModel viewModel;

    public ScheduledEvendDateAdapter(List<ScheduledEventDate> scheduledEventDates, Context context, ScheduledEventDateViewModel viewModel){
        this.scheduledEventDates = scheduledEventDates;
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ScheduledEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.date_card, parent, false);
        return new ScheduledEventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledEventHolder holder, int position) {
        ScheduledEventDate scheduledEventDate = scheduledEventDates.get(position);
        holder.setData(scheduledEventDate);
        holder.dateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSchelucedEventDate(scheduledEventDate);
            }
        });
    }


    @Override
    public int getItemCount() {
        return scheduledEventDates.size();
    }

    class ScheduledEventHolder extends RecyclerView.ViewHolder {

        TextView dayName, dayNum;
        CardView isFocused, dateCard;

        public ScheduledEventHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            dayNum = itemView.findViewById(R.id.dayNumber);
            isFocused = itemView.findViewById(R.id.isFocused);
            dateCard = itemView.findViewById(R.id.dateCard);
        }

        public void setData(ScheduledEventDate scheduledEventDate) {
            dayName.setText(scheduledEventDate.getDate().getDayName());
            dayNum.setText(scheduledEventDate.getDate().getDayNum());
        }
    }
}

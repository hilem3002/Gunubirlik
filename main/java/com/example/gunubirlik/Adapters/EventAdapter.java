package com.example.gunubirlik.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gunubirlik.AlarmManagers.AlarmManagerExecuter;
import com.example.gunubirlik.AlarmManagers.Receivers.EventUpdateReceiver;
import com.example.gunubirlik.Calculators.DateEventDateConverter;
import com.example.gunubirlik.Models.Events.Event;
import com.example.gunubirlik.R;
import com.example.gunubirlik.ViewModels.ScheduledEventDateViewModel;

import java.util.List;
import java.util.Objects;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {
    private List<Event> upComingEvents, currentDayUpComingEvents;
    private Context context;
    private AlarmManagerExecuter alarm;
    private ScheduledEventDateViewModel scheduledEventDateViewModel;

    public EventAdapter(List<Event> upComingEvents, Context context, AlarmManagerExecuter alarm, List<Event> currentDayUpComingEvents, ScheduledEventDateViewModel scheduledEventDateViewModel) {
        this.upComingEvents = upComingEvents;
        this.context = context;
        this.alarm = alarm;
        this.currentDayUpComingEvents = currentDayUpComingEvents;
        this.scheduledEventDateViewModel = scheduledEventDateViewModel;
    }

    // creating the view
    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_event_card, parent, false);
        return new EventHolder(view);
    }

    // setting the datas
    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        // getting the each position for the list and setting
        Event event = upComingEvents.get(position);
        holder.setData(event);

        // setting the alarms for each event
        if (currentDayUpComingEvents.equals(upComingEvents)) {
            String minus15Time = DateEventDateConverter.minus15Min(event.getStartTime());
            int[] hourMin = DateEventDateConverter.stringToIntTime(minus15Time);
            alarm.setAlarmEvent(EventUpdateReceiver.class, 1, hourMin[0], hourMin[1]);
        }
    }

    @Override
    public int getItemCount() {
        return upComingEvents.size();
    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventStart, eventEnd, eventDescription;
        CardView eventCard;
        ImageView deleteEvent;
        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.upcomingEventName);
            eventStart = itemView.findViewById(R.id.upcomingEventStart);
            eventEnd = itemView.findViewById(R.id.upcomingEventEnd);
            eventDescription = itemView.findViewById(R.id.upcomingEventDescription);
            deleteEvent = itemView.findViewById(R.id.deleteEvent);

            eventCard = itemView.findViewById(R.id.upcomingEventCard);
            eventCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventDescription.getVisibility() == View.GONE) {
                        eventDescription.setVisibility(View.VISIBLE);
                    }
                    else {
                        eventDescription.setVisibility(View.GONE);
                    }
                }
            });
        }

        public void setData(Event event) {
            this.eventName.setText(event.getName());
            this.eventStart.setText(event.getStartTime());
            this.eventEnd.setText(event.getEndTime());
            this.eventDescription.setText(event.getDescription());

            this.deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // updating the list in ui
                    upComingEvents.remove(event);
                    Objects.requireNonNull(scheduledEventDateViewModel.getSchelucedEventDate().getValue()).setEvents(upComingEvents);
                    scheduledEventDateViewModel.setSchelucedEventDate(scheduledEventDateViewModel.getSchelucedEventDate().getValue());

                    // TODO SAVE THE NEW DATA IN SHAREDPREFERENCES
                }
            });
        }
    }
}

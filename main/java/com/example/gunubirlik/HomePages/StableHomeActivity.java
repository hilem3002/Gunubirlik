package com.example.gunubirlik.HomePages;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gunubirlik.Adapters.EventAdapter;
import com.example.gunubirlik.Adapters.ScheduledEvendDateAdapter;
import com.example.gunubirlik.AlarmManagers.AlarmManagerExecuter;
import com.example.gunubirlik.AlarmManagers.Receivers.CurrentEventUpdateReceiver;
import com.example.gunubirlik.AlarmManagers.Receivers.NextEventUpdateReceiver;
import com.example.gunubirlik.Calculators.DateEventDateConverter;
import com.example.gunubirlik.Factories.PlanListViewModelFactory;
import com.example.gunubirlik.Models.Events.Event;
import com.example.gunubirlik.Services.EventHandlerService;
import com.example.gunubirlik.Services.PopUpService.OnTimeSelect;
import com.example.gunubirlik.R;
import com.example.gunubirlik.Services.PopUpService.PopUpService;
import com.example.gunubirlik.ViewModels.CurrentEventViewModel;
import com.example.gunubirlik.ViewModels.EventDateViewModel;
import com.example.gunubirlik.ViewModels.FinishedEventViewModel;
import com.example.gunubirlik.ViewModels.ListDatesViewModel;
import com.example.gunubirlik.ViewModels.NextEventViewModel;
import com.example.gunubirlik.ViewModels.PlanListViewModel;
import com.example.gunubirlik.ViewModels.ScheduledEventDateViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class StableHomeActivity extends AppCompatActivity {

    private TextView listName, curDayName, curDayNum, curDayMonth, curEventName, curEventStartTime,
            curEventEndTime, curEventDescription, eventStartedOrFinishedText, successRate, upcomingEventTime,
            upcomingEventName, upcomingEventTimeInDetails, upcomingEventDetails;
    private CardView nextEventCard, curEventCard, moreEventCard, successRateCard;
    private LinearLayout nextEventDetailsLayout, curEventDetailsLayout;
    private RecyclerView recyclerView, dateCardRv;
    private MaterialButton seeAllButton, clickedAsStarted, addButton, cleanButton;
    private ImageButton addEventButton, exitButton, backToToday;
    private ConstraintLayout constraintLayout;
    private TextInputEditText newEventName, newEventDescription, newEventStart, newEventEnd;
    private EventHandlerService eventHandlerService;
    private ListDatesViewModel listDatesViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stable_home);

        listName = findViewById(R.id.listName);
        dateCardRv = findViewById(R.id.dateCardRv);
        nextEventCard = findViewById(R.id.nextEventCard);
        recyclerView = findViewById(R.id.recyclerViewEvent);
        curDayName = findViewById(R.id.curDayName);
        curDayNum = findViewById(R.id.curDayNum);
        curDayMonth = findViewById(R.id.curDayMonth);
        nextEventDetailsLayout = findViewById(R.id.nextEventDetailsLayout);
        curEventDetailsLayout = findViewById(R.id.curEventDetails);
        moreEventCard = findViewById(R.id.moreEventCard);
        seeAllButton = findViewById(R.id.seeAllButton);
        curEventCard = findViewById(R.id.curEventCard);
        curDayName = findViewById(R.id.curDayName);
        curDayNum = findViewById(R.id.curDayNum);
        curDayMonth = findViewById(R.id.curDayMonth);
        backToToday = findViewById(R.id.backToTodayButton);
        AlarmManagerExecuter alarm = new AlarmManagerExecuter(this);

        // getting the selected plan list from shared preference and setting in viewmodel
        PlanListViewModelFactory factory = new PlanListViewModelFactory(this);
        PlanListViewModel planListViewModel = new ViewModelProvider(this, factory).get(PlanListViewModel.class);
        eventHandlerService = new EventHandlerService(planListViewModel);
        planListViewModel.setPlanList();

        // setting the shared preference datas to views
        planListViewModel.getPlanList().observe(this, planList -> {
            listName.setText(planList.getListName());

            // setting the current event to its view model
            CurrentEventViewModel currentEventViewModel = new ViewModelProvider(this).get(CurrentEventViewModel.class);
            currentEventViewModel.setCurrentEvent(planList.getCurrentEvent());

            // setting the current event details to views
            currentEventViewModel.getCurrentEvent().observe(this, currentEvent -> {
                if (currentEvent != null) {

                    if (planList.getFinishedEvent() == null) {
                        // setting the alarm for current event end time
                        int[] hourMin = DateEventDateConverter.stringToIntTime(currentEvent.getEndTime());
                        alarm.setAlarmEvent(CurrentEventUpdateReceiver.class, 3, hourMin[0], hourMin[1]);
                    }

                    curEventCard.setVisibility(View.VISIBLE);
                    curEventName = findViewById(R.id.curEventName);
                    curEventStartTime = findViewById(R.id.curEventStartTime);
                    curEventEndTime = findViewById(R.id.curEventEndTime);
                    curEventDescription = findViewById(R.id.curEventDescription);
                    eventStartedOrFinishedText = findViewById(R.id.eventStartedOrFinishedText);
                    curEventName.setText(currentEvent.getName());
                    curEventStartTime.setText(currentEvent.getStartTime());
                    curEventEndTime.setText(currentEvent.getEndTime());
                    curEventDescription.setText(currentEvent.getDescription());

                    // transitions for clicks on current event card
                    curEventCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // curEventCard open and close
                            // recyclerview will close if both are open
                            int vis = (curEventDetailsLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                            TransitionManager.beginDelayedTransition(curEventDetailsLayout, new AutoTransition());
                            curEventDetailsLayout.setVisibility(vis);
                            if (curEventDetailsLayout.getVisibility() == View.VISIBLE && nextEventDetailsLayout.getVisibility() == View.VISIBLE) {
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                    });

                    clickedAsStarted = findViewById(R.id.clickedAsStartedButton);
                    successRateCard = findViewById(R.id.successRateCard);
                    // looking for current event is finished or started
                    if (currentEvent.isStarted() && currentEvent.isFinished()) {

                        // setting the finished event to its view model
                        FinishedEventViewModel finishedEventViewModel = new ViewModelProvider(this).get(FinishedEventViewModel.class);
                        finishedEventViewModel.setFinishedEvent(planList.getFinishedEvent());

                        // getting the finished event and also if it would be setted again code will execute for each set as others
                        finishedEventViewModel.getFinishedEvent().observe(this, finishedEvent -> {
                            if (finishedEvent != null) {
                                eventStartedOrFinishedText.setText("Biten etkinlik");
                                clickedAsStarted.setVisibility(View.GONE);
                                successRate = findViewById(R.id.successRate);
                                successRate.setText(finishedEvent.getSuccessRate());
                                successRateCard.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    else {
                        eventStartedOrFinishedText.setText("Başlayan etkinlik");
                        clickedAsStarted.setVisibility(View.VISIBLE);
                        successRateCard.setVisibility(View.GONE);
                        if (currentEvent.isStarted()) {
                            currentEvent.clickedAsStarted(StableHomeActivity.this, clickedAsStarted);
                        }
                        clickedAsStarted.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!currentEvent.isStarted()) {
                                    currentEvent.clickedAsStarted(StableHomeActivity.this, clickedAsStarted);
                                }
                                else {
                                    planList.setLastEvent(currentEvent.clicedAsFinished(StableHomeActivity.this, clickedAsStarted));
                                }
                                currentEventViewModel.setCurrentEvent(currentEvent);
                            }
                        });
                    }
                    planListViewModel.savePlanList();
                }
                else {
                    curEventCard.setVisibility(View.GONE);
                }
            });

            // setting the next event view model
            NextEventViewModel nextEventViewModel = new ViewModelProvider(this).get(NextEventViewModel.class);
            nextEventViewModel.setNextEvent(planList.getNextEvent());
            eventHandlerService.setNextEventViewModel(nextEventViewModel);

            //eventHandlerService.setNextEventViewModel(nextEventViewModel);
            // getting the next event
            nextEventViewModel.getNextEvent().observe(this, nextEvent -> {
                if (nextEvent != null) {
                    if (!nextEvent.equals(planList.getNextEvent())) {
                        planList.setNextEvent(nextEvent);
                    }
                    nextEventCard.setVisibility(View.VISIBLE);
                    upcomingEventTime = findViewById(R.id.upcomingEventTime);
                    upcomingEventName = findViewById(R.id.upcomingEventName);
                    upcomingEventTimeInDetails = findViewById(R.id.upcomingEventTimeInDetails);
                    upcomingEventDetails = findViewById(R.id.upcomingEventDetails);
                    upcomingEventTime.setText(nextEvent.getStartTime());
                    upcomingEventName.setText(nextEvent.getName());
                    upcomingEventTimeInDetails.setText(nextEvent.getStartTime());
                    upcomingEventDetails.setText(nextEvent.getDescription());

                    int[] hourMin = DateEventDateConverter.stringToIntTime(nextEvent.getStartTime());
                    alarm.setAlarmEvent(NextEventUpdateReceiver.class, 2, hourMin[0], hourMin[1]);

                    nextEventCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // nextEventCard open and close
                            // recyclerview will close if both are open
                            int vis = (nextEventDetailsLayout.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
                            TransitionManager.beginDelayedTransition(nextEventDetailsLayout, new AutoTransition());
                            nextEventDetailsLayout.setVisibility(vis);
                            if (curEventDetailsLayout.getVisibility() == View.VISIBLE && nextEventDetailsLayout.getVisibility() == View.VISIBLE) {
                                recyclerView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else {
                    nextEventCard.setVisibility(View.GONE);
                }
            });

            // setting the view model
            listDatesViewModel = new ViewModelProvider(this).get(ListDatesViewModel.class);
            listDatesViewModel.setListDates(planList.getListDates());
            eventHandlerService.setListDatesViewModel(listDatesViewModel);

            // getting the data to execute the code for each time that is setted
            listDatesViewModel.getListDate().observe(this, listDate -> {
                if (listDate != null) {
                    if (!listDate.equals(planList.getListDates())) {
                        planList.setListDates(listDate);
                    }

                    // setting the alarm
                    alarm.setAlarmScheduledDateEvent();

                    //setting the view modeL
                    ScheduledEventDateViewModel scheduledEventDateViewModel = new ViewModelProvider(this).get(ScheduledEventDateViewModel.class);
                    scheduledEventDateViewModel.setSchelucedEventDate(listDate.get(0));

                    // setting the date cards recyclerview
                    ScheduledEvendDateAdapter scheduledEvendDateAdapter = new ScheduledEvendDateAdapter(listDate.subList(1, 15), this, scheduledEventDateViewModel);
                    dateCardRv.setHasFixedSize(true);
                    LinearLayoutManager managerScheduled = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    dateCardRv.setLayoutManager(managerScheduled);
                    dateCardRv.setAdapter(scheduledEvendDateAdapter);

                    // getting the data to execute the code for each time that is setted
                    scheduledEventDateViewModel.getSchelucedEventDate().observe(this, schelucedEventDate -> {

                        if (schelucedEventDate != null) {
                            if (schelucedEventDate.getEvents() != null) {
                                // setting the recyclerview
                                EventAdapter adapter = new EventAdapter(schelucedEventDate.getEvents(), this, alarm, listDate.get(0).getEvents(), scheduledEventDateViewModel);
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                schelucedEventDate.setEvents(new ArrayList<>());
                                planListViewModel.savePlanList();
                                scheduledEventDateViewModel.setSchelucedEventDate(schelucedEventDate);
                            }

                            // setting the current day data that means scheluced event date object
                            EventDateViewModel eventDateViewModel = new ViewModelProvider(this).get(EventDateViewModel.class);
                            eventDateViewModel.setEventDate(schelucedEventDate.getDate());

                            // getting the data
                            eventDateViewModel.getEventDate().observe(this, eventDate -> {
                                curDayNum.setText(eventDate.getDayNum());
                                curDayName.setText(eventDate.getDayName());
                                curDayMonth.setText(eventDate.getMonth());
                            });

                            // back to today when main day card clicked
                            backToToday.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (schelucedEventDate != listDate.get(0)) {
                                        scheduledEventDateViewModel.setSchelucedEventDate(listDate.get(0));
                                    }
                                }
                            });
                        }

                        addEventButton = findViewById(R.id.addEventButton);
                        constraintLayout = findViewById(R.id.stableHomeLayout);
                        PopUpService popUpService = new PopUpService(StableHomeActivity.this, constraintLayout, R.layout.add_event_popup);
                        addEventButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // pop up screen execution
                                popUpService.popUpExecution();
                            }
                        });

                        newEventName = popUpService.getView().findViewById(R.id.newEventName);
                        newEventStart = popUpService.getView().findViewById(R.id.newEventStart);
                        newEventEnd = popUpService.getView().findViewById(R.id.newEventEnd);
                        newEventDescription = popUpService.getView().findViewById(R.id.newEventDescription);

                        newEventStart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popUpService.timePickerExecute(new OnTimeSelect() {
                                    // used an interface for data loading delay
                                    @Override
                                    public void OnTimeSelection(String time) {
                                        newEventStart.setText(time);
                                    }
                                });
                            }
                        });

                        newEventEnd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popUpService.timePickerExecute(new OnTimeSelect() {
                                    // used an interface for data loading delay
                                    @Override
                                    public void OnTimeSelection(String time) {
                                        newEventEnd.setText(time);
                                    }
                                });
                            }
                        });

                        addButton = popUpService.getView().findViewById(R.id.newEventAdd);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popUpService.popUpClose();
                                Event event = new Event(
                                        newEventStart.getText().toString(),
                                        newEventEnd.getText().toString(),
                                        newEventName.getText().toString(),
                                        newEventDescription.getText().toString());

                                // updating and saving the data
                                schelucedEventDate.getEvents().add(event);
                                planListViewModel.savePlanList();
                                scheduledEventDateViewModel.setSchelucedEventDate(schelucedEventDate);
                            }
                        });

                        cleanButton = popUpService.getView().findViewById(R.id.newEventClean);
                        cleanButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                newEventStart.setText("");
                                newEventEnd.setText("");
                                newEventName.setText("");
                                newEventDescription.setText("");
                            }
                        });

                        exitButton = popUpService.getView().findViewById(R.id.exitButton);
                        exitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popUpService.popUpClose();
                            }
                        });
                    });
                }
            });

            moreEventCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // cur and next event card details will close if not
                    // recyclerview will be setted to visible if its not
                    TransitionManager.beginDelayedTransition(nextEventDetailsLayout, new AutoTransition());
                    nextEventDetailsLayout.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(curEventDetailsLayout, new AutoTransition());
                    curEventDetailsLayout.setVisibility(View.GONE);
                }
            });

            seeAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // cur and next event card will be setted to gone or visible when pressed
                    if (curEventCard.getVisibility() == View.VISIBLE) {
                        curEventCard.setVisibility(View.GONE);
                        nextEventCard.setVisibility(View.GONE);
                        seeAllButton.setText("Küçült");
                    }
                    else {
                        if (planList.getNextEvent() != null) {
                            nextEventCard.setVisibility(View.VISIBLE);
                        }
                        if (planList.getCurrentEvent() != null) {
                            curEventCard.setVisibility(View.VISIBLE);
                        }
                        seeAllButton.setText("Tümünü gör");
                    }
                }
            });

        });

    }

    // unregister from the EventBus
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventHandlerService != null) {
            eventHandlerService.unregister();
        }
    }

}

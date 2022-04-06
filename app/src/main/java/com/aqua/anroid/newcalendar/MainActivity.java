package com.aqua.anroid.newcalendar;

import static com.aqua.anroid.newcalendar.CalendarUtils.daysInMonthArray;
import static com.aqua.anroid.newcalendar.CalendarUtils.monthYearFromDate;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.time.LocalDate;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets(); //id 통해 목록 찾음
        //loadFromDBToMemory();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        setOnClickListener();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    /* db
    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instance
    }*/

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setEventAdapter();
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    // 이벤트 Adapter 제공
    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    // event item 클릭 시
    private void setOnClickListener() {
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = (Event) eventListView.getItemAtPosition(position);
                Intent editEventIntent = new Intent(getApplicationContext(), EventEditActivity.class);
                editEventIntent.putExtra(Event.Event_EDIT_EXTRA, selectedEvent.getId());
                startActivity(editEventIntent);
            }
        });
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(MainActivity.this,EventEditActivity.class));
    }

    // 재개될 때마다 다시 로드되도록 EventAdapter 호출
    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }
}
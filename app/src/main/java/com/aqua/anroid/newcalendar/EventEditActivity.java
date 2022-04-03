package com.aqua.anroid.newcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time; // 현지 시간으로 시간 호출

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now(); // 지금 현지 시간으로 초기화
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View View)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event (eventName,CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent); // 새 이벤트를 이벤트 목록에 추가

        startActivity(new Intent(this,MainActivity.class));

    }

    public void deleteEventAction(View view) {
    }
}

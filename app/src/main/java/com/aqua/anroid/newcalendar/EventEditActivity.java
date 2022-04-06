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
    private EditText eventTitleET;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time; // 현지 시간으로 시간 호출

    private Event selectedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now(); // 지금 현지 시간으로 초기화

        //날짜 Text 선택한 날짜로 설정
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
        
        checkForEditEvent();
    }



    private void initWidgets()
    {
        eventTitleET = findViewById(R.id.eventTitleET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    private void checkForEditEvent()
    {
        Intent previousIntent = getIntent();

        int passedEventID = previousIntent.getIntExtra(Event.Event_EDIT_EXTRA, -1);
        selectedEvent = Event.getEventForID(passedEventID);

        // 이벤트 편집이 있음을 의미하는 선택된 이벤트 찾았을 때
        if(selectedEvent != null)
        {
            eventTitleET.setText(selectedEvent.getName());
            //eventTitleET.setText(selectedEvent.getName());
        }
    }

    public void saveEventAction(View View)
    {
        String eventTitle = eventTitleET.getText().toString();

        if(selectedEvent == null)
        {
            int id = Event.eventsList.size();
            Event newEvent = new Event(id, eventTitle, CalendarUtils.selectedDate, time);
            //Event newEvent = new Event (eventTitle,CalendarUtils.selectedDate, time);

            Event.eventsList.add(newEvent); // 새 이벤트를 이벤트 목록에 추가
        }
        else // 편집 모드
        {
            //선택한 메모에 제목을 가져와 동일하게 지정
            selectedEvent.setName(eventTitle);

            // db 업데이트 하기
            // ...
        }
        startActivity(new Intent(this,MainActivity.class));

    }

    public void deleteEventAction(View view)
    {
    }
}

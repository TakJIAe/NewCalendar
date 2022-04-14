package com.aqua.anroid.newcalendar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.OrientationHelper;

import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class EventEditActivity extends AppCompatActivity
{
    private EditText eventTitleET;
    private TextView eventDateTV, eventTimeTV;
    private Button deleteEventBtn;

    private Event selectedEvent;
    private LocalTime time; // 현지 시간으로 시간 호출

    TextView startDateTV,endDateTV;

    Event event = new Event();

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

        getdateData();
        checkForEditEvent();

    }

    private void initWidgets()
    {
        eventTitleET = findViewById(R.id.eventTitleET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);

        deleteEventBtn = findViewById(R.id.deleteEventBtn);
        startDateTV = findViewById(R.id.startDateTV);
        endDateTV= findViewById(R.id.endDateTV);

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
            startDateTV.setText(selectedEvent.getStartdate());
            endDateTV.setText(selectedEvent.getEnddate());

        }
        else
        {
            //새로운 이벤트 생성 시 삭제 버튼 숨김
            deleteEventBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void saveEventAction(View View)
    {
        String eventTitle = eventTitleET.getText().toString();
        String eventStartDate = startDateTV.getText().toString();
        String eventEndDate = endDateTV.getText().toString();

        if(selectedEvent == null)
        {
            int id = Event.eventsList.size();

            Event newEvent = new Event(id, eventTitle, CalendarUtils.selectedDate,  eventStartDate, eventEndDate, time);
            //Event newEvent = new Event (eventTitle,CalendarUtils.selectedDate, time);

            Event.eventsList.add(newEvent); // 새 이벤트를 이벤트 목록에 추가
        }
        else // 편집 모드
        {
            //선택한 메모에 제목을 가져와 동일하게 지정
            selectedEvent.setName(eventTitle);
            selectedEvent.setStartdate(eventStartDate);
            selectedEvent.setStartdate(eventEndDate);

            // db 업데이트 하기
            // ...
        }
        startActivity(new Intent(this,MainActivity.class));

    }

    // 이벤트 삭제
    public void deleteEventAction(View view)
    {
        // 새 날짜를 호출하여 삭제된 시간을 제공
        selectedEvent.setDeleted(new Date());
        //db 설정
        //db 업데이트

        startActivity(new Intent(this,MainActivity.class));
    }

    //날짜 선택
    public void datepickerAction(View view)
    {
        startActivity(new Intent(this,DatePickerActivity.class));

    }


    private void getdateData()
    {
        Intent intent = getIntent();
        ArrayList<String> Dates = intent.getStringArrayListExtra("Dates");

        if(Dates != null && Dates.size()>0)
        {
            for(String s : Dates)
            {
                Log.i("Event Dates", s);
            }

            //선택한 날짜 표시
            startDateTV.setText(Dates.get(0));
            endDateTV.setText(Dates.get(Dates.size()-1));

            //선택한 날짜 객체 저장
            event.setStartdate(Dates.get(0));
            event.setEnddate(Dates.get(Dates.size()-1));
        }
        else
        {
            Log.i("Event Dates : ", "dates is null");
        }
    }
}


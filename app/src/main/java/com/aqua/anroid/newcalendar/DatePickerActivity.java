package com.aqua.anroid.newcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.settings.appearance.ConnectedDayIconPosition;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DatePickerActivity extends AppCompatActivity
{
    private Button datePickerOkBtn;
    private com.applikeysolutions.cosmocalendar.view.CalendarView calendarView;

    private ArrayList<String> Dates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

        initCalender();
        setdateData();

    }

    private void initCalender()
    {
        datePickerOkBtn = findViewById(R.id.datePickerOkBtn);
        calendarView = (com.applikeysolutions.cosmocalendar.view.CalendarView) findViewById(R.id.calendar_view);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        calendarView.setConnectedDaySelectedIconRes(ConnectedDayIconPosition.TOP);
        calendarView.setSelectionType(SelectionType.RANGE);

    }
    private void setdateData()
    {
        Intent intent = new Intent(getApplicationContext(),EventEditActivity.class);

        datePickerOkBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(calendarView.getSelectionManager() instanceof RangeSelectionManager)
                {
                    RangeSelectionManager rangeSelectionManager = (RangeSelectionManager) calendarView.getSelectionManager();

                    // 날짜를 선택했을 때
                    if(rangeSelectionManager.getDays() != null)
                    {
//                        String result = "";

                        ArrayList<Calendar> days = (ArrayList<Calendar>) calendarView.getSelectedDates();

                        for (int i = 0; i < days.size(); i++)
                        {
                            Calendar calendar = days.get(i);
                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                            final int month = calendar.get(Calendar.MONTH);
                            final int year = calendar.get(Calendar.YEAR);
                            String week = new SimpleDateFormat("EE").format(calendar.getTime());

                            String day_full = year + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일";

//                            result += (day_full + "\n");

                            Dates.add(day_full);

                        }
//                        Toast.makeText(DatePickerActivity.this, result, Toast.LENGTH_LONG).show();

                        intent.putStringArrayListExtra("Dates", Dates);
                        startActivity(intent);
                    }
                }
            }
        });
    }


}
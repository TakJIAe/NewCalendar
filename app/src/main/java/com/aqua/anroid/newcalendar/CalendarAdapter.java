package com.aqua.anroid.newcalendar;



import static com.aqua.anroid.newcalendar.Event.eventsList;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;


    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.155555555); //월별 달력

        return new CalendarViewHolder(view, onItemListener, days);
    }

    // 생성된 ViewHolder 에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        //날짜 변수에 담기
        final LocalDate date = days.get(position);
        Event selectedEvent;

        //ID 로 목록 찾고 리스트 호출
        ArrayList<Event> dailyEvents = eventsForDate(CalendarUtils.selectedDate);

        if (date == null)
            //날짜가 null인 경우 홀더 날짜를 설정
            holder.dayOfMonth.setText("");

        else {
            //그렇지 않으면 날짜 넣기
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            //선택한 날짜 회색으로 표시
            if (date.equals(CalendarUtils.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }

            //주말 색상 지정(토요일: BLUE, 일요일: RED)
            if ((position + 1) % 7 == 0) {
                holder.dayOfMonth.setTextColor(Color.BLUE);
            } else if (position == 0 || position % 7 == 0) {
                holder.dayOfMonth.setTextColor(Color.RED);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (eventsList.size()>0) {
                    for (int i = 0; eventsList.size()>i ; i++) {

                        Event CalendarDate1 = eventsList.get(i);
                        Date curr = dateFormat.parse(date.toString());

                        Date d1 = dateFormat.parse(CalendarDate1.startdate);
                        Date d2 = dateFormat.parse(CalendarDate1.enddate);

                        int result1 = curr.compareTo(d1);       // curr > d1
                        int result2 = curr.compareTo(d2);

                        // 조건이 맞을때
                        if ((result1 >= 0) && (result2 <= 0))
                            holder.parentView.setBackgroundColor(Color.GREEN);
                    }

                }
            }
            catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }
    // 전체 데이터 개수 return
    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        //해당 변수 이름은 현재날짜로 변경됨
        void onItemClick(int position, LocalDate date);
    }

    // 주어진 날짜에 대한 모든 이벤트 반환
    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList)
        {
            // 삭제되지 않은 이벤트만 채우기
            if(event.getDeleted()==null)
            {
                //이벤트 날짜가 지난 날짜와 같으면 이벤트 목록에서 해당 이벤트 반환
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String selectDate1 = date.toString();   //현재누른날짜
                String startDate1 = event.getStartdate();  //시작날짜
                String endDate1 = event.getEnddate();  //종료날짜
                Date selectDate = null;


                try {
                    selectDate = dateFormat.parse(selectDate1);

                    Date startDate  = dateFormat.parse(startDate1);
                    Date endDate    = dateFormat.parse(endDate1);

                    int result1 = selectDate.compareTo(startDate);       // curr > d1
                    int result2 = selectDate.compareTo(endDate);

                    // 조건이 맞을때
                    if((result1>=0)&&(result2<=0))
                        events.add(event);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                /*if (event.getDate().equals(date))
                {
                    events.add(event);
                }*/
            }
        }

        return events;
    }

}
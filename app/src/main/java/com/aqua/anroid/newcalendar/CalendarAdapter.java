package com.aqua.anroid.newcalendar;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

/*
    ArrayList<MyEvent> myEvents;
*/

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
        layoutParams.height = (int) (parent.getHeight() * 0.166666666); //월별 달력

/*      if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);*/

        return new CalendarViewHolder(view, onItemListener, days);
    }

    // 생성된 ViewHolder 에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        //날짜 변수에 담기
        final LocalDate date = days.get(position);

        //ID 로 목록 찾고 리스트 호출
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);


        if (date == null)
            //날짜가 null인 경우 홀더 날짜를 설정
            holder.dayOfMonth.setText("");

        else
        {
            //그렇지 않으면 날짜 넣기
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            //선택한 날짜 회색으로 표시
            if (date.equals(CalendarUtils.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
           /* if(!dailyEvents.isEmpty()){

            }*/

            //주말 색상 지정(토요일: BLUE, 일요일: RED)
            if ((position + 1)%7 ==0){
                holder.dayOfMonth.setTextColor(Color.BLUE);
            }else if(position == 0 || position %7 ==0){
                holder.dayOfMonth.setTextColor(Color.RED);
            }



        /*    holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int iyear = date.getYear();
                    int iMonth = date.getMonthValue();
                    int iDay = date.getDayOfMonth();

                    String yearMonDay = iyear + "년" + iMonth + "월" + iDay + "일";

                    Toast.makeText(holder.itemView.getContext(), yearMonDay, Toast.LENGTH_SHORT).show();

                }
            });*/

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


}
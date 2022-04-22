package com.aqua.anroid.newcalendar;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

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
        layoutParams.height = (int) (parent.getHeight() * 0.166666666); //월별 달력

/*      if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);*/

        return new CalendarViewHolder(view, onItemListener,days);
    }

    // 생성된 ViewHolder에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText(""); //날짜가 null일 때 월의 홀더 날짜 설정하려면 텍스트를 빈 문자열로 설정
        else
        {
            //그렇지 않으면 날짜로 설정
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate)) //날짜가 선택한 날짜와 동일한지
                holder.parentView.setBackgroundColor(Color.LTGRAY); // 현재 날짜 회색으로 표시
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
        void onItemClick(int position, LocalDate date);
    }
}
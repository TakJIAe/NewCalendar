package com.aqua.anroid.newcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/* 이벤트 유형의 배열 어댑터 확장 */
public class EventAdapter extends ArrayAdapter<Event>
{
    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        //이벤트 항목 가져옴
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = event.getName() + " " + CalendarUtils.formattedTime(event.getTime());
        eventTitle.setText(eventTitle);
        return convertView;
    }
}

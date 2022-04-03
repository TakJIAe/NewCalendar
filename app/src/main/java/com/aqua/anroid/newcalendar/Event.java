package com.aqua.anroid.newcalendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>(); //이벤트 목록

    // 주어진 날짜에 대한 모든 이벤트 반환
    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList)
        {
            //이벤트 날짜가 지난 날짜와 같으면 이벤트 목록에서 해당 이벤트 반환
           if(event.getDate().equals(date))
                events.add(event);
        }
        return events;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;

    public Event (String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

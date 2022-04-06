package com.aqua.anroid.newcalendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>(); //이벤트 목록
    public static String Event_EDIT_EXTRA = "eventEdit";

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

    private int id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private Date deleted;


    public Event (int id, String name, LocalDate date, LocalTime time)
    {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        deleted = null;

    }

    public static Event getEventForID(int passedEventID)
    {
        for(Event event : eventsList)
        {
            if(event.getId() == passedEventID)
                return event;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
}

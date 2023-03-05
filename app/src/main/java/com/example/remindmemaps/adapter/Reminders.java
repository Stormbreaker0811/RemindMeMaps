package com.example.remindmemaps.adapter;

public class Reminders {
    String reminder,date;


    public Reminders(String reminder, String date){

        this.reminder = reminder;
        this.date = date;

    }


    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.mooo.ewolvy.lasttime.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "tasks")
public class TaskItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int color;
    private String datesHistory;
    private Date lastTime;
    private Date remindOn;
    private int position;

    public TaskItem(int id, String name, int color, String datesHistory, Date lastTime, Date remindOn, int position) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.datesHistory = datesHistory;
        this.lastTime = lastTime;
        this.remindOn = remindOn;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public String getDatesHistory() {
        return datesHistory;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public Date getRemindOn() {
        return remindOn;
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDatesHistory(String datesHistory) {
        this.datesHistory = datesHistory;
    }

    public void setLastTime(Date lastTime) {
        addToHistory(lastTime.toString());
        this.lastTime = lastTime;
    }

    public void setRemindOn(Date remindOn) {
        this.remindOn = remindOn;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // Private helper methods
    private void addToHistory (String newTime){
        if (datesHistory.equals("")){
            datesHistory = newTime;
        } else {
            datesHistory = datesHistory + "\n" + newTime;
        }
    }
}

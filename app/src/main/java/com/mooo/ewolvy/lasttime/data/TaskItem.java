package com.mooo.ewolvy.lasttime.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "tasks", indices = {@Index(value = "position", unique = true)})
public class TaskItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int color;
    private String datesHistory;
    private String lastTime;
    private String remindOn;
    private int position;

    public TaskItem(int id, String name, int color, String datesHistory, String lastTime, String remindOn, int position) {
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

    public String getLastTime() {
        return lastTime;
    }

    public String getRemindOn() {
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

    public void setLastTime(String lastTime) {
        addToHistory(lastTime);
        this.lastTime = lastTime;
    }

    public void setRemindOn(String remindOn) {
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

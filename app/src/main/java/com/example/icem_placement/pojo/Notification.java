package com.example.icem_placement.pojo;

public class Notification {
    public String time;
    public String title;
    public String description;

    public Notification(String time, String title, String description) {
        this.time = time;
        this.title = title;
        this.description = description;
    }

    public Notification() { }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

}

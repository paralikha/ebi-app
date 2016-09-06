package com.ssagroup.ebi_app.models;

import android.graphics.Bitmap;

/**
 * Created by User on 8/10/2016.
 */
public class Schedules {
    public String title;
    public String description;
    public String desc;
    public String date;
    public String day;
    public String id;
    public String month;
    public String time;

    public Schedules(String title, String description, String desc, String date) {
        this.title = title;
        this.description = description;
        this.desc = desc;
        this.date = date;
    }

    public Schedules(String id, String day, String title, String description, String desc, String date, String time) {
        this.id = id;
        this.day = day;
        this.title = title;
        this.description = description;
        this.desc = desc;
        this.date = date;
        this.time = time;

    }
}

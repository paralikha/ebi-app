package com.ssagroup.ebi_app.models;

import android.graphics.Bitmap;

import org.json.JSONArray;

/**
 * Created by User on 8/8/16.
 */
public class Course {
    public String title;
    public String description;
    public Bitmap thumbnail;
    public String id;

    public String summary;
    public JSONArray schedule;
    public String fee;


    public Course(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Course(String id, String title, String description, Bitmap thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Course(String id, String title, Bitmap thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Course(String id, String title, String description, String summary, JSONArray schedule, String fee,  Bitmap thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.summary = summary;
        this.schedule = schedule;
        this.fee = fee;
        this.thumbnail = thumbnail;

    }
}

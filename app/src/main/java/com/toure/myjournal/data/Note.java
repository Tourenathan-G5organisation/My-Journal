package com.toure.myjournal.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Toure Nathan on 6/30/2018.
 */

@Entity
public class Note {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String noteTitle;

    @ColumnInfo(name = "content")
    private String noteContent;

    @ColumnInfo(name = "note_time")
    private Date noteTime;

    private int color;

    @Ignore
    private Calendar calendarTime;

    public Note(int id, String noteTitle, String noteContent, Date noteTime, int color) {
        this.id = id;
        this.noteContent = noteContent;
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.color = color;
    }

    @Ignore
    public Note(String noteTitle, String noteContent, Date noteTime, int color) {
        this.noteContent = noteContent;
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.color = color;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Date getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(Date noteTime) {
        this.noteTime = noteTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Calendar getCalendar() {
        if (calendarTime == null) {
            calendarTime = Calendar.getInstance();
        }
        calendarTime.setTime(this.noteTime);
        return calendarTime;
    }

}

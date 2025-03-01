package com.example.shake_to_undo_notes_app;

public class Note {
    private String title, desc;
    private long time;

    public Note(String title, String desc, long time) {
        this.title = title;
        this.desc = desc;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

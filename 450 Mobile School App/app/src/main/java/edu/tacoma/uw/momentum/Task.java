package edu.tacoma.uw.momentum;

import java.io.Serializable;

public class Task implements Serializable {
    private String mCategory;
    private String mTitle;
    private String mDescription;
    private String mDue;
    private int mId;
    public final static String ID = "id";
    public final static String CATEGORY = "category";
    public final static String TITLE = "title";

    public final static String DESCRIPTION = "description";
    public final static String DUE = "due";

    public Task(int id, String due, String category, String title, String description) {
        this.mId = id;
        this.mDue = due;
        this.mCategory = category;
        this.mTitle = title;
        this.mDescription = description;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDue() {
        return mDue;
    }

    public void setDue(String due) {
        this.mDue = due;
    }
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}


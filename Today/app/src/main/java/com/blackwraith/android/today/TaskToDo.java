package com.blackwraith.android.today;


import java.time.LocalDateTime;
import java.util.UUID;

public class TaskToDo {
    private UUID mID;
    private String mTitle;
    private String mDescription;
    private int mCategory;
    private Boolean mIsCompleted;
    private LocalDateTime mCreationDate;
    private LocalDateTime mDeadlineDate;
    private LocalDateTime mCompletionDate;


    public TaskToDo() {
        mID = UUID.randomUUID();
        mCreationDate = LocalDateTime.now();
        mIsCompleted = false;
        mCategory = 0; //0 - General
    }

    public TaskToDo(UUID uuid) {
        mID = uuid;
        mCreationDate = LocalDateTime.now();
        mIsCompleted = false;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }

    public Boolean getCompleted() {
        return mIsCompleted;
    }

    public void setCompleted(Boolean completed) {
        mIsCompleted = completed;
    }

    public LocalDateTime getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        mCreationDate = creationDate;
    }

    public LocalDateTime getDeadlineDate() {
        return mDeadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        mDeadlineDate = deadlineDate;
    }

    public LocalDateTime getCompletionDate() {
        return mCompletionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        mCompletionDate = completionDate;
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }
}

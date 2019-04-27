package com.artemis.mynotes.Model;

public class NoteModel {
    private String title, content, createDate;

    public NoteModel(String title, String content, String createDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}

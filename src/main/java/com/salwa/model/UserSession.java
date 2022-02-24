package com.salwa.model;

import java.util.List;

public class UserSession {
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    private Long duration;
    private List<String> pages;

    public UserSession(Long duration, List<String> pages, Long startTime) {
        this.duration = duration;
        this.pages = pages;
        this.startTime = startTime;
    }

    private Long startTime;
}

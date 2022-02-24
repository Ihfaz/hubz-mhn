package com.salwa.model;

public class Event {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    private String url;
    private String visitorId;
    private Long timestamp;

    public Event(String url, String visitorId, Long timestamp){
        this.url = url;
        this.visitorId = visitorId;
        this.timestamp = timestamp;
    }
}

package com.salwa.model;

public class SessionData {

    private Long timestamp;
    private String url;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

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

    private String visitorId;

    public SessionData(Long timestamp, String url, String visitorId) {
        this.timestamp = timestamp;
        this.url = url;
        this.visitorId =visitorId;
    }
}

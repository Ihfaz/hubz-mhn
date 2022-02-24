package com.salwa.model;

import java.util.List;
import java.util.Map;

public class SessionByUser {
    public SessionByUser(Map<String, List<UserSession>> sessionsByUser) {
        this.sessionsByUser = sessionsByUser;
    }

    public Map<String, List<UserSession>> getSessionsByUser() {
        return sessionsByUser;
    }

    public void setSessionsByUser(Map<String, List<UserSession>> sessionsByUser) {
        this.sessionsByUser = sessionsByUser;
    }

    private Map<String, List<UserSession>> sessionsByUser;

}

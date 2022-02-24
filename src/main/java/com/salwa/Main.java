package com.salwa;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salwa.model.*;
import java.util.*;

import com.salwa.model.Event;
import java.util.List;

public class Main {
    public static final Long CONSECUTIVE_SESSIONS_MAX_TIME_DIFF = 600000L;

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        String url = "hubspotURL";
        List<Event> eventList = Server.get(url);

        String sessionByUserJson = "";
        SessionByUser sessionByUser = groupUserSessions(eventList);
        try {
            sessionByUserJson = mapper.writeValueAsString(sessionByUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(sessionByUserJson);


//        Server.post(postUrl, sesionByUserJson);
    }

    public static SessionByUser groupUserSessions(List<Event> events) {
        Map<String, PriorityQueue<SessionData>> visitorIdToSequentialListOfSessionDataMap = new HashMap<>();
        Map<String, List<UserSession>> visitorIdToListOfUserSessionMap = new HashMap<>();

        for (Event event : events) {
            if(!visitorIdToSequentialListOfSessionDataMap.containsKey(event.getVisitorId())) {
                visitorIdToSequentialListOfSessionDataMap.put(event.getVisitorId(),
                        new PriorityQueue<>(Comparator.comparing(SessionData::getTimestamp)));
            }
            visitorIdToSequentialListOfSessionDataMap.get(event.getVisitorId())
                    .add(new SessionData(event.getTimestamp(), event.getUrl(), event.getVisitorId()));
        }

        for(String key : visitorIdToSequentialListOfSessionDataMap.keySet()) {
            if(!visitorIdToListOfUserSessionMap.containsKey(key)) {
                visitorIdToListOfUserSessionMap.put(key, new ArrayList<>());
            }
            PriorityQueue<SessionData> pq = visitorIdToSequentialListOfSessionDataMap.get(key);
            List<String> urls = new ArrayList<>();
            if(!pq.isEmpty()) {
                SessionData firstSessionData = pq.poll();
                urls.add(firstSessionData.getUrl());
                Long startTimeStamp = firstSessionData.getTimestamp();
                Long sessionsFirstTimeStamp = firstSessionData.getTimestamp();
                long diff = 0l;
                while (!pq.isEmpty()) {
                    SessionData currentSessionData = pq.poll();
                    if (currentSessionData.getTimestamp() - startTimeStamp <= CONSECUTIVE_SESSIONS_MAX_TIME_DIFF) {
                        urls.add(currentSessionData.getUrl());
                        diff = currentSessionData.getTimestamp() - sessionsFirstTimeStamp;
                        startTimeStamp = currentSessionData.getTimestamp();
                    } else {
                        UserSession userSession = new UserSession(diff, urls, sessionsFirstTimeStamp);
                        visitorIdToListOfUserSessionMap.get(currentSessionData.getVisitorId()).add(userSession);
                        urls = new ArrayList<>();
                        urls.add(currentSessionData.getUrl());
                        startTimeStamp = currentSessionData.getTimestamp();
                        sessionsFirstTimeStamp = currentSessionData.getTimestamp();
                        diff = 0l;
                    }
                }
                UserSession userSession = new UserSession(diff, urls, sessionsFirstTimeStamp);
                visitorIdToListOfUserSessionMap.get(key).add(userSession);
            }
        }
        return new SessionByUser(visitorIdToListOfUserSessionMap);
    }
}
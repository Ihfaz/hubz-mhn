package com.salwa;

import com.salwa.model.Event;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String url = "hubspotURL";
        List<Event> eventList = Server.get(url);
        System.out.println(eventList);

//        String postUrl = "Something";
//        JSONObject payload = new JSONObject("{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\"tenali.ramakrishna@15ce.com\", \"status\":\"active\"}");
//        Server.post(postUrl, payload);
    }
}
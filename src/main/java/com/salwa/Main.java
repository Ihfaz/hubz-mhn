package com.salwa;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) {
        String url = "hubspotURL";
        JSONObject res = Server.get(url);
        System.out.println(res);

        String postUrl = "Something";
        JSONObject payload = new JSONObject("{\"name\":\"Tenali Ramakrishna\", \"gender\":\"male\", \"email\":\"tenali.ramakrishna@15ce.com\", \"status\":\"active\"}");
        Server.post(postUrl, payload);
    }
}
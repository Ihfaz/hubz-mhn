package com.salwa;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.salwa.model.Event;
import org.json.*;

public class Server {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    public static List<Event> get(String urlString) {
        List<Event> eventList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(READ_TIMEOUT);
        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject res = new JSONObject(result.toString());
        JSONArray arr = res.getJSONArray("events");
        for (int i = 0; i < arr.length(); i++){
            JSONObject eventJson = arr.getJSONObject(i);
            Event event = new Event(eventJson.getString("url"), eventJson.getString("visitorId"), eventJson.getLong("timestamp"));
            eventList.add(event);
        }

        return eventList;
    }

    public static int post(String urlString, JSONObject payload){
        int statusCode = 0;
        StringBuilder response = new StringBuilder("");

        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
        } catch (IOException e) {
            e.printStackTrace();
        }

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.setConnectTimeout(CONNECTION_TIMEOUT);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = payload.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(response.toString());
        return statusCode;
    }
}

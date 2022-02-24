package com.salwa;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.salwa.model.Event;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.*;

public class Server {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private static HttpClient httpClient = HttpClientBuilder.create().build();

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

    public static void post(String urlString, String payload){
//        StringBuilder response = new StringBuilder("");

//        URL url;
//        HttpURLConnection con = null;
//        try {
//            url = new URL(urlString);
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        con.setRequestProperty("Content-Type", "application/json; utf-8");
//        con.setRequestProperty("Accept", "application/json");
//        con.setDoOutput(true);
//        con.setConnectTimeout(CONNECTION_TIMEOUT);
//
//        try(OutputStream os = con.getOutputStream()) {
//            byte[] input = payload.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try(BufferedReader br = new BufferedReader(
//                new InputStreamReader(con.getInputStream(), "utf-8"))) {
//            String responseLine;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        try {
            StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);
            HttpPost request = new HttpPost(urlString);
            request.setEntity(entity);
            HttpResponse res = httpClient.execute(request);
            System.out.println(res.getStatusLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

package ru.ifmo.lang.evtushenko.t07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WhiteboardClient {
    public static void main(String[] args) throws IOException {
        final String host = "http://localhost:8080/";
        String request;
        if (args[0].equals("get")){
            request = host+"get";
        } else {
            request = host+"post?message="+args[1];
        }
        URL url = new URL(request);
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String message = reader.readLine();
        reader.close();
        System.out.println(message);
    }
}

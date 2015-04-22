package ru.ifmo.lang.evtushenko.t07;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class WhiteboardServer {

    private static String message = "Null";

    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/get", new GetHandler());
        server.createContext("/post", new PostHandler());
        server.start();
    }

    private static class GetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            httpExchange.sendResponseHeaders(200, message.length());
            final OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(URLEncoder.encode(message, "Unicode").getBytes());
            responseBody.close();
        }
    }

    private static class PostHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            final String successAlert = "Your message has been saved";
            String request = httpExchange.getRequestURI().getQuery();
            message = URLDecoder.decode(request.substring(8, request.length()), "Unicode");
            httpExchange.sendResponseHeaders(200, successAlert.length());
            final OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(successAlert.getBytes());
            responseBody.close();
        }
    }
}

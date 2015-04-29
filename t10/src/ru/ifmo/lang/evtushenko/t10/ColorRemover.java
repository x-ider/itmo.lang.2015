package ru.ifmo.lang.evtushenko.t10;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;

public class ColorRemover {
    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/remove_color", new RemoveColorHandler());
        server.createContext("/help", new HelpHandler());
        server.start();
    }

    private static class HelpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String helpMessage = "<html><div align=center><h2>If you are annoyed by one color that don't let you live" +
                    " free then this service is for you!!!</h2><p>To remove color from picture use next template:<br>" +
                    "http://localhost:8080/remove_color?source=&lt;link to your picture&gt;&color=&lt;name of your " +
                    "color&gt;</p><p>Supported colors:<br>White, Black, Blue, Green, Red, Pink, Yellow, Orange, Gray</p></div><html>";
            httpExchange.sendResponseHeaders(200, helpMessage.length());
            final OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(helpMessage.getBytes());
            responseBody.close();
        }
    }

    private static BufferedImage deleteColor(BufferedImage image, Color color) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (image.getRGB(i, j) > color.getRGB() - 1000000 && image.getRGB(i, j) < color.getRGB() + 1000000) {
                    image.setRGB(i, j, Color.OPAQUE);
                }
            }
        }
        return image;
    }

    private static Color defineColor(String source) {
        Color color;
        switch (source) {
            case "white":
                color = Color.WHITE;
                break;
            case "black":
                color = Color.BLACK;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "green":
                color = Color.GREEN;
                break;
            case "red":
                color = Color.RED;
                break;
            case "pink":
                color = Color.PINK;
                break;
            case "yellow":
                color = Color.YELLOW;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            default:
                color = Color.WHITE;
                break;
        }
        return color;
    }

    private static class RemoveColorHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String request = httpExchange.getRequestURI().getQuery();
            String source = request.substring(request.indexOf("=") + 1, request.indexOf("&"));
            String color = request.substring(request.lastIndexOf("=") + 1);
            URL url = new URL(source);
            BufferedImage image = deleteColor(ImageIO.read(url), defineColor(color));
            WritableRaster raster = image.getRaster();
            DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
            httpExchange.sendResponseHeaders(200, data.getData().length);
            final OutputStream responseBody = httpExchange.getResponseBody();
            ImageIO.write(image, "png", responseBody);
            responseBody.close();
        }
    }

}
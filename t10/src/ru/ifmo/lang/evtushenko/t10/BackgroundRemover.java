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

public class BackgroundRemover {
    public static void main(String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/clear_background", new ClearBackgroundHandler());
        server.start();
    }

    private static class ClearBackgroundHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String request = httpExchange.getRequestURI().getQuery();
            URL url = new URL(request.substring(7, request.length()));
            BufferedImage image = ImageIO.read(url);
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    if (image.getRGB(i, j) == Color.WHITE.getRGB()) {
                        image.setRGB(i, j, Color.OPAQUE);
                    }
                }
            }
            WritableRaster raster = image.getRaster();
            DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
            httpExchange.sendResponseHeaders(200, data.getData().length);
            final OutputStream responseBody = httpExchange.getResponseBody();
            ImageIO.write(image, "png", responseBody);
            responseBody.close();
        }
    }

}


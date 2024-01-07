package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public static String runApi(String path, String arguments) throws Exception {
        URL url = createUrl(path, arguments);
        HttpURLConnection connection = createConnection(url);

        if (connection.getResponseCode() != 200) {
            throw new Exception("HTTP Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
        }

        return processResponse(connection);
    }


    protected static URL createUrl(String path, String arguments) throws Exception {
        String sUrl = "http://api.recrm.ru/json" + path + "?key=" + "43a0cef4dd3b4c818a5328154582ef5d";

        if (!arguments.isEmpty()) {
            sUrl += "&" + arguments;
        }

        return new URL(sUrl);
    }


    static HttpURLConnection createConnection(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        return connection;
    }


    static String processResponse(HttpURLConnection connection) throws Exception {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        )) {
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                output.append(line);
            }

            return output.toString();
        } finally {
            connection.disconnect();
        }
    }
}

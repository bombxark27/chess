package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpCommunicator {
    private String serverUrl;
    private final Gson serializer = new Gson();

    public HttpCommunicator(String url) {
        this.serverUrl = url;
    }

    public <T> T makeRequest(String method, String url, Object request, Class<T> responseClass, String authToken) throws ResponseException{
        HttpURLConnection http = null;
        try {
            URL urlObj = (new URI(serverUrl + url).toURL());
            http = (HttpURLConnection) urlObj.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (authToken != null) {
                writeHeaders(authToken, http);
            }
            else {
                http.addRequestProperty("Content-Type", "application/json");
            }

            if (method != "GET") {
                writeBody(request, http);
            }

            http.connect();

            return readBody(http,responseClass);

        } catch (Exception e) {
            throw new ResponseException(500,e.getMessage());
        }

    }

    private void writeBody(Object request, HttpURLConnection http) throws IOException {
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = serializer.toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
    }

    private void writeHeaders(String authToken, HttpURLConnection http) throws IOException {
        http.addRequestProperty("Content-Type", "application/json");
        http.addRequestProperty("Authorization", authToken);
    }

    private <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader reader = new InputStreamReader(respBody);
            if (responseClass != null) {
                response = serializer.fromJson(reader, responseClass);
            }
        }
        return response;
    }

}

package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpCommunicator {
    private String serverUrl;
    private final Gson serializer = new Gson();

    public HttpCommunicator(String url) {
        this.serverUrl = url;
    }

    public <T> T makeRequest(String method, String url, Object request, Class<T> responseClass, String authToken) throws Exception{
        HttpURLConnection http = null;
        try {
            URL urlObj = (new URI(serverUrl + url).toURL());
            http = (HttpURLConnection) urlObj.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            http.connect();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return null;
    }

    private void writeBody(Object request, HttpURLConnection http) throws IOException {
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = serializer.toJson(request);
            outputStream.write(jsonBody.getBytes());
        }
    }

}

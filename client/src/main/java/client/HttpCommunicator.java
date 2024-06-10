package client;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpCommunicator {
    private String serverUrl;

    public HttpCommunicator(String url) {
        this.serverUrl = url;
    }

    public <T> T makeRequest(String method, String url, Class<T> responseType) throws Exception{
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

}

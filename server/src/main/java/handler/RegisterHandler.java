package handler;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class RegisterHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().equals("POST")){

                String reqBody = exchange.getRequestBody().toString();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
        }
        catch(IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

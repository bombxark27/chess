package client;

import model.AuthData;
import model.UserData;

public class ServerFacade {
    private HttpCommunicator httpCommunicator;
    String authToken;

    public ServerFacade(String url) {
        httpCommunicator = new HttpCommunicator(url);
        authToken = null;
    }

    public AuthData register(String username, String password, String email) throws Exception {
        UserData newUser = new UserData(username, password, email);
        AuthData authData = httpCommunicator.makeRequest("POST","/user",newUser, AuthData.class,authToken);
        authToken = authData.authToken();
        return authData;
    }

    public AuthData login(String username, String password) throws Exception {
        UserData loginUser = new UserData(username,password,null);
        AuthData authData = httpCommunicator.makeRequest("POST","/session",loginUser, AuthData.class,authToken);
        authToken = authData.authToken();
        return authData;
    }

    public void logout() throws Exception {
        httpCommunicator.makeRequest("DELETE","/session",null,null,authToken);
    }

    public void createGame() throws Exception{

    }

    public void listGames() throws Exception {

    }

    public void playGame() {

    }

    public void observeGame() {

    }

}

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

    public void register(String username, String password, String email) throws Exception {
        UserData newUser = new UserData(username, password, email);
        AuthData authData = httpCommunicator.makeRequest("POST","/user",newUser, AuthData.class,authToken);
    }

    public void login(String username, String password) {

    }

    public void logout() {

    }

    public void createGame() {

    }

    public void listGames() {

    }

    public void playGame() {

    }

    public void observeGame() {

    }

}

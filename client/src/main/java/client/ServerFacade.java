package client;

import model.AuthData;
import model.UserData;

public class ServerFacade {
    private HttpCommunicator httpCommunicator;

    public AuthData register(String username, String password, String email) {
        UserData newUser = new UserData(username, password, email);


        return null;
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

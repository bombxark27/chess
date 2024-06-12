package client;

import model.AuthData;
import model.GameData;
import model.UserData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import result.CreateGameResult;
import result.ListGamesResult;

import java.util.Collection;

public class ServerFacade {
    private HttpCommunicator httpCommunicator;
    String authToken;

    public ServerFacade(String url) {
        httpCommunicator = new HttpCommunicator(url);
        authToken = null;
    }

    public AuthData register(String username, String password, String email) throws ResponseException {
        UserData newUser = new UserData(username, password, email);
        AuthData authData = httpCommunicator.makeRequest("POST","/user",newUser, AuthData.class,authToken);
        authToken = authData.authToken();
        return authData;
    }

    public AuthData login(String username, String password) throws ResponseException {
        UserData loginUser = new UserData(username,password,null);
        AuthData authData = httpCommunicator.makeRequest("POST","/session",loginUser, AuthData.class,authToken);
        authToken = authData.authToken();
        return authData;
    }

    public void logout() throws ResponseException {
        httpCommunicator.makeRequest("DELETE","/session",null,null,authToken);
        authToken = null;
    }

    public boolean noAuthorization() {
        return authToken == null;
    }

    public int createGame(String gameName) throws ResponseException{
        CreateGameRequest createGameRequest = new CreateGameRequest(gameName);
        CreateGameResult createGameResult = httpCommunicator.makeRequest("POST","/game",createGameRequest, CreateGameResult.class,authToken);
        return createGameResult.gameID();
    }

    public Collection<GameData> listGames() throws ResponseException {
        ListGamesResult listGamesResult = httpCommunicator.makeRequest("GET","/game",null, ListGamesResult.class,authToken);
        return listGamesResult.games();
    }

    public void playGame(String playerColor, int gameID) throws ResponseException {
        JoinGameRequest joinGameRequest = new JoinGameRequest(playerColor,gameID);
        httpCommunicator.makeRequest("PUT","/game",joinGameRequest,null,authToken);
    }

    public void observeGame(int gameID) {

    }

}

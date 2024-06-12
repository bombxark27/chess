package client;

import com.google.gson.Gson;
import model.AuthData;

import java.util.Arrays;

import static client.State.*;

public class ChessClient {

    private final String SIGNED_OUT_HELP =
            """
            register <USERNAME> <PASSWORD> <EMAIL> - to create an account
            login <USERNAME> <PASSWORD> - to play chess
            quit - playing chess
            help - with possible commands
            """;
    private final String LOGGED_IN_HELP =
            """
            createGame <NAME> - creates a new game
            listGames - list all games
            joinGame <ID> [WHITE|BLACK] - joins a game
            observe <ID> - observe a game
            logout - when you are done
            quit - playing chess
            help - with possible commands
            """;

    private ServerFacade facade;
    private String serverURL;
    private State state;

    public ChessClient(String serverURL) {
        facade = new ServerFacade(serverURL);
        this.serverURL = serverURL;
        state = SIGNED_OUT;

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logout();
                case "creategame" -> createGame(params);
                case "listgames" -> listGames();
                case "joingame" -> joinGame(params);
                case "observegame" -> observeGame(params);
                case "quit" -> throw new Exception("quit");
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException{
        if (params.length >= 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            AuthData authData = facade.register(username,password,email);
            state = SIGNED_IN;
            return String.format("You registered as %s", authData.username());
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    public String login(String... params) throws ResponseException{
        if (params.length >= 2) {
            var username = params[0];
            var password = params[1];
            AuthData authData = facade.login(username,password);
            state = SIGNED_IN;
            return String.format("You logged in as %s", authData.username());
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD>");
    }

    public String logout() throws ResponseException{
        isSignedIn();
        facade.logout();
        state = SIGNED_OUT;
        return "You logged out";
    }

    public String createGame(String... params) throws ResponseException{
        isSignedIn();
        if (params.length >= 1) {
            var gameName = params[0];
            int gameID = facade.createGame(gameName);
            return String.format("You created a game with ID %d", gameID);
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }

    public String listGames() throws ResponseException{
        isSignedIn();
        var games = facade.listGames();
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : games){
            result.append(game.toString());
            result.append('\n');
//            result.append(gson.toJson(game)).append("\n");
        }

        return result.toString();
    }

    public String joinGame(String... params) throws ResponseException{
        isSignedIn();
        if (params.length >= 2) {
            var playerColor = params[1];
            var gameID = Integer.parseInt(params[0]);
            facade.playGame(playerColor,gameID);
            return String.format("You joined the game with ID %d", gameID);
        }
        throw new ResponseException(400, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observeGame(String... params) throws ResponseException{
        isSignedIn();
        if (params.length >= 1) {
            var gameID = Integer.parseInt(params[0]);
            return facade.observeGame(gameID);
        }
        throw new ResponseException(400, "Expected: <ID>");
    }

    public String help() {
        if (state == SIGNED_OUT) {
            return SIGNED_OUT_HELP;
        }
        return LOGGED_IN_HELP;
    }

    private void isSignedIn() throws ResponseException {
        if (state == SIGNED_OUT) {
            throw new ResponseException(400, "You need to sign in");
        }
    }
}

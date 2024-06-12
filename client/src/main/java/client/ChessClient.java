package client;

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
            joinGame [WHITE|BLACK] <ID> - joins a game
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
//            return switch (cmd) {
//                case "register" -> register(params);
//                case "login" -> login(params);
//                case "logout" -> logout();
//                case "creategame" -> createGame(params);
//                case "listgames" -> listGames();
//                case "joingame" -> joinGame(params);
//                case "observegame" -> observeGame(params);
//                case "quit" -> "quit";
//                default -> help();
//            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return null;
    }

    public String register(String... params) throws ResponseException{
        if (params.length >= 3) {
            var username = params[0];
            var password = params[1];
            var email = params[2];
            AuthData authData = facade.register(username,password,email);
            return String.format("You signed in as %s", authData.username());
        }
        throw new ResponseException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>");
    }

    private void isSignedIn() throws ResponseException {
        if (state == SIGNED_OUT) {
            throw new ResponseException(400, "You need to sign in");
        }
    }
}

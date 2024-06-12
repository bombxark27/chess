package client;

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
}

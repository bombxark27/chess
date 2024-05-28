package server;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.*;
import spark.*;

import java.util.Collection;

public class Server {
    private final ClearService clearService = new ClearService();
    private final CreateGameService createGameService = new CreateGameService();
    private final JoinGameService joinGameService = new JoinGameService();
    private final ListGamesService listGamesService = new ListGamesService();
    private final LoginService loginService = new LoginService();
    private final LogoutService logoutService = new LogoutService();
    private final RegisterService registerService = new RegisterService();
    private final Gson serializer = new Gson();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (request, response) -> {
            clearService.clearDatabase();
            return "{}";
        });

        Spark.post("/user", (request, response) -> {
            AuthData authData = registerService.register(serializer.fromJson(request.body(), UserData.class));
            return serializer.toJson(authData);
        });

        Spark.post("/session" , (request,response) -> {
            AuthData authData = loginService.login(serializer.fromJson(request.body(), UserData.class));
            return serializer.toJson(authData);
        });

        Spark.delete("/session", (request,response) -> {
            logoutService.logout(request.headers("Authorization"));
            return "{}";
        });

        Spark.get("/game", (request, response) -> {
            Collection<GameData> games = listGamesService.listGames(request.headers("Authorization"));
            return serializer.toJson(games);
        });

        Spark.post("/game", (request,response) -> {
            int gameID = createGameService.createGame(serializer.fromJson(request.body(),String.class),request.headers("Authorization"));
            return serializer.toJson(gameID);
        });

        Spark.put("/game", (request,response) -> {
            String authToken = request.headers("Authorization");
            serializer.fromJson(request.body(),String.class);

            return "{}";
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

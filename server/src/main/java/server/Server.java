package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import result.CreateGameResult;
import result.ListGamesResult;
import result.AlreadyTakenResult;
import result.BadRequestResult;
import result.InternalServiceErrorResult;
import result.UnauthorizedResult;
import result.FailureResult;
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
            response.status(200);
            return serializer.toJson(authData);
        });

        Spark.post("/session" , (request,response) -> {
            UserData userData = serializer.fromJson(request.body(), UserData.class);
            AuthData authData = loginService.login(userData);
            return serializer.toJson(authData);
        });

        Spark.delete("/session", (request,response) -> {
            logoutService.logout(request.headers("Authorization"));
            response.status(200);
            return "{}";
        });

        Spark.get("/game", (request, response) -> {
            Collection<GameData> games = listGamesService.listGames(request.headers("Authorization"));
            ListGamesResult listGamesResult = new ListGamesResult(games);
            return serializer.toJson(listGamesResult);
        });

        Spark.post("/game", (request,response) -> {

            CreateGameRequest createGameRequest = serializer.fromJson(request.body(), CreateGameRequest.class);
            int gameID = createGameService.createGame(String.valueOf(createGameRequest),request.headers("Authorization"));

            CreateGameResult createGameResult = new CreateGameResult(gameID);

            return serializer.toJson(createGameResult);
        });

        Spark.put("/game", (request,response) -> {
            String authToken = request.headers("Authorization");
            JoinGameRequest joinGameRequest = serializer.fromJson(request.body(), JoinGameRequest.class);
            joinGameService.joinGame(authToken, joinGameRequest.playerColor(), joinGameRequest.gameID());

            response.status(200);
            return "{}";
        });



        Spark.exception(BadRequestResult.class, (exception, request, response) -> {
            response.status(400);
            response.body(serializer.toJson(new FailureResult("Error: bad request")));
        });
        Spark.exception(UnauthorizedResult.class, (exception, request, response) -> {
            response.status(401);
            response.body(serializer.toJson(new FailureResult("Error: unauthorized")));
        });
        Spark.exception(AlreadyTakenResult.class, (exception, request, response) -> {
            response.status(403);
            response.body(serializer.toJson(new FailureResult("Error: already taken")));
        });
        Spark.exception(DataAccessException.class, (exception, request, response) -> {
            response.status(500);
            response.body(serializer.toJson(new FailureResult(STR."Error: \{exception.getMessage()}")));
        });
        Spark.exception(InternalServiceErrorResult.class, (exception, request, response) -> {
            response.status(500);
            response.body(serializer.toJson(new FailureResult(STR."Error: \{exception.getMessage()}")));
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

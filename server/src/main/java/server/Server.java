package server;

import com.google.gson.Gson;
import service.*;
import spark.*;

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

//        Spark.post("/user", (request, response) -> {
//
//            registerService.register(request);
//
//        });



        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}

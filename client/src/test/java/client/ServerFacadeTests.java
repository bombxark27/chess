package client;

import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private static HttpCommunicator communicator;

    @BeforeAll
    public static void init() throws Exception {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String url = "http://localhost:" + port + "/";
        facade = new ServerFacade(url);
        communicator = new HttpCommunicator(url);
    }

    @BeforeEach
    public void setUp() throws ResponseException {
        communicator.makeRequest("DELETE","/db",null,null,null);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Good Register")
    void register() throws Exception {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
        assertFalse(facade.noAuthorization());
    }

    @Test
    @DisplayName("Bad Register")
    void badRegister() throws Exception {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () ->
                facade.register("player1", "password", "p1@email.com"));
    }

    @Test
    @DisplayName("Good Login")
    void login() throws ResponseException {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
        var loginData = facade.login("player1", "password");
        assertTrue(loginData.authToken().length() > 10);
    }

    @Test
    @DisplayName("Bad Login: user does not exist")
    void badLoginUser() throws ResponseException {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
        assertThrows(ResponseException.class, () -> facade.login("player2", "password"));
    }

    @Test
    @DisplayName("Bad Login: invalid password")
    void badLoginPassword() throws ResponseException {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
        assertThrows(ResponseException.class, () -> facade.login("player1", "wrongpassword"));
    }

    @Test
    @DisplayName("Good Logout")
    void logout() throws ResponseException {
        facade.register("player1", "password", "p1@email.com");
        facade.login("player1", "password");
        assertFalse(facade.noAuthorization());
        facade.logout();
        assertTrue(facade.noAuthorization());
    }

    @Test
    @DisplayName("Bad Logout")
    void badLogout() throws ResponseException {
        assertThrows(ResponseException.class, () -> facade.logout());
    }

    @Test
    @DisplayName("Good Create Game")
    void createGame() throws ResponseException {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
        var gameID = facade.createGame("game1");
        assertTrue(gameID > 0);
    }

    @Test
    @DisplayName("Bad Create Game")
    void badCreateGame() throws ResponseException {
        var authData = facade.register("player1", "password", "p1@email.com");
        facade.createGame("game1");
        assertThrows(ResponseException.class, () -> facade.createGame("game1"));
    }

    @Test
    @DisplayName("Good List Games")
    void listGames() throws ResponseException {
        facade.register("player1", "password", "p1@email.com");
        Collection<GameData> games = facade.listGames();
        assertEquals(new ArrayList<GameData>(), games);
    }

    @Test
    @DisplayName("List Not Empty")
    void listNotEmpty() throws ResponseException {
        facade.register("player1", "password", "p1@email.com");
        facade.createGame("game1");
        Collection<GameData> games = facade.listGames();
        assertFalse(games.isEmpty());
    }

    @Test
    @DisplayName("Bad List Games")
    void badListGames() throws ResponseException {
        assertThrows(ResponseException.class, () -> facade.listGames());
    }

    @Test
    @DisplayName("Good Play Game")
    void playGame() throws ResponseException {
        facade.register("player1", "password", "p1@email.com");
        int gameID = facade.createGame("game1");
        facade.playGame("white",gameID);
        assertFalse(facade.noAuthorization());
    }

    @Test
    @DisplayName("Bad Play Game")
    void badPlayGame() throws ResponseException {
        facade.register("player1", "password", "p1@email.com");
        assertThrows(ResponseException.class, () -> facade.playGame("white",0));
    }

    @Test
    @DisplayName("Observe Game")
    void observeGame() throws ResponseException {
        facade.register("player1", "password", "p1@email.com");
        int gameID = facade.createGame("game1");
        facade.observeGame(gameID);
        assertTrue(true);
    }
}

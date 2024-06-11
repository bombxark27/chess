package client;

import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
    }

    @Test
    @DisplayName("Bad Register")
    void badRegister() throws Exception {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertThrows(Exception.class, () ->
                facade.register("player1", "password", "p1@email.com"));
    }

    @Test
    @DisplayName("Good Login")
    void login() throws Exception {
        var authData = facade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
        var loginData = facade.login("player1", "password");
        assertTrue(loginData.authToken().length() > 10);
    }
}

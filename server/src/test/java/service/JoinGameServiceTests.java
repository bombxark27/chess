package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class JoinGameServiceTests {
    private MemoryGameDAO gameDataAccess;
    private MemoryAuthDAO authDataAccess;
    private CreateGameService createGameService;
    private JoinGameService joinGameService;
    private Collection<GameData> expectedGames;

    @BeforeEach
    public void setUp(){
        gameDataAccess = new MemoryGameDAO();
        authDataAccess = new MemoryAuthDAO();
        createGameService = new CreateGameService();
        joinGameService = new JoinGameService();
        expectedGames = new ArrayList<>();

        ClearService clearService = new ClearService();
        clearService.clearDatabase();
    }

    @Test
    @DisplayName("Valid Join")
    public void validJoin() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        int gameID = createGameService.createGame(authToken,"validGame");

        GameData expectedGame = new GameData(gameID,"reg23",null,"validGame",new ChessGame());
        expectedGames.add(expectedGame);

        joinGameService.joinGame(authToken,"white",gameID);

        Assertions.assertFalse(gameDataAccess.listGames().isEmpty());
        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

        expectedGames.clear();

        UserData secondUser = new UserData("john","keyboard","john@email.com");
        String secondAuthToken = authDataAccess.createAuth(secondUser.username());

        GameData otherGame = new GameData(gameID,"reg23","john","validGame",new ChessGame());
        expectedGames.add(otherGame);

        joinGameService.joinGame(secondAuthToken,"black",gameID);

        Assertions.assertFalse(gameDataAccess.listGames().isEmpty());
        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

    }

    @Test
    @DisplayName("White Player Taken")
    public void whitePlayerTaken() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        int gameID = createGameService.createGame(authToken,"validGame");

        GameData expectedGame = new GameData(gameID,"reg23",null,"validGame",new ChessGame());
        expectedGames.add(expectedGame);

        joinGameService.joinGame(authToken,"white",gameID);

        Assertions.assertFalse(gameDataAccess.listGames().isEmpty());
        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

        UserData secondUser = new UserData("john","keyboard","john@email.com");
        String secondAuthToken = authDataAccess.createAuth(secondUser.username());

        Assertions.assertThrows(RuntimeException.class, () ->
                joinGameService.joinGame(secondAuthToken,"white",gameID));

    }

    @Test
    @DisplayName("Black Player Taken")
    public void blackPlayerTaken() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        int gameID = createGameService.createGame(authToken,"validGame");

        GameData expectedGame = new GameData(gameID,null,"reg23","validGame",new ChessGame());
        expectedGames.add(expectedGame);

        joinGameService.joinGame(authToken,"black",gameID);

        Assertions.assertFalse(gameDataAccess.listGames().isEmpty());
        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

        UserData secondUser = new UserData("john","keyboard","john@email.com");
        String secondAuthToken = authDataAccess.createAuth(secondUser.username());

        Assertions.assertThrows(RuntimeException.class, () ->
                joinGameService.joinGame(secondAuthToken,"black",gameID));
    }

    @Test
    @DisplayName("Unauthorized Join")
    public void unauthorizedJoin() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        int gameID = createGameService.createGame(authToken,"validGame");

        Assertions.assertThrows(RuntimeException.class, () ->
                joinGameService.joinGame("wrongToken","white",gameID));

    }

    @Test
    @DisplayName("Invalid Game")
    public void invalidGame() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        Assertions.assertThrows(RuntimeException.class, () ->
                joinGameService.joinGame(authToken,"white",123456));

    }


}

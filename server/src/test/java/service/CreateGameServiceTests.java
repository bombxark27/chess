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

public class CreateGameServiceTests {
    private MemoryGameDAO gameDataAccess;
    private MemoryAuthDAO authDataAccess;
    private CreateGameService createGameService;
    private Collection<GameData> expectedGames;

    @BeforeEach
    public void setUp(){
        gameDataAccess = new MemoryGameDAO();
        authDataAccess = new MemoryAuthDAO();
        createGameService = new CreateGameService();
        expectedGames = new ArrayList<>();
        ClearService clearService = new ClearService();
        clearService.clearDatabase();
    }

    @Test
    @DisplayName("Create Valid Game")
    public void createValidGame(){
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        int gameID = createGameService.createGame(authToken,"validGame");

        GameData expectedGame = new GameData(gameID,null,null,"validGame",new ChessGame());
        expectedGames.add(expectedGame);

        Assertions.assertFalse(gameDataAccess.listGames().isEmpty());
        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

        int otherID = createGameService.createGame(authToken,"anotherValidGame");

        GameData otherGame = new GameData(otherID,null,null,"anotherValidGame",new ChessGame());
        expectedGames.add(otherGame);

        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

    }

    @Test
    @DisplayName("GameAlreadyExists")
    public void gameAlreadyExists(){
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        createGameService.createGame(authToken,"validGame");

        Assertions.assertThrows(RuntimeException.class, () -> createGameService.createGame(authToken,"validGame"));


    }

    @Test
    @DisplayName("Unauthorized Creation")
    public void unauthorizedCreation(){
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        Assertions.assertThrows(RuntimeException.class, () ->
                createGameService.createGame("invalidToken","error"));
    }

}

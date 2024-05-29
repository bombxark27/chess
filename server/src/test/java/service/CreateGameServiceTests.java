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
import result.BadRequestResult;
import result.UnauthorizedResult;

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
    public void createValidGame() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        int gameID = createGameService.createGame("validGame", authToken);

        GameData expectedGame = new GameData(gameID,null,null,"validGame",new ChessGame());
        expectedGames.add(expectedGame);

        Assertions.assertFalse(gameDataAccess.listGames().isEmpty());
        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

        int otherID = createGameService.createGame("anotherValidGame", authToken);

        GameData otherGame = new GameData(otherID,null,null,"anotherValidGame",new ChessGame());
        expectedGames.add(otherGame);

        Assertions.assertEquals(expectedGames,gameDataAccess.listGames());

    }

    @Test
    @DisplayName("GameAlreadyExists")
    public void gameAlreadyExists() throws Exception{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        createGameService.createGame("validGame",authToken);

        Assertions.assertThrows(BadRequestResult.class, () -> createGameService.createGame("validGame", authToken));


    }

    @Test
    @DisplayName("Unauthorized Creation")
    public void unauthorizedCreation(){
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        Assertions.assertThrows(UnauthorizedResult.class, () ->
                createGameService.createGame("error","invalidToken"));
    }

}

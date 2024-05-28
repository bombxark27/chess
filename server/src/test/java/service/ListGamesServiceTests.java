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

public class ListGamesServiceTests {
    private MemoryGameDAO gameDataAccess;
    private MemoryAuthDAO authDataAccess;
    private ListGamesService listGamesService;
    private Collection<GameData> expectedGames;

    @BeforeEach
    public void setUp(){
        gameDataAccess = new MemoryGameDAO();
        authDataAccess = new MemoryAuthDAO();
        listGamesService = new ListGamesService();
        expectedGames = new ArrayList<>();
        ClearService clearService = new ClearService();
        clearService.clearDatabase();
    }

    @Test
    @DisplayName("Return Empty Games")
    public void returnEmptyGames() throws RuntimeException{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());
        Collection<GameData> actualGames = listGamesService.listGames(authToken);
        Assertions.assertEquals(expectedGames,actualGames);
        Assertions.assertTrue(actualGames.isEmpty());
    }


    @Test
    @DisplayName("Small Amount Games")
    public void smallAmountGames() throws RuntimeException{
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        String authToken = authDataAccess.createAuth(newUser.username());

        GameData game1 = new GameData(1234,null,null,"game1",new ChessGame());
        GameData game2 = new GameData(2341,null,null,"game2",new ChessGame());
        GameData game3 = new GameData(3412,null,null,"game3",new ChessGame());
        GameData game4 = new GameData(4123,null,null,"game4",new ChessGame());

        try {
            gameDataAccess.insertGame(game1);
            gameDataAccess.insertGame(game2);
            gameDataAccess.insertGame(game3);
            gameDataAccess.insertGame(game4);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        expectedGames.add(game1);
        expectedGames.add(game2);
        expectedGames.add(game3);
        expectedGames.add(game4);

        Collection<GameData> actualGames = listGamesService.listGames(authToken);

        Assertions.assertEquals(expectedGames,actualGames);

    }

    @Test
    @DisplayName("Not Authorized")
    public void notAuthorized() throws RuntimeException{
        Assertions.assertThrows(RuntimeException.class,() -> listGamesService.listGames("unauthorizedToken"));
    }
}

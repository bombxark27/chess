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
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ClearServiceTests {
    private ClearService service;
    private HashMap<String,AuthData> expectedAuthDAO;
    private Collection<GameData> expectedGameDAO;
    private Collection<UserData> expectedUserDAO;

    @BeforeEach
    public void setUp(){
        service = new ClearService();
        expectedAuthDAO = new HashMap<String,AuthData>();
        expectedGameDAO = new ArrayList<GameData>();
        expectedUserDAO = new ArrayList<UserData>();
    }

    @Test
    public void clearDatabaseTest()  {
        UserData user = new UserData("abc","123","user@email.com");
        AuthData auth = new AuthData("abc","12ab34cd56ef");
        GameData game = new GameData(1234,null,null,"newGame",new ChessGame());
        MemoryUserDAO userDatabase = new MemoryUserDAO();
        MemoryAuthDAO authDatabase = new MemoryAuthDAO();
        MemoryGameDAO gameDatabase = new MemoryGameDAO();
        try {
            userDatabase.insertUser(user);
            authDatabase.createAuth(auth.username());
            gameDatabase.insertGame(game);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        service.clearDatabase();
        Assertions.assertEquals(expectedAuthDAO,authDatabase.authDataInDatabase());
        Assertions.assertEquals(expectedGameDAO,gameDatabase.listGames());
        Assertions.assertEquals(expectedUserDAO,userDatabase.usersInDatabase());


    }
}

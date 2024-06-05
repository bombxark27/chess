package dataaccess;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SQLTestsDAO {
    private SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
    private SQLGameDAO sqlGameDAO = new SQLGameDAO();
    private SQLUserDAO sqlUserDAO = new SQLUserDAO();
    private static DatabaseManager db;

    public SQLTestsDAO() throws Exception {
    }

    @BeforeAll
    public static void createDatabase() throws DataAccessException {
        db = new DatabaseManager();

        db.createDatabase();


    }

    @BeforeEach
    public void setUp() throws Exception {
//        sqlUserDAO = new SQLUserDAO();
        sqlAuthDAO.clearAuth();
        sqlGameDAO.clearGame();
        sqlUserDAO.clearUser();
    }

    @Test
    @DisplayName("Clear Tables Tests")
    public void clearTablesTests() throws Exception {

        UserData user = new UserData("admin", "admin", "admin");
        AuthData auth = new AuthData("admin", "admin");
        GameData game = new GameData(5,"admin","admin","admin",new ChessGame());
        sqlAuthDAO.insertAuth(auth);
        sqlGameDAO.insertGame(game);
        sqlUserDAO.insertUser(user);



    }
}

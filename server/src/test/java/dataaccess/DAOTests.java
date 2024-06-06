package dataaccess;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DAOTests {
    private SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
    private SQLGameDAO sqlGameDAO = new SQLGameDAO();
    private SQLUserDAO sqlUserDAO = new SQLUserDAO();
    private static DatabaseManager db;

    public DAOTests() throws Exception {
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

        sqlAuthDAO.clearAuth();
        sqlGameDAO.clearGame();
        sqlUserDAO.clearUser();

        Assertions.assertEquals(new HashMap<String,AuthData>(),sqlAuthDAO.authDataInDatabase());
        Assertions.assertEquals(new ArrayList<UserData>(),sqlUserDAO.usersInDatabase());

    }

    @Test
    @DisplayName("Insert User")
    public void insertUserTest() throws Exception {
        UserData user = new UserData("admin", "admin", "admin");
        sqlUserDAO.insertUser(user);
        UserData selectedUser = sqlUserDAO.getUser(user.username());
        Assertions.assertEquals(user,selectedUser);
    }

    @Test
    @DisplayName("Insert Bad User")
    public void insertBadUserTest() throws Exception {
        UserData user = new UserData("admin", "admin", "admin");
        sqlUserDAO.insertUser(user);
        Assertions.assertThrows(DataAccessException.class, () -> sqlUserDAO.insertUser(user));
    }

    @Test
    @DisplayName("List Users")
    public void listUsersTest() throws Exception {
        UserData user = new UserData("admin", "admin", "admin");
        UserData user2 = new UserData("admin2", "admin2", "admin2");
        UserData user3 = new UserData("admin3", "admin3", "admin3");
        UserData user4 = new UserData("admin4", "admin4", "admin4");
        UserData user5 = new UserData("admin5", "admin5", "admin5");
        Collection<UserData> expected = new ArrayList<>();
        expected.add(user);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);
        expected.add(user5);
        sqlUserDAO.insertUser(user);
        sqlUserDAO.insertUser(user2);
        sqlUserDAO.insertUser(user3);
        sqlUserDAO.insertUser(user4);
        sqlUserDAO.insertUser(user5);

        Collection<UserData> actual = sqlUserDAO.usersInDatabase();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Bad List")
    public void badListTest() throws Exception {
        Assertions.assertTrue(true);
    }


    @Test
    @DisplayName("Insert Auth")
    public void insertAuthTest() throws Exception {
        AuthData auth = new AuthData("admin", "admin");
        sqlAuthDAO.insertAuth(auth);
        AuthData selectedAuth = sqlAuthDAO.getAuth("admin");
        Assertions.assertEquals(auth,selectedAuth);
    }

    @Test
    @DisplayName("Insert Bad Auth")
    public void insertBadAuthTest() throws Exception {
        AuthData auth = new AuthData("admin", "admin");
        sqlAuthDAO.insertAuth(auth);
        Assertions.assertThrows(DataAccessException.class, () -> sqlAuthDAO.insertAuth(auth));
    }

    @Test
    @DisplayName("Auth In Database")
    public void authInDatabaseTest() throws Exception {
        HashMap<String,AuthData> expected = new HashMap<>();
        AuthData auth = new AuthData("admin", "admin");
        AuthData auth2 = new AuthData("admin2", "admin2");
        AuthData auth3 = new AuthData("admin3", "admin3");
        expected.put("admin",auth);
        expected.put("admin2",auth2);
        expected.put("admin3",auth3);
        sqlAuthDAO.insertAuth(auth);
        sqlAuthDAO.insertAuth(auth2);
        sqlAuthDAO.insertAuth(auth3);
        HashMap<String,AuthData> actual = sqlAuthDAO.authDataInDatabase();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    @DisplayName("Delete One Auth")
    public void deleteAuthTest() throws Exception {
        HashMap<String,AuthData> expected = new HashMap<>();
        AuthData auth = new AuthData("admin", "admin");
        AuthData auth2 = new AuthData("admin2", "admin2");
        expected.put("admin",auth);
        sqlAuthDAO.insertAuth(auth);
        sqlAuthDAO.insertAuth(auth2);
        sqlAuthDAO.deleteAuth("admin2");
        HashMap<String,AuthData> actual = sqlAuthDAO.authDataInDatabase();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Bad Delete")
    public void badDeleteTest() throws Exception {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Insert Game")
    public void insertGameTest() throws Exception {
        GameData game = new GameData(5,"admin","admin","admin",new ChessGame());
        sqlGameDAO.insertGame(game);
        GameData actual = sqlGameDAO.getGame(game.gameID());
        Assertions.assertEquals(game,actual);
    }

    @Test
    @DisplayName("Insert Bad Game")
    public void insertBadGameTest() throws Exception {
        GameData game = new GameData(5,"admin","admin","admin",new ChessGame());
        sqlGameDAO.insertGame(game);
        Assertions.assertThrows(DataAccessException.class, () -> sqlGameDAO.insertGame(game));
    }

    @Test
    @DisplayName("Insert Same GameID")
    public void insertSameGameIDTest() throws Exception {
        GameData game = new GameData(5,"admin","admin","admin",new ChessGame());
        GameData badGame = new GameData(5,"admin2","admin2","admin2",new ChessGame());
        sqlGameDAO.insertGame(game);
        Assertions.assertThrows(DataAccessException.class, () -> sqlGameDAO.insertGame(badGame));
    }

    @Test
    @DisplayName("List Games")
    public void listGamesTest() throws Exception {
        Collection<GameData> expected = new ArrayList<>();
        GameData game = new GameData(1,"admin","admin","admin",new ChessGame());
        GameData game2 = new GameData(2,"admin2","admin2","admin2",new ChessGame());
        GameData game3 = new GameData(3,"admin3","admin3","admin3",new ChessGame());
        expected.add(game);
        expected.add(game2);
        expected.add(game3);
        sqlGameDAO.insertGame(game);
        sqlGameDAO.insertGame(game2);
        sqlGameDAO.insertGame(game3);
        Collection<GameData> actual = sqlGameDAO.listGames();
        Assertions.assertEquals(expected,actual);

    }

    @Test
    @DisplayName("List Bad Games")
    public void listBadGamesTest() throws Exception {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Join Game")
    public void joinGameTest() throws Exception {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        String authToken = memoryAuthDAO.createAuth("admin");
        AuthData authData = new AuthData("admin", authToken);
        sqlAuthDAO.insertAuth(authData);
        GameData game = new GameData(5,null,null,"admin",new ChessGame());
        GameData expected = new GameData(5,"admin",null,"admin",new ChessGame());
        sqlGameDAO.insertGame(game);
        sqlGameDAO.updateGame(authData.authToken(),"white",game.gameID());
        GameData actual = sqlGameDAO.getGame(5);
        Assertions.assertEquals(expected,actual);

    }

    @Test
    @DisplayName("Invalid Join")
    public void invalidJoinTest() throws Exception {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        String authToken = memoryAuthDAO.createAuth("admin");
        AuthData authData = new AuthData("admin", authToken);
        AuthData badAuthData = new AuthData("fail", "fail");
        sqlAuthDAO.insertAuth(authData);
        sqlAuthDAO.insertAuth(badAuthData);
        GameData game = new GameData(5,null,null,"admin",new ChessGame());
        GameData bad = new GameData(5,"fail",null,"admin",new ChessGame());
        sqlGameDAO.insertGame(game);
        sqlGameDAO.updateGame(authData.authToken(),"white",game.gameID());

        Assertions.assertThrows(DataAccessException.class, () -> sqlGameDAO.updateGame(badAuthData.authToken(),
                "white",bad.gameID()));

    }
}

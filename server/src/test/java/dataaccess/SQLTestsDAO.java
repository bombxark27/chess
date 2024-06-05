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
        UserData selectedUser = sqlUserDAO.getUser(user.username(),user.password());
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


}

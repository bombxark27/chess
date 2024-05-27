package service;

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
    public void clearDatabaseTest(){

    }
}

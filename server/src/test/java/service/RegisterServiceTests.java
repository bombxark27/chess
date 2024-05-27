package service;

import dataaccess.DataAccessException;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RegisterServiceTests {
    private RegisterService service;
    private HashMap<String,AuthData> expectedAuthDAO;
    private Collection<UserData> expectedUserDAO;


    @BeforeEach
    public void setUp(){
        expectedUserDAO = new ArrayList<UserData>();
        service = new RegisterService();


    }

    @Test
    @DisplayName("Register New User")
    public void registerNewUser(){
        UserData expectedUser = new UserData("reg23","password5","reg23@email.com");
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        service.register(newUser);
        expectedUserDAO.add(expectedUser);


        Assertions.assertEquals(expectedUserDAO,userDataAccess.usersInDatabase());
        Assertions.assertFalse(authDataAccess.authDataInDatabase().isEmpty());
    }

    @Test
    @DisplayName("Register Invalid User")
    public void registerInvalidUser(){

    }

}

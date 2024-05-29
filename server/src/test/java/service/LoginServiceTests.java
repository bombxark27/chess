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
import result.UnauthorizedResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LoginServiceTests {
    private LoginService loginService;
    private Collection<UserData> expectedUserDatabase;
    private MemoryAuthDAO expectedAuthDatabase;
    private MemoryUserDAO userMemory;

    @BeforeEach
    public void setUp(){
        loginService = new LoginService();
        expectedUserDatabase = new ArrayList<UserData>();
        expectedAuthDatabase = new MemoryAuthDAO();
        userMemory = new MemoryUserDAO();

        ClearService clearService = new ClearService();
        clearService.clearDatabase();
    }

    @Test
    @DisplayName("Successful Login")
    public void successfulLogin() throws Exception {
        UserData expectedUser = new UserData("reg23","password5","reg23@email.com");
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        RegisterService registerService = new RegisterService();

        expectedUserDatabase.add(expectedUser);

        registerService.register(newUser);
        loginService.login(expectedUser);

        Assertions.assertEquals(expectedUserDatabase,userMemory.usersInDatabase());
        Assertions.assertFalse(expectedAuthDatabase.authDataInDatabase().isEmpty());

    }

    @Test
    @DisplayName("Invalid Username")
    public void invalidUsername() throws Exception {
        UserData badUser = new UserData("invalid","password5","reg23@email.com");
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        RegisterService registerService = new RegisterService();

        expectedUserDatabase.add(newUser);
        registerService.register(newUser);

        Assertions.assertEquals(expectedUserDatabase,userMemory.usersInDatabase());
        Assertions.assertThrows(UnauthorizedResult.class, () -> loginService.login(badUser));
    }

    @Test
    @DisplayName("Invalid Password")
    public void invalidPassword() throws Exception {
        UserData badUser = new UserData("reg23","wrongPassword2","reg23@email.com");
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        RegisterService registerService = new RegisterService();

        expectedUserDatabase.add(newUser);
        registerService.register(newUser);

        Assertions.assertEquals(expectedUserDatabase,userMemory.usersInDatabase());
        Assertions.assertThrows(UnauthorizedResult.class, () -> loginService.login(badUser));
    }
}

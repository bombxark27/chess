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

public class LogoutServiceTests {
    private MemoryAuthDAO authDataAccess;
    private MemoryUserDAO userDataAccess;
    private LogoutService logoutService;
    private LoginService loginService;
    private RegisterService registerService;

    private Collection<UserData> expectedUsers;
    private HashMap<String,AuthData> expectedAuthData;


    @BeforeEach
    public void setUp() {
        authDataAccess = new MemoryAuthDAO();
        userDataAccess = new MemoryUserDAO();
        logoutService = new LogoutService();
        loginService = new LoginService();
        registerService = new RegisterService();

        expectedUsers = new ArrayList<>();
        expectedAuthData = new HashMap<>();

        ClearService clearService = new ClearService();
        clearService.clearDatabase();
    }

    @Test
    @DisplayName("Successful Logout")
    public void testSuccessfulLogout() throws Exception {
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        expectedUsers.add(newUser);

        registerService.register(newUser);

        AuthData testLogin = loginService.login(newUser);

        String authToken = testLogin.authToken();

        Assertions.assertFalse(authDataAccess.authDataInDatabase().isEmpty());
        Assertions.assertTrue(authDataAccess.authDataInDatabase().containsKey(authToken));

        logoutService.logout(authToken);

        Assertions.assertEquals(expectedUsers,userDataAccess.usersInDatabase());
        Assertions.assertFalse(authDataAccess.authDataInDatabase().containsKey(authToken));
        Assertions.assertFalse(authDataAccess.authDataInDatabase().isEmpty());

    }

    @Test
    @DisplayName("AuthToken Doesn't Exist")
    public void testAuthTokenDoesntExist() throws Exception {
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        expectedUsers.add(newUser);

        registerService.register(newUser);

        loginService.login(newUser);

        Assertions.assertThrows(RuntimeException.class, () -> logoutService.logout("fakeAuthToken"));

    }


}

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
import org.mindrot.jbcrypt.BCrypt;
import result.AlreadyTakenResult;
import result.BadRequestResult;

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
        ClearService clearService = new ClearService();
        clearService.clearDatabase();


    }

    @Test
    @DisplayName("Register New User")
    public void registerNewUser() throws Exception {
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        service.register(newUser);
        String hashedPassword = BCrypt.hashpw("password5", BCrypt.gensalt());
        UserData expectedUser = userDataAccess.getUser("reg23");
        expectedUserDAO.add(expectedUser);


        Assertions.assertEquals(expectedUserDAO,userDataAccess.usersInDatabase());
        Assertions.assertEquals(expectedUser,userDataAccess.getUser("reg23"));
        Assertions.assertFalse(authDataAccess.authDataInDatabase().isEmpty());
    }

    @Test
    @DisplayName("Register Invalid User")
    public void registerInvalidUser() throws Exception {
        UserData newUser = new UserData("reg23",null,null);
        Assertions.assertThrows(BadRequestResult.class,()->service.register(newUser));
    }

    @Test
    @DisplayName("Re-Register User")
    public void reRegisterUser() throws Exception {
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        service.register(newUser);
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();

        Assertions.assertFalse(authDataAccess.authDataInDatabase().isEmpty());
        Assertions.assertThrows(AlreadyTakenResult.class,()->service.register(newUser));
    }

    @Test
    @DisplayName("Register Changed Other DAO")
    public void registerChangedOtherDAO() throws Exception {
        UserData newUser = new UserData("reg23","password5","reg23@email.com");
        service.register(newUser);
        MemoryUserDAO otherUserDAO = new MemoryUserDAO();
        expectedUserDAO.add(otherUserDAO.getUser("reg23"));

        Assertions.assertEquals(expectedUserDAO,otherUserDAO.usersInDatabase());
        Assertions.assertFalse(otherUserDAO.usersInDatabase().isEmpty());
    }

}

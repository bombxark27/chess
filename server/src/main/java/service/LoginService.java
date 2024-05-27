package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

import model.AuthData;
import model.UserData;

public class LoginService {
    AuthData login(UserData user){
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        AuthData result;
        try {
            UserData existingUser = userDataAccess.getUser(user.username(), user.password());
            String authToken = authDataAccess.createAuth(existingUser.username());
            result = authDataAccess.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}

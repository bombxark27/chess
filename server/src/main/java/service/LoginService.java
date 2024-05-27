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
            userDataAccess.getUser(user.username());
            result = authDataAccess.getAuth(authDataAccess.createAuth(user.username()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}

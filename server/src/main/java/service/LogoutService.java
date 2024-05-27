package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

import model.AuthData;
import model.UserData;

public class LogoutService {
    public void logout(String authToken) {
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        try {
            authDataAccess.getAuth(authToken);
            authDataAccess.deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

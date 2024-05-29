package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

import model.AuthData;
import model.UserData;
import result.UnauthorizedResult;

public class LogoutService {
    public void logout(String authToken) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        try {
            authDataAccess.getAuth(authToken);
            authDataAccess.deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedResult();
        }
    }

}

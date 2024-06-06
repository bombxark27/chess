package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;

import dataaccess.SQLAuthDAO;
import result.UnauthorizedResult;

public class LogoutService {
    public void logout(String authToken) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        SQLAuthDAO sqlAuthDataAccess = new SQLAuthDAO();
        try {
            authDataAccess.getAuth(authToken);
            authDataAccess.deleteAuth(authToken);
            sqlAuthDataAccess.deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedResult();
        }
    }

}

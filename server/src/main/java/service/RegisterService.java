package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

import model.AuthData;
import model.UserData;
import result.AlreadyTakenResult;
import result.BadRequestResult;


public class RegisterService {
    public AuthData register(UserData user) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        String authToken;
        AuthData result;

        if (user.username() == null || user.password() == null || user.email() == null) {
            throw new BadRequestResult();
        }
        try {
            userDataAccess.insertUser(user);
            authToken = authDataAccess.createAuth(user.username());
            result = authDataAccess.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new AlreadyTakenResult();
        }

        return result;
    }
}

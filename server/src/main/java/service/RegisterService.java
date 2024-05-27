package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

import model.AuthData;
import model.UserData;

import request.RegisterRequest;
import result.RegisterResult;

public class RegisterService {
    AuthData register(UserData user){

        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryUserDAO userDataAccess = new MemoryUserDAO();
        String authToken;
        AuthData result;

        try {
            userDataAccess.insertUser(user);
            authToken = authDataAccess.createAuth(user.username());
            result = authDataAccess.getAuth(authToken);

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}

package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.sql.*;

public class SQLAuthDAO implements AuthDAO{


    @Override
    public String createAuth(String username) {
        return "";
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public HashMap<String, AuthData> authDataInDatabase() {
        return null;
    }

    @Override
    public void clearAuth() {

    }
}

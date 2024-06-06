package dataaccess;

import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {
    public String createAuth(String username) throws Exception;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void deleteAuth(String authToken) throws Exception;
    public HashMap<String,AuthData> authDataInDatabase();
    public void clearAuth() throws Exception;
}

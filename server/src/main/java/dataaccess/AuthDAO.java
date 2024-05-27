package dataaccess;

import model.AuthData;

public interface AuthDAO {

    public AuthData createAuth(String username);
    public void insertAuth(AuthData data) throws DataAccessException;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void deleteAuth(String authToken) throws DataAccessException;
    public void clearAuth();
}

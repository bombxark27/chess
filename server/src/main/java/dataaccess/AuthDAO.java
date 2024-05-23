package dataaccess;

import model.AuthData;

public interface AuthDAO {
    public AuthData createAuth(String username);
    public AuthData getAuth(String username) throws DataAccessException;
    public AuthData deleteAuth(String username);
    public AuthData clear();
}

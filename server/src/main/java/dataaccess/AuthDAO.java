package dataaccess;

import model.AuthData;

public interface AuthDAO {
    public AuthData createAuth(String username);
    public AuthData getAuth(String username);
    public AuthData deleteAuth(String username);
}
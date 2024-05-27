package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {
    public UserData createUser(String username, String password, String email) throws DataAccessException;
    public void insertUser(UserData data) throws DataAccessException;
    public UserData getUser(String username, String password) throws DataAccessException;
    public boolean userExists(String username);
    public Collection<UserData> usersInDatabase();
    public void clearUser();
}

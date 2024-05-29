package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {
    public void insertUser(UserData data) throws DataAccessException;
    public UserData getUser(String username, String password) throws DataAccessException;
    public Collection<UserData> usersInDatabase();
    public void clearUser();
}

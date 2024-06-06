package dataaccess;

import model.UserData;

import java.util.Collection;

public interface UserDAO {
    public void insertUser(UserData data) throws Exception;
    public UserData getUser(String username) throws DataAccessException;
    public Collection<UserData> usersInDatabase();
    public void clearUser() throws Exception;
}

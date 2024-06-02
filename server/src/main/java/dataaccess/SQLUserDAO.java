package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.List;

public class SQLUserDAO implements UserDAO{


    @Override
    public void insertUser(UserData data) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username, String password) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<UserData> usersInDatabase() {
        return List.of();
    }

    @Override
    public void clearUser() {

    }
}

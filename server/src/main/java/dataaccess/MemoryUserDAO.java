package dataaccess;

import model.UserData;

public class MemoryUserDAO implements UserDAO {

    @Override
    public UserData createUser(String username, String password, String email) {
        return null;
    }

    @Override
    public UserData getUser(String username) {
        return null;
    }
}

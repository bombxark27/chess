package dataaccess;

import model.UserData;

public class MemoryUserDAO implements UserDAO {

    @Override
    public UserData createUser(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        return null;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException{

        return null;
    }

    @Override
    public UserData clear(){
        return null;
    }
}

package dataaccess;

import model.UserData;
import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO {
    private static Collection<UserData> users = new ArrayList<UserData>();


    @Override
    public void insertUser(UserData data) throws DataAccessException{
        if (users.contains(data)){
            throw new DataAccessException("User already exists");
        }
        users.add(data);
    }

    @Override
    public UserData getUser(String username, String password) throws DataAccessException{
        for (UserData user : users){
            if (user.username().equals(username) && user.password().equals(password)){
                return user;
            }
        }
        throw new DataAccessException("User not found");
    }

    @Override
    public Collection<UserData> usersInDatabase(){
        return users;
    }

    @Override
    public void clearUser(){
        users.clear();
    }
}

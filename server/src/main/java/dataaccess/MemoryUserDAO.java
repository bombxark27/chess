package dataaccess;

import model.UserData;
import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO {
    private static Collection<UserData> users = new ArrayList<UserData>();

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException{
        for (UserData user : users){
            if (user.username().equals(username)){
                throw new DataAccessException("Username already exists");
            }
        }
        return new UserData(username, password, email);
    }

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
    public boolean userExists(String username){
        boolean exists = false;
        for (UserData user : users){
            if (user.username().equals(username)) {
                exists = true;
                break;
            }
        }
        return exists;
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

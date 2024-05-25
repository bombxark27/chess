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
        UserData user = new UserData(username, password, email);
        users.add(user);
        return user;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException{
        for (UserData user : users){
            if (user.username().equals(username)){
                return user;
            }
        }
        throw new DataAccessException("User not found");
    }

    @Override
    public void clear(){
        users.clear();
    }
}

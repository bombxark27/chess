package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.util.Collection;
import java.util.List;
import java.sql.*;

public class SQLUserDAO implements UserDAO{


    @Override
    public void insertUser(UserData data) throws DataAccessException {
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?);";
        var json = new Gson().toJson(data);


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
        var statement = "DELETE FROM user";
//        executeUpdate(statement);
    }



    
    public void executeUpdate(String statement) throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        }
    }
}

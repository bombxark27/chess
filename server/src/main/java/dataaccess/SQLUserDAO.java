package dataaccess;

import com.google.gson.Gson;
import model.UserData;

import java.util.Collection;
import java.util.List;
import java.sql.*;

public class SQLUserDAO implements UserDAO{


    @Override
    public void insertUser(UserData data) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1,data.username());
                preparedStatement.setString(2,data.password());
                preparedStatement.setString(3,data.email());
                preparedStatement.executeUpdate();
                var resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }



    }

    @Override
    public UserData getUser(String username, String password) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Collection<UserData> usersInDatabase() {
        return List.of();
    }

    @Override
    public void clearUser() {
        var statement = "TRUNCATE TABLE user";
//        executeUpdate(statement);
    }



    

}

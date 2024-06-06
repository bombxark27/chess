package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

import static dataaccess.DatabaseManager.createDatabase;
import static dataaccess.DatabaseManager.executeUpdate;

public class SQLUserDAO implements UserDAO{


    public SQLUserDAO() throws Exception{
        createDatabase();
    }

    @Override
    public void insertUser(UserData data) throws Exception {
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
//        String hashedPassword = BCrypt.hashpw(data.password(), BCrypt.gensalt());
//        executeUpdate(statement,data.username(),hashedPassword,data.email());
        executeUpdate(statement,data.username(),data.password(),data.email());

    }

    @Override
    public UserData getUser(String username, String password) throws DataAccessException {
        Collection<UserData> users = usersInDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (var rs = preparedStatement.executeQuery()) {
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
        Collection<UserData> result = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        result.add(new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email")));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public void clearUser() throws Exception {
        var statement = "TRUNCATE TABLE user";
//        var statement = "DELETE FROM user";
        executeUpdate(statement);
    }



    

}

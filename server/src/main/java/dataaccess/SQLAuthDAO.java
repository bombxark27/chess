package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.sql.*;

import static dataaccess.DatabaseManager.executeUpdate;

public class SQLAuthDAO implements AuthDAO{


    @Override
    public String createAuth(String username) {
        var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
        return "";
    }

    public AuthData insertAuth(AuthData data) throws Exception {
        var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
        executeUpdate(statement,data.username(),data.authToken());
        return new AuthData(data.username(),data.authToken());
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT * FROM auth WHERE authToken = ?";
            try (var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.setString(1, authToken);
                try (var resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        return new AuthData(resultSet.getString("username"), resultSet.getString("authToken"));
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws Exception {
        var statement = "DELETE FROM auth WHERE authToken = ?";
        executeUpdate(statement, authToken);
    }

    @Override
    public HashMap<String, AuthData> authDataInDatabase() {
        return null;
    }

    @Override
    public void clearAuth() throws Exception {
//        var statement = "TRUNCATE TABLE auth";
        var statement = "DELETE FROM auth";
        executeUpdate(statement);
    }
}

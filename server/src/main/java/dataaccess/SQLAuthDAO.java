package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.sql.*;

import static dataaccess.DatabaseManager.executeUpdate;

public class SQLAuthDAO implements AuthDAO{


    @Override
    public String createAuth(String username) throws Exception{
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT * FROM auth WHERE username = ?";
            try (var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.setString(1, username);
                try (var resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()){
                        return resultSet.getString("authToken");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
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
        Collection<AuthData> result = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT * FROM auth";
            try (var preparedStatement = conn.prepareStatement(statement)){
                try (var resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        result.add(new AuthData(resultSet.getString("username"), resultSet.getString("authToken")));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        HashMap<String, AuthData> authData = new HashMap<>();
        for (AuthData data : result){
            authData.put(data.authToken(), data);
        }
        return authData;
    }

    @Override
    public void clearAuth() throws Exception {
        var statement = "TRUNCATE TABLE auth";
//        var statement = "DELETE FROM auth";
        executeUpdate(statement);
    }
}

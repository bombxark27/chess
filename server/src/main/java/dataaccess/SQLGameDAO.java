package dataaccess;

import com.google.gson.Gson;
import model.GameData;

import java.util.Collection;
import java.util.List;
import java.sql.*;

import static dataaccess.DatabaseManager.executeUpdate;

public class SQLGameDAO implements GameDAO{


    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        return null;
    }

    @Override
    public void insertGame(GameData data) throws Exception {
        var statement = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?, ?)";
        var json = new Gson().toJson(data.game());
        executeUpdate(statement, data.gameID(),data.whiteUsername(),data.blackUsername(),data.gameName(),json);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE gameID = ?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setInt(1, gameID);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                    }
                }
            }
        } catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        var statement = "UPDATE game ";
    }

    @Override
    public Collection<GameData> listGames() {
        return List.of();
    }

    @Override
    public void clearGame() throws Exception {
        var statement = "TRUNCATE TABLE games";
        executeUpdate(statement);
    }
}

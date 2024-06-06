package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static dataaccess.DatabaseManager.executeUpdate;

public class SQLGameDAO implements GameDAO{


    @Override
    public GameData createGame(String gameName) throws Exception {
        var statement = "INSERT INTO games (gameName, chessGame) VALUES (?,?)";
        executeUpdate(statement,gameName,new ChessGame());
        try (var conn = DatabaseManager.getConnection()) {
            var selectStatement = "SELECT * FROM game WHERE gameName = ?";
            try (var preparedStatement = conn.prepareStatement(selectStatement)) {
                preparedStatement.setString(1, gameName);
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return readGame(resultSet);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
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
                        return readGame(resultSet);
                    }
                }
            }
        } catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void updateGame(String authToken, String playerColor, int gameID) throws Exception {
        var statement = "UPDATE game SET whiteUsername = ?, blackUsername = ?, chessGame = ? " +
                "WHERE gameID = ?";

        GameData data = getGame(gameID);
        SQLAuthDAO authDAO = new SQLAuthDAO();
        AuthData authData = authDAO.getAuth(authToken);
        if (playerColor.equalsIgnoreCase("white") && data.whiteUsername() == null) {
            executeUpdate(statement, authData.username(), data.blackUsername(), data.game(), gameID);
        }
        else if (playerColor.equalsIgnoreCase("black") && data.blackUsername() == null) {
            executeUpdate(statement, data.blackUsername(), authData.username(), data.game(), gameID);
        }
        else{
            throw new DataAccessException("Invalid player color or Player taken");
        }

    }

    @Override
    public Collection<GameData> listGames() {
        Collection<GameData> result = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()){
            var statement = "SELECT * FROM game";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                try (var resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(readGame(resultSet));
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public void clearGame() throws Exception {
        var statement = "TRUNCATE TABLE game";
//        var statement = "DELETE FROM game";
        executeUpdate(statement);
    }


    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
//        var json = rs.getString("chessGame");
        var chessGame = new Gson().fromJson(rs.getString("chessGame"), ChessGame.class);
        return new GameData(gameID,whiteUsername,blackUsername,gameName,chessGame);
    }
}

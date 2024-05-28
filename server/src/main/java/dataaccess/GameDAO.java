package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    public GameData createGame(String gameName) throws DataAccessException;
    public void insertGame(GameData data) throws DataAccessException;
    public GameData getGame(int gameID) throws DataAccessException;
    public void updateGame(String authToken, String playerColor, int gameID) throws DataAccessException;
    public Collection<GameData> listGames();
    public void clearGame();
}

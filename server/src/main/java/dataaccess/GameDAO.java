package dataaccess;

import model.GameData;

public interface GameDAO {
    public int createGame(String gameName) throws DataAccessException;
    public GameData getGame(int gameID) throws DataAccessException;
    public GameData updateGame(String playerColor, int gameID) throws DataAccessException;
    public GameData listGames();
    public GameData clear();
}

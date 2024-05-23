package dataaccess;

import model.GameData;

public interface GameDAO {
    public GameData createGame(String gameName);
    public GameData getGame(int gameID);
    public GameData updateGame(String playerColor, int gameID);
    public GameData listGames();
}

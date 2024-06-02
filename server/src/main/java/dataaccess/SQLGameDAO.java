package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO{


    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        return null;
    }

    @Override
    public void insertGame(GameData data) throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(String authToken, String playerColor, int gameID) throws DataAccessException {

    }

    @Override
    public Collection<GameData> listGames() {
        return List.of();
    }

    @Override
    public void clearGame() {

    }
}

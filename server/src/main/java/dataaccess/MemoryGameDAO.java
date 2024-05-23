package dataaccess;

import model.GameData;

public class MemoryGameDAO implements GameDAO{

    @Override
    public GameData createGame(String gameName) {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException{
        return null;
    }

    @Override
    public GameData updateGame(String playerColor, int gameID) throws DataAccessException{
        return null;
    }

    @Override
    public GameData listGames() {
        return null;
    }

    @Override
    public GameData clear(){
        return null;
    }
}

package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import model.GameData;
import result.UnauthorizedResult;

import java.util.Collection;

public class ListGamesService {
    public Collection<GameData> listGames(String authToken) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
        Collection<GameData> games;
        Collection<GameData> databaseGames;

        try {
            authDataAccess.getAuth(authToken);
            games = gameDataAccess.listGames();
        } catch (DataAccessException e) {
            throw new UnauthorizedResult();
        }

        return games;
    }

}

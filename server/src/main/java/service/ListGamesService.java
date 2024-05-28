package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import model.GameData;

import java.util.Collection;

public class ListGamesService {
    public Collection<GameData> listGames(String authToken){
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
        Collection<GameData> games;

        try {
            authDataAccess.getAuth(authToken);
            games = gameDataAccess.listGames();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return games;
    }

}

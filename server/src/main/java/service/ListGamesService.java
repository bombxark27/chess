package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import dataaccess.SQLGameDAO;
import model.GameData;
import result.UnauthorizedResult;

import java.util.Collection;

public class ListGamesService {
    public Collection<GameData> listGames(String authToken) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();
        Collection<GameData> games;
        Collection<GameData> databaseGames;

        try {
            authDataAccess.getAuth(authToken);
            games = gameDataAccess.listGames();
            databaseGames = gameDAO.listGames();
//            if (games != databaseGames) {
//                throw new UnauthorizedResult();
//            }
        } catch (DataAccessException e) {
            throw new UnauthorizedResult();
        }

        return games;
    }

}

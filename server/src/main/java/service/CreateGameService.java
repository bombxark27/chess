package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import model.GameData;

public class CreateGameService {
    public int createGame(String authToken, String gameName){
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
        int gameID;

        try {
            authDataAccess.getAuth(authToken);
            GameData newGame = gameDataAccess.createGame(gameName);
            gameDataAccess.insertGame(newGame);
            gameID = newGame.gameID();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return gameID;
    }

}

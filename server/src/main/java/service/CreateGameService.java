package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import model.GameData;
import result.BadRequestResult;
import result.UnauthorizedResult;

public class CreateGameService {
    public int createGame(String gameName, String authToken) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
        int gameID;
        GameData newGame;

        if (!authDataAccess.authDataInDatabase().containsKey(authToken)) {
            throw new UnauthorizedResult();
        }
        try{
            authDataAccess.getAuth(authToken);
            newGame = gameDataAccess.createGame(gameName);
            gameDataAccess.insertGame(newGame);
            gameID = newGame.gameID();
        } catch (DataAccessException e) {
            throw new BadRequestResult();
        }

        return gameID;
    }

}

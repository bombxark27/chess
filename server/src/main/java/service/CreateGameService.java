package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.SQLAuthDAO;
import dataaccess.SQLGameDAO;
import dataaccess.SQLUserDAO;

import model.GameData;
import result.BadRequestResult;
import result.UnauthorizedResult;

public class CreateGameService {
    public int createGame(String gameName, String authToken) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
//        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();
        int gameID;
        GameData newGame;

        if (!authDataAccess.authDataInDatabase().containsKey(authToken)) {
            throw new UnauthorizedResult();
        }
        try{
            authDataAccess.getAuth(authToken);
            newGame = gameDataAccess.createGame(gameName);
            gameDataAccess.insertGame(newGame);
            gameDAO.insertGame(newGame);
            gameID = newGame.gameID();
        } catch (DataAccessException e) {
            throw new BadRequestResult();
        }

        return gameID;
    }

}

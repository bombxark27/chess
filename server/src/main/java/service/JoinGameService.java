package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

public class JoinGameService {
    public void joinGame(String authToken, String playerColor, int gameID){
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();

        try {
            authDataAccess.getAuth(authToken);
            gameDataAccess.updateGame(authToken,playerColor,gameID);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

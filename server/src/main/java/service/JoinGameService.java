package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import model.GameData;
import result.AlreadyTakenResult;
import result.BadRequestResult;
import result.UnauthorizedResult;

public class JoinGameService {
    public void joinGame(String authToken, String playerColor, int gameID) throws Exception{
        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        MemoryGameDAO gameDataAccess = new MemoryGameDAO();
        ChessGame.TeamColor teamColor;

        if (!authDataAccess.authDataInDatabase().containsKey(authToken)) {
            throw new UnauthorizedResult();
        }

        if (playerColor == null || gameID == 0){
            throw new BadRequestResult();
        }

        if (playerColor.equalsIgnoreCase("white")){
            teamColor = ChessGame.TeamColor.WHITE;
        }
        else if (playerColor.equalsIgnoreCase("black")){
            teamColor = ChessGame.TeamColor.BLACK;
        }
        else{
            throw new BadRequestResult();
        }

        GameData desiredGame = gameDataAccess.getGame(gameID);
        if (desiredGame == null) {
            throw new BadRequestResult();
        }
        else if (teamColor == ChessGame.TeamColor.WHITE) {
            if (desiredGame.whiteUsername() != null){
                throw new AlreadyTakenResult();
            }
        }
        else {
            if (desiredGame.blackUsername() != null){
                throw new AlreadyTakenResult();
            }
        }

        try {
            authDataAccess.getAuth(authToken);
            gameDataAccess.updateGame(authToken,playerColor,gameID);
        } catch (DataAccessException e) {
            throw new BadRequestResult();
        }
    }

}

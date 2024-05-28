package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class MemoryGameDAO implements GameDAO{
    private static Collection<GameData> games = new ArrayList<GameData>();

    @Override
    public GameData createGame(String gameName) throws DataAccessException{
        Random rand = new Random();
        int gameID = rand.nextInt(10000);
        boolean sameID = true;
        while (sameID) {
            sameID = false;
            for (GameData game : games) {
                if (gameID == game.gameID()) {
                    sameID = true;
                    gameID = rand.nextInt(10000);
                }
                if (gameName.equals(game.gameName())) {
                    throw new DataAccessException("Game name already exists");
                }
            }
        }
        return new GameData(gameID, null, null, gameName, new ChessGame());
    }

    @Override
    public void insertGame(GameData data) throws DataAccessException{
        if (games.contains(data)){
            throw new DataAccessException("Game already exists");
        }
        games.add(data);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException{
        for (GameData game: games){
            if (game.gameID() == gameID){
                return game;
            }
        }
        throw new DataAccessException("Game does not exist");
    }

    @Override
    public void updateGame(String authToken, String playerColor, int gameID) throws DataAccessException{
        boolean updated = false;

        MemoryAuthDAO authDataAccess = new MemoryAuthDAO();
        AuthData authorization = authDataAccess.getAuth(authToken);

        ChessGame.TeamColor chessColor;
        if (playerColor.equalsIgnoreCase("white")){
            chessColor = ChessGame.TeamColor.WHITE;
        }
        else if (playerColor.equalsIgnoreCase("black")){
            chessColor = ChessGame.TeamColor.BLACK;
        }
        else{
            throw new DataAccessException("Invalid player color");
        }

        GameData oldGame = null;
        GameData updatedGame = null;
        for (GameData game : games){
            if (game.gameID() == gameID){
                oldGame = game;
                if (chessColor == ChessGame.TeamColor.WHITE && game.whiteUsername() == null){
                    updatedGame = new GameData(gameID,authorization.username(),game.blackUsername(),game.gameName(),game.game());
                }
                else if (chessColor == ChessGame.TeamColor.WHITE && game.whiteUsername() != null){
                    throw new DataAccessException("White already taken");
                }
                if (chessColor == ChessGame.TeamColor.BLACK && game.blackUsername() == null){
                    updatedGame = new GameData(gameID,game.whiteUsername(),authorization.username(),game.gameName(),game.game());
                }
                else if (chessColor == ChessGame.TeamColor.BLACK && game.blackUsername() != null){
                    throw new DataAccessException("Black already taken");
                }
                updated = true;
                break;
            }
        }
        if(!updated) {
            throw new DataAccessException("Game does not exist");
        }
        games.remove(oldGame);
        games.add(updatedGame);
    }

    @Override
    public Collection<GameData> listGames() {
        return games;
    }

    @Override
    public void clearGame(){
        games.clear();
    }
}

package dataaccess;

import chess.ChessGame;
import model.GameData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class MemoryGameDAO implements GameDAO{
    private static Collection<GameData> games = new ArrayList<GameData>();

    @Override
    public int createGame(String gameName) throws DataAccessException{
        Random rand = new Random();
        int gameID = rand.nextInt(10000);
        for (GameData game: games){
            if (gameID == game.gameID()){
                gameID = rand.nextInt(10000);
            }
            if (gameName.equals(game.gameName())){
                throw new DataAccessException("Game name already exists");
            }
        }
        GameData newGame = new GameData(gameID, null, null, gameName, new ChessGame());
        games.add(newGame);
        return gameID;
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
    public GameData updateGame(String playerColor, int gameID) throws DataAccessException{
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
        ChessGame desiredGame;
        for (GameData game : games){
            if (game.gameID() == gameID){
                desiredGame = game.game();
                return game;
            }
        }
        throw new DataAccessException("Game does not exist");
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

package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame implements Cloneable{
    private ChessBoard gameBoard = new ChessBoard();
    private TeamColor teamTurn = TeamColor.WHITE;
    public ChessGame() {
        gameBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece myPiece;
        ChessGame cloneGame;
        TeamColor turn = teamTurn;
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        if (gameBoard.getPiece(startPosition) != null){
            myPiece = gameBoard.getPiece(startPosition);
            Collection<ChessMove> noRules = myPiece.pieceMoves(gameBoard,startPosition);
            teamTurn = myPiece.getTeamColor();
            for (ChessMove move : noRules){
                try {
                    cloneGame = (ChessGame) this.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                ChessBoard cloneBoard = cloneGame.getBoard();
                cloneBoard.addPiece(move.getEndPosition(),cloneBoard.getPiece(move.getStartPosition()));
                cloneBoard.addPiece(move.getStartPosition(),null);
                cloneGame.setBoard(cloneBoard);
                if (isInCheck(teamTurn) && cloneGame.isInCheck(teamTurn)){
                    continue;
                }
                else if (!cloneGame.isInCheck(teamTurn)){
                    possibleMoves.add(move);
                }
            }
            teamTurn = turn;
            return possibleMoves;
        }
        else{
            return null;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        Collection<ChessMove> possibleMoves = validMoves(startPosition);
        if (gameBoard.getPiece(startPosition) == null){
            throw new InvalidMoveException("No piece at startPosition");
        }
        else if (gameBoard.getPiece(startPosition).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Not your turn");
        }
        else if (!possibleMoves.contains(move)) {
            throw new InvalidMoveException("Move not possible");
        }
        ChessPiece pawnPromotionPiece = null;
        if (move.getPromotionPiece() != null){
            pawnPromotionPiece = new ChessPiece(gameBoard.getPiece(startPosition).getTeamColor(),move.getPromotionPiece());
            gameBoard.addPiece(move.getEndPosition(), pawnPromotionPiece);
        }
        else {
            gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
        }
        gameBoard.addPiece(move.getStartPosition(), null);
        if (teamTurn == TeamColor.BLACK){
            teamTurn = TeamColor.WHITE;
        }
        else {
            teamTurn = TeamColor.BLACK;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        ChessPosition pivot;
        ChessPiece piece;
        ArrayList<ChessMove> enemyMoves = new ArrayList<ChessMove>();
        for (int row = 1; row <=8; row++){
            for (int col = 1; col <=8; col++){
                pivot = new ChessPosition(row,col);
                if (gameBoard.getPiece(pivot) != null){
                    piece = gameBoard.getPiece(pivot);
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                        kingPosition = pivot;
                    }
                    else if (piece.getTeamColor() != teamColor){
                        enemyMoves.addAll(piece.pieceMoves(gameBoard,pivot));
                    }
                }
            }
        }
        for (ChessMove move : enemyMoves){
            if (move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean checkmate = false;
        Collection<ChessMove> possibleMoves = getPossibleMoves(teamColor);
        if (possibleMoves.isEmpty() && isInCheck(teamColor)){
            checkmate = true;
        }
        return checkmate;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean stalemate = false;
        if (isInCheckmate(teamColor)){
            return stalemate;
        }
        Collection<ChessMove> possibleMoves = getPossibleMoves(teamColor);
        if (possibleMoves.isEmpty()){
            stalemate = true;
        }
        return stalemate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }

    public Collection<ChessMove> getPossibleMoves(TeamColor teamColor) {
        ChessPosition pivot;
        ChessPiece piece;
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        for (int row = 1; row <=8; row++) {
            for (int col = 1; col <= 8; col++) {
                pivot = new ChessPosition(row, col);
                if (gameBoard.getPiece(pivot) != null){
                    piece = gameBoard.getPiece(pivot);
                    if (piece.getTeamColor() == teamColor){
                        possibleMoves.addAll(this.validMoves(pivot));
                    }
                }
            }
        }
        return possibleMoves;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        ChessGame clonedGame = (ChessGame) super.clone();
        //should be deep copy of the board
        ChessBoard clonedBoard = (ChessBoard) gameBoard.clone();
        clonedGame.gameBoard = clonedBoard;
        return clonedGame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(gameBoard, chessGame.gameBoard) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameBoard, teamTurn);
    }
}

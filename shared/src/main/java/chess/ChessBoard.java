package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        // -1 on the index to prevent squares[8][8] should access by squares[7][7]
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        // -1 on the index to prevent squares[8][8] should access by squares[7][7]
        //squares[9][8] causes an error
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    public boolean onBoard(ChessPosition position) {
        if (position.getRow() > 0 && position.getRow() <= 8) {
            if (position.getColumn() > 0 && position.getColumn() <= 8) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];
        var aPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        var aPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        var aBishopWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        var aBishopBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        var aKingWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        var aKingBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        var aKnightWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        var aKnightBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var aQueenWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        var aQueenBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        var aRookWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        var aRookBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPosition tempPosition;
        //adds pawns
        for (int i = 1; i<=8; i++){
            tempPosition = new ChessPosition(2, i);
            addPiece(tempPosition, aPawnWhite);
            tempPosition = new ChessPosition(7, i);
            addPiece(tempPosition, aPawnBlack);
            if(i == 1){
                tempPosition = new ChessPosition(1,i);
                addPiece(tempPosition,aRookWhite);
                tempPosition = new ChessPosition(1,i+7);
                addPiece(tempPosition,aRookWhite);
                tempPosition = new ChessPosition(8,i);
                addPiece(tempPosition,aRookBlack);
                tempPosition = new ChessPosition(8,i+7);
                addPiece(tempPosition,aRookBlack);
            }
            else if(i == 2){
                tempPosition = new ChessPosition(1,i);
                addPiece(tempPosition,aKnightWhite);
                tempPosition = new ChessPosition(1,i+5);
                addPiece(tempPosition,aKnightWhite);
                tempPosition = new ChessPosition(8,i);
                addPiece(tempPosition,aKnightBlack);
                tempPosition = new ChessPosition(8,i+5);
                addPiece(tempPosition,aKnightBlack);
            }
            else if(i == 3){
                tempPosition = new ChessPosition(1,i);
                addPiece(tempPosition,aBishopWhite);
                tempPosition = new ChessPosition(1,i+3);
                addPiece(tempPosition,aBishopWhite);
                tempPosition = new ChessPosition(8,i);
                addPiece(tempPosition,aBishopBlack);
                tempPosition = new ChessPosition(8,i+3);
                addPiece(tempPosition,aBishopBlack);
            }
            else if(i == 4){
                tempPosition = new ChessPosition(1,i);
                addPiece(tempPosition,aQueenWhite);
                tempPosition = new ChessPosition(1,i+1);
                addPiece(tempPosition,aKingWhite);
                tempPosition = new ChessPosition(8,i);
                addPiece(tempPosition,aQueenBlack);
                tempPosition = new ChessPosition(1,i+1);
                addPiece(tempPosition,aKingBlack);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

}

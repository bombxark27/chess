package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private PieceType type;
    private ChessGame.TeamColor teamColor;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.teamColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }


    public String getPieceName() {
        switch (type) {
            case KING: return "K";
            case QUEEN: return "Q";
            case BISHOP: return "B";
            case KNIGHT: return "N";
            case ROOK: return "R";
            case PAWN: return "P";
        }
        return "";
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (type == PieceType.BISHOP) {
            BishopMovesCalculator myBishop = new BishopMovesCalculator();
            return myBishop.pieceMoves(board,myPosition);
        }
        else if (type == PieceType.KING) {
            KingMovesCalculator myKing = new KingMovesCalculator();
            return myKing.pieceMoves(board,myPosition);
        }
        else if (type == PieceType.KNIGHT){
            KnightMovesCalculator myKnight = new KnightMovesCalculator();
            return myKnight.pieceMoves(board,myPosition);
        }
        else if (type == PieceType.PAWN){
            PawnMovesCalculator myPawn = new PawnMovesCalculator();
            return myPawn.pieceMoves(board,myPosition);
        }
        else if (type == PieceType.QUEEN){
            QueenMovesCalculator myQueen = new QueenMovesCalculator();
            return myQueen.pieceMoves(board,myPosition);
        }
        else if (type == PieceType.ROOK){
            RookMovesCalculator myRook = new RookMovesCalculator();
            return myRook.pieceMoves(board,myPosition);
        }
        return new ArrayList<ChessMove>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return type == that.type && teamColor == that.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, teamColor);
    }

    @Override
    public String toString() {
        if (this.getTeamColor() == ChessGame.TeamColor.BLACK) {
            return getPieceName().toLowerCase();
        }
        return getPieceName();
    }
}

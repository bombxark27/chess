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
        if (this.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (this.getPieceType() == PieceType.PAWN) {
                return "P";
            }
            else if (this.getPieceType() == PieceType.ROOK){
                return "R";
            }
            else if (this.getPieceType() == PieceType.KNIGHT){
                return "N";
            }
            else if (this.getPieceType() == PieceType.BISHOP){
                return "B";
            }
            else if (this.getPieceType() == PieceType.QUEEN){
                return "Q";
            }
            else if (this.getPieceType() == PieceType.KING){
                return "K";
            }
        }
        else if (this.getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (this.getPieceType() == PieceType.PAWN) {
                return "p";
            }
            else if (this.getPieceType() == PieceType.ROOK){
                return "r";
            }
            else if (this.getPieceType() == PieceType.KNIGHT){
                return "n";
            }
            else if (this.getPieceType() == PieceType.BISHOP){
                return "b";
            }
            else if (this.getPieceType() == PieceType.QUEEN){
                return "q";
            }
            else if (this.getPieceType() == PieceType.KING){
                return "k";
            }
        }
        return " ";
    }
}

package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface PieceMovesCalculator {
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}

class PawnMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

class KnightMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

class BishopMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition tempPosition = new ChessPosition(currentRow,currentCol);
        while (board.onBoard(tempPosition)) {
            tempPosition = new ChessPosition(tempPosition.getRow()+1, tempPosition.getColumn()+1);
            if (board.getPiece(tempPosition) == null) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
            }
            else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosition).getTeamColor()) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
                break;
            }
            else if (board.getPiece(myPosition).getTeamColor() == board.getPiece(tempPosition).getTeamColor()) {
                break;
            }
        }
        tempPosition = new ChessPosition(currentRow, currentCol);
        while (board.onBoard(tempPosition)) {
            tempPosition = new ChessPosition(tempPosition.getRow()-1, tempPosition.getColumn()-1);
            if (board.getPiece(tempPosition) == null) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
            }
            else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosition).getTeamColor()) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
                break;
            }
            else if (board.getPiece(myPosition).getTeamColor() == board.getPiece(tempPosition).getTeamColor()) {
                break;
            }
        }
        tempPosition = new ChessPosition(currentRow,currentCol);
        while (board.onBoard(tempPosition)) {
            tempPosition = new ChessPosition(tempPosition.getRow()+1, tempPosition.getColumn()-1);
            if (board.getPiece(tempPosition) == null) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
            }
            else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosition).getTeamColor()) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
                break;
            }
            else if (board.getPiece(myPosition).getTeamColor() == board.getPiece(tempPosition).getTeamColor()) {
                break;
            }
        }
        tempPosition = new ChessPosition(currentRow,currentCol);
        while (board.onBoard(tempPosition)) {
            tempPosition = new ChessPosition(tempPosition.getRow()-1, tempPosition.getColumn()+1);
            if (board.getPiece(tempPosition) == null) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
            }
            else if (board.getPiece(myPosition).getTeamColor() != board.getPiece(tempPosition).getTeamColor()) {
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
                break;
            }
            else if (board.getPiece(myPosition).getTeamColor() == board.getPiece(tempPosition).getTeamColor()) {
                break;
            }
        }

        return possibleMoves;
    }
}

class QueenMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

class KingMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessPosition tempPosition = new ChessPosition(currentRow,currentCol);
        for (int i = -1; i < 2; i++) {
            tempPosition = new ChessPosition(currentRow+1,currentCol+i);
            if (board.onBoard(tempPosition)){
                if(board.getPiece(tempPosition) == null){
                ChessMove move = new ChessMove(myPosition,tempPosition,null);
                possibleMoves.add(move);
                }
                else if(board.getPiece(tempPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    ChessMove move = new ChessMove(myPosition,tempPosition,null);
                    possibleMoves.add(move);
                }
            }
            tempPosition = new ChessPosition(currentRow, currentCol+i);
            if (board.onBoard(tempPosition)){
                if(board.getPiece(tempPosition) == null) {
                    ChessMove move = new ChessMove(myPosition, tempPosition, null);
                    possibleMoves.add(move);
                }
                else if(board.getPiece(tempPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    ChessMove move = new ChessMove(myPosition,tempPosition,null);
                    possibleMoves.add(move);
                }
            }
            tempPosition = new ChessPosition(currentRow-1, currentCol+i);
            if (board.onBoard(tempPosition)){
                if(board.getPiece(tempPosition) == null) {
                    ChessMove move = new ChessMove(myPosition, tempPosition, null);
                    possibleMoves.add(move);
                }
                else if(board.getPiece(tempPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    ChessMove move = new ChessMove(myPosition,tempPosition,null);
                    possibleMoves.add(move);
                }
            }
        }
        return possibleMoves;
    }
}

class RookMovesCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

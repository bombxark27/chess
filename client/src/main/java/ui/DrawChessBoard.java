package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessGame.*;
import chess.ChessPiece;
import chess.ChessPosition;


public class DrawChessBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int LINE_WIDTH_IN_CHARS = 0;
    private static TeamColor color = TeamColor.WHITE;
    private static ChessGame game = new ChessGame();
    private static ChessBoard board = game.getBoard();


//    public DrawChessBoard(TeamColor color, ChessGame game) {
//        this.color = color;
//        board = game.getBoard();
//    }

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = {" a "," b "," c "," d "," e "," f "," g "," h "};
        drawHeader(out,EMPTY);
        for (int boardCol = 7; boardCol >=0; --boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }
        drawHeader(out,EMPTY);
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {

        printHeaderText(out, headerText);
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);

        out.print(player);

        setDefault(out);
    }

    private static void drawChessBoard(PrintStream out) {
        drawRowOfSquares(out);
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out) {

        for (int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES; ++squareRow) {
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + (squareRow+1) + " ");
            for (int squareCol = 7; squareCol >= 0; --squareCol) {
                if (squareCol%2 == 0 && (squareRow+1)%2 == 1){
                    setRed(out);
                }
                else {
                    setBlue(out);
                }
                if (squareCol%2 == 1 && (squareRow+1)%2 == 0) {
                    setRed(out);
                }
                drawChessPiece(out,squareRow+1,squareCol+1);

            }
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + (squareRow+1) + " ");
            setDefault(out);
            out.println();
        }
    }

    private static void drawChessPiece(PrintStream out, int row, int col) {
        String result = null;
        ChessPosition position = new ChessPosition(row,col);
        ChessPiece piece = board.getPiece(position);
        if (piece != null) {
            ChessPiece.PieceType type = piece.getPieceType();
            TeamColor color = piece.getTeamColor();
            switch (type) {
                case PAWN -> result = WHITE_PAWN;
                case BISHOP -> result = WHITE_BISHOP;
                case KNIGHT -> result = WHITE_KNIGHT;
                case ROOK -> result = WHITE_ROOK;
                case QUEEN -> result = WHITE_QUEEN;
                case KING -> result = WHITE_KING;
            }
            if (color == TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_WHITE);

            }
            else {
                out.print(SET_TEXT_COLOR_BLACK);
            }
            out.print(SET_TEXT_BOLD);
        }
        else {
            result = EMPTY;
        }
        out.print(moveCursorToLocation(row+1,col+1));

        out.print(result);
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_TEXT_BOLD_FAINT);
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setDefault(PrintStream out) {
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_BG_COLOR);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlue(PrintStream out) {
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLUE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_BLUE);

        out.print(player);

        setWhite(out);
    }
}

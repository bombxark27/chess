package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessGame.*;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;


public class DrawChessBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int LINE_WIDTH_IN_CHARS = 0;
    private static TeamColor color;
    private static ChessGame gameData;
    private static ChessBoard board;


    public DrawChessBoard(String color, GameData game) {
        if (color != null) {
            this.color = TeamColor.valueOf(color.toUpperCase());
        }
        else {
            this.color = null;
        }
        this.gameData = game.game();
        board = gameData.getBoard();
    }

    public void displayBothBoards() {
        boardBlack();
        System.out.println();
        boardWhite();
    }

    public static void boardBlack() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeaders(out,7);
        drawChessBoardBlack(out);
        drawHeaders(out,7);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        setDefault(out);
    }

    public static void boardWhite() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeaders(out,0);
        drawChessBoardWhite(out);
        drawHeaders(out,0);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        setDefault(out);
    }

    private static void drawHeaders(PrintStream out, int boardCol) {
        setBlack(out);
        String[] headers = {" a "," b "," c "," d "," e "," f "," g "," h "};
        drawHeader(out,EMPTY);
        if (boardCol == 7) {
            for (int col = boardCol; col >= 0; --col) {
                drawHeader(out, headers[col]);
                if (col < BOARD_SIZE_IN_SQUARES - 1) {
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }
            }
        }
        else if (boardCol == 0) {
            for (int col = boardCol; col < BOARD_SIZE_IN_SQUARES; ++col) {
                drawHeader(out, headers[col]);
                if (col < BOARD_SIZE_IN_SQUARES - 1) {
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }
            }
        }
        drawHeader(out, EMPTY);
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

    private static void drawCheckeredBoard(PrintStream out, int squareRow, int squareCol){
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

    private static void drawChessBoardBlack(PrintStream out) {
        for (int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES; ++squareRow) {
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + (squareRow+1) + " ");
            for (int squareCol = 7; squareCol >= 0; --squareCol) {
                drawCheckeredBoard(out,squareRow,squareCol);
            }
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + (squareRow+1) + " ");
            setDefault(out);
            out.println();
        }
    }

    private static void drawChessBoardWhite(PrintStream out) {
        for (int squareRow = 7; squareRow >= 0; --squareRow) {
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" " + (squareRow+1) + " ");
            for (int squareCol = 0; squareCol < BOARD_SIZE_IN_SQUARES; ++squareCol) {
                drawCheckeredBoard(out,squareRow,squareCol);
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
        out.print(result);
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_TEXT_BOLD_FAINT);
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


}

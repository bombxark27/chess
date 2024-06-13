import chess.*;

import client.Repl;

public class Main {

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var port = 8080;
        System.out.println("Started test HTTP server on " + port);
        String url = "http://localhost:" + port + "/";
        if (args.length == 1) {
            url = args[0];
        }
        new Repl(url).run();
    }
}
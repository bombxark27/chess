import chess.*;

import client.Repl;
import client.ResponseException;
import server.Server;

public class Main {
    private static Server server;

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
//        server = new Server();
//        var port = server.run(0);
        var port = 8080;
        System.out.println("Started test HTTP server on " + port);
        String url = "http://localhost:" + port + "/";
        if (args.length == 1) {
            url = args[0];
        }
        new Repl(url).run();
//        server.stop();
    }
}
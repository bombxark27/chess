package client;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run()  {
        Scanner scanner = new Scanner(System.in);
        var result = "";
        System.out.print(client.help());
        while (!result.equals("quit")) {

//            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                if (result != "quit") {
                    System.out.print(result);
                }
                else {
                    System.out.println("Thanks for playing!");
                }

            } catch (Exception e) {
                if (e.getMessage().equals("quit")) {
                    System.out.println("Thanks for playing!");
                    break;
                }
                System.out.println(client.help());
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        System.exit(0);
    }

    private void printPrompt() {
        System.out.print('\n' + SET_TEXT_COLOR_BLACK + ">>>" + SET_TEXT_COLOR_GREEN);
    }
}

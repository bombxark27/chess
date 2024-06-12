package client;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run()  {
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);

            } catch (Exception e) {
                if (e.getMessage().equals("quit")) {
                    System.out.println("Thanks for playing!");
                    break;
                }
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print('\n' + SET_TEXT_COLOR_BLACK + ">>>" + SET_TEXT_COLOR_GREEN);
    }
}

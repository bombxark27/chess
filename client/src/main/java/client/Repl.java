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

            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                if (result.strip() != "quit") {
                    System.out.print(result);
                    System.out.print("\n");
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
                System.out.println(e.getMessage() + '\n');
            }
        }
        System.out.println();
        System.exit(0);
    }

    private void printPrompt() {
        System.out.print(SET_TEXT_COLOR_GREEN + ">>>" + RESET_TEXT_COLOR);
    }
}

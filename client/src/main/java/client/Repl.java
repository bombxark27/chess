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
                if (result != "quit") {
                    System.out.print(result);
                    System.out.print("\n");
                }
                else {
                    System.out.print(RESET_TEXT_COLOR);
                    System.out.println("Thanks for playing!");
                    if (client.getState() == State.SIGNED_IN) {
                        client.logout();
                    }
                    break;
                }

            }
            catch (Exception e) {
                System.out.println(SET_TEXT_COLOR_RED + e.getMessage() + RESET_TEXT_COLOR);
            }
        }
        scanner.close();
    }

    private void printPrompt() {
        System.out.print(SET_TEXT_COLOR_GREEN + ">>>" + RESET_TEXT_COLOR);
    }
}

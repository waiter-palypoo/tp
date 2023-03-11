package seedu.duke;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager(0);
        Ui.printGreeting();
        Scanner in = new Scanner(System.in);
        String userCmd = in.nextLine();
        while (!userCmd.equals("bye")) {
            try {
                Parser.handleCmd(userCmd, expenseManager);
            } catch (DukeException e) {
                System.out.println(e);
            }
            userCmd = in.nextLine();
        }
        in.close();
        Ui.printGoodbye();
    }
}

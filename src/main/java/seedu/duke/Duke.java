package seedu.duke;

import java.util.Scanner;

public class Duke {

    public static void main(String[] args) {
        Storage storage = new Storage();
        ExpenseManager expenseManager = storage.loadDataExpenses();
        Ui.printGreeting();
        expenseManager.startupDueDateCheck();
        Scanner in = new Scanner(System.in);
        String userCmd = in.nextLine();
        while (!userCmd.equals("bye")) {
            try {
                Parser.handleCmd(userCmd, expenseManager);
                storage.saveExpenses(expenseManager);
            } catch (DukeException e) {
                System.out.println(e);
            }
            userCmd = in.nextLine();
        }
        in.close();
        Ui.printGoodbye();
    }
}

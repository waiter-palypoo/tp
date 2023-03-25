package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    public static ArrayList<Expense> expenses = new ArrayList<>();
    public static ArrayList<FutureExpense> futureExpenses = new ArrayList<>();
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager(0, expenses, futureExpenses);
        Ui.printGreeting();
        Storage.loadDataExpenses(expenses);
        Storage.loadDataFutureExpenses(futureExpenses);
        Scanner in = new Scanner(System.in);
        String userCmd = in.nextLine();
        while (!userCmd.equals("bye")) {
            try {
                Parser.handleCmd(userCmd, expenseManager, expenses, futureExpenses);
            } catch (DukeException e) {
                System.out.println(e);
            }
            userCmd = in.nextLine();
        }
        in.close();
        Ui.printGoodbye();
    }
}

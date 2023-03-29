package seedu.duke;

import java.util.ArrayList;

public class Ui {
    /**
     * Print the Input guidelines for the reader
     */
    public static void printInstructions() {
        System.out.println("LIST OF ALL COMMANDS:");
        System.out.println("add expense + name + $/\"amount\" + d/\"yyyymmdd\" to add an expense");
        System.out.println("edit expense +  id/\"expense id\" + in/\"amount/date/category\" to edit an expense");
        System.out.println("delete expense +  id/\"expense id\" to delete an expense");
        System.out.println("set balance +  $/\"amount\" to set your balance");
        System.out.println("list expenses to list all past expenses");
        System.out.println("expenses above + $/\"amount\" to see all expenses above amount");
        System.out.println("expenses above + $/\"amount\" to see all expenses below amount");
        System.out.println("check balance to check the remaining balance");
        System.out.println("add future expense + name + $/\"amount\" + d/\"yyyymmdd\" to add a future expense");
        System.out.println("edit future expense + id/\"expense id\" + in/\"amount/date/category\" "
                           + "to edit a future expense");
        System.out.println("delete future expense + id/\"expense id\" to delete a future expense");
        System.out.println("list future expenses to list all future expenses");
        System.out.println("check upcoming expenses to list all future expenses within an upcoming time period");
        System.out.println("pay \"id\" to pay for a future expense and move it to the list of past expenses");
        System.out.println("bye to exit the program");
    }

    /**
     * Print the Horizontal Line that seperates between different user Inputs
     */
    public static void printHorizontalLine() {
        System.out.println("-".repeat(80));
    }

    public static String[] getFormattedList(ArrayList<Expense> expenses) {
        String[] ret = new String[expenses.size() + 1];
        ret[0] = "Here are the list of your expenses !\n";
        for (int i = 0; i < expenses.size(); ++i) {
            ret[i + 1] = String.format("%d. %s", i + 1, expenses.get(i));
        }
        return ret;
    }

    public static void printLines(String... lines) {
        printHorizontalLine();
        for (String line : lines) {
            System.out.println(line);
        }
        printHorizontalLine();
    }

    /**
     * Print the message for user to signal that Duke cannot understand the input
     */
    public static void printFalseInput() {
        printHorizontalLine();
        System.out.println("Sorry Duke could not understand your input :> please follow the instructions");
        System.out.println();
        printInstructions();
        printHorizontalLine();
    }

    /**
     * Print the Greeting when Duke is started
     */
    public static void printGreeting() {
        String logo = "___  ___                      ___  ___          _\n"
                      + "|  \\/  |                      |  \\/  |         | |\n"
                      + "| .  . | ___  _ __   ___ _   _| .  . | __ _ ___| |_ ___ _ __\n"
                      + "| |\\/| |/ _ \\| '_ \\ / _ \\ | | | |\\/| |/ _` / __| __/ _ \\ '__|\n"
                      + "| |  | | (_) | | | |  __/ |_| | |  | | (_| \\__ \\ ||  __/ |\n"
                      + "\\_|  |_/\\___/|_| |_|\\___|\\__, \\_|  |_/\\__,_|___/\\__\\___|_|\n"
                      + "                         __/ |\n"
                      + "                        |___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you ?");
        printHorizontalLine();
        printInstructions();
        printHorizontalLine();
    }

    /**
     * Print the Goodbye message when user exits Duke
     */
    public static void printGoodbye() {
        printHorizontalLine();
        System.out.println("Thanks for using MoneyMaster! See ya!");
        System.out.println(" /\\_/\\  ");
        System.out.println("( o.o ) ");
        System.out.println(" > ^ <  ");
        printHorizontalLine();
    }

    public static void printChoice() {
        printHorizontalLine();
        System.out.println("Which of the following category is this expense? Input a single number!");
        System.out.println("1. Food & Drinks");
        System.out.println("2. Shopping");
        System.out.println("3. Transportation");
        System.out.println("4. Life & Entertainment");
        System.out.println("5. Investments");
        System.out.println("6. Communication & Technology");
        System.out.println("7. Others");
        printHorizontalLine();
    }
}

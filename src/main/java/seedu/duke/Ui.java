package seedu.duke;

import java.util.ArrayList;

public class Ui {

    private static final String LOGO = "___  ___                      ___  ___          _\n"
                                       + "|  \\/  |                      |  \\/  |         | |\n"
                                       + "| .  . | ___  _ __   ___ _   _| .  . | __ _ ___| |_ ___ _ __\n"
                                       + "| |\\/| |/ _ \\| '_ \\ / _ \\ | | | |\\/| |/ _` / __| __/ _ \\ '__|\n"
                                       + "| |  | | (_) | | | |  __/ |_| | |  | | (_| \\__ \\ ||  __/ |\n"
                                       + "\\_|  |_/\\___/|_| |_|\\___|\\__, \\_|  |_/\\__,_|___/\\__\\___|_|\n"
                                       + "                         __/ |\n"
                                       + "                        |___/";
    private static final String ERROR_MESSAGE =
            "Sorry Duke could not understand your input :> please follow the instructions";
    private static final String WELCOME_MESSAGE = "Hello! I'm Duke \n"
                                                  + "What can I do for you ?";
    private static final String EXIT_MESSAGE = "Thanks for using MoneyMaster! See ya! \n"
                                               + " /\\_/\\  \n"
                                               + "( o.o ) \n"
                                               + " > ^ <  ";
    private static final String CATEGORY_CHOICE =
            "Which of the following category is this expense? Input a single number!\n"
            + "1. Food & Drinks\n"
            + "2. Shopping\n"
            + "3. Transportation\n"
            + "4. Life & Entertainment\n"
            + "5. Investments\n"
            + "6. Communication & Technology\n"
            + "7. Others";
    private static final String COMMAND_LIST =
            "LIST OF ALL COMMANDS:\n"
            + "add expense + name + $/\"amount\" + d/\"yyyymmdd\" to add an expense\n"
            + "edit expense +  id/\"expense id\" + in/\"amount/date/category/name\" to edit an expense\n"
            + "delete expense +  id/\"expense id\" to delete an expense\n"
            + "clear expenses to clear all current expenses\n"
            + "set balance +  $/\"amount\" to set your balance\n"
            + "list expenses to list all past expenses\n"
            + "list expenditure by category to list total expenditure on each category\n"
            + "expenses above + $/\"amount\" to see all expenses above amount\n"
            + "expenses below + $/\"amount\" to see all expenses below amount\n"
            + "check balance to check the remaining balance\n"
            + "set currency {CURRENCY_SYMBOL} to set the app to your desired currency\n"
            + "get currency to get the currently set currency\n"
            + "add future expense + name + $/\"amount\" + d/\"yyyymmdd\" to add a future expense\n"
            + "edit future expense + id/\"expense id\" + in/\"amount/date/category/name\" to edit a future expense\n"
            + "delete future expense + id/\"expense id\" to delete a future expense\n"
            + "clear future expenses to clear all future expenses\n"
            + "list future expenses to list all future expenses\n"
            + "check upcoming expenses to list all future expenses within an upcoming time period\n"
            + "pay \"id\" to pay for a future expense and move it to the list of past expenses\n"
            + "bye to exit the program";

    /**
     * Print the Horizontal Line that separates between different user Inputs
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
        System.out.println(ERROR_MESSAGE);
        System.out.println();
        System.out.println(COMMAND_LIST);
        printHorizontalLine();
    }

    /**
     * Print the Greeting when Duke is started
     */
    public static void printGreeting() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(WELCOME_MESSAGE);
        printHorizontalLine();
        System.out.println(COMMAND_LIST);
        printHorizontalLine();
    }

    /**
     * Print the Goodbye message when user exits Duke
     */
    public static void printGoodbye() {
        printHorizontalLine();
        System.out.println(EXIT_MESSAGE);
        printHorizontalLine();
    }

    public static void printChoice() {
        printHorizontalLine();
        System.out.println(CATEGORY_CHOICE);
        printHorizontalLine();
    }
}

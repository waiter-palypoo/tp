package seedu.duke;

public class Ui {
    /**
     * Print the Input guidelines for the reader 
     */
    public static void printInstructions() {
        System.out.println("LIST OF ALL COMMANDS:");
        System.out.println("add expense +  $/\"amount\" + d/\"yyyymmdd\" to add an expense");
        System.out.println("edit expense +  id/\"expense id\" + in/\"amount/date/category\" to edit an expense");
        System.out.println("set balance +  $/\"amount\" to set your balance");
        System.out.println("list expenses to list all past expenses");
        System.out.println("check balance to check the remaining balance");
    }

    /**
     * Print the Horizontal Line that seperates between different user Inputs
     */
    public static void printHorizontalLine() {
        for (int i = 0; i <= 30; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    /**
     * Print the message for user to signal that Duke cannot understand the input
     */
    public static void printfalseInput() {
        System.out.println("Sorry Duke could not understand your input :> please follow the instructions");
        printInstructions();
    }

    /**
     * Print the Greeting when Duke is started
     */
    public static void printGreeting() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
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
        System.out.println("Thanks for using Duke! See ya!");
        System.out.println(" /\\_/\\  ");
        System.out.println("( o.o ) ");
        System.out.println(" > ^ <  ");
    }

    public static void printChoice() {
        System.out.println("Which of the following category is this expense? Input a single number!");
        System.out.println("1. Food & Drinks");
        System.out.println("2. Shopping");
        System.out.println("3. Transportation");
        System.out.println("4. Life & Entertainment");
        System.out.println("5. Investments");
        System.out.println("6. Communication & Technology");
        System.out.println("7. Others");
    }

}

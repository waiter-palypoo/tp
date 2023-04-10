package seedu.duke;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Parser {
    /**
     * Parses the user input and executes the command
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input.
     * @throws DukeException to handle any exceptions found in user input
     */
    public static void handleCmd(String userCmd, ExpenseManager expenseManager) throws DukeException {
        ArrayList<Expense> expenses = expenseManager.getExpenses();
        ArrayList<FutureExpense> futureExpenses = expenseManager.getFutureExpenses();
        Scanner in = new Scanner(System.in);
        try {
            if (userCmd.startsWith("add expense")) {
                Ui.printChoice();
                String choice = in.nextLine();
                int choiceNum = Integer.parseInt(choice);
                executeAddExpense(userCmd, expenseManager, choiceNum);
            } else if (userCmd.startsWith("edit expense")) {
                executeEditExpense(expenseManager, userCmd, expenses);
            } else if (userCmd.startsWith("delete expense")) {
                executeDeleteExpense(expenseManager, userCmd);
            } else if (userCmd.startsWith("set balance")) {
                executeSetBudget(expenseManager, userCmd);
            } else if (userCmd.startsWith("get currency")) {
                Ui.printLines(String.format("Your currency is currently set to: %s", expenseManager.getCurrency()));
            } else if (userCmd.startsWith("list expenses") || userCmd.startsWith("list expense")) {
                expenseManager.printExpense();
            } else if (userCmd.startsWith("check balance")) {
                Ui.printHorizontalLine();
                System.out.println(String.format("Your current balance is: %.2f %s",
                                                 expenseManager.getTotalBalance() * expenseManager.getRate(),
                                                 expenseManager.getCurrency()));
                Ui.printHorizontalLine();
            } else if (userCmd.startsWith("add future expense")) {
                Ui.printChoice();
                String choice = in.nextLine();
                int choiceNum = Integer.parseInt(choice);
                executeAddFutureExpense(userCmd, expenseManager, choiceNum);
            } else if (userCmd.startsWith("edit future expense")) {
                executeEditFutureExpense(expenseManager, userCmd, futureExpenses);
            } else if (userCmd.startsWith("set currency")) {
                try {
                    String currency = userCmd.split(" ")[2];
                    if (!CurrencyLoader.getCurrencyLoader().currencyExists(currency)) {
                        Ui.printLines(String.format(
                                "The currency %s is not valid. Please try again with a valid currency symbol",
                                currency));
                    } else {
                        expenseManager.setCurrency(currency);
                        Ui.printLines(String.format("Your currency has been successfully set to: %s", currency));
                    }
                } catch (ArrayIndexOutOfBoundsException exc) {
                    throw new DukeException("Currency symbol cannot be empty.");
                }
            } else if (userCmd.startsWith("delete future expense")) {
                executeDeleteFutureExpense(expenseManager, userCmd);
            } else if (userCmd.startsWith("list future expenses") || userCmd.startsWith("list future expense")) {
                expenseManager.printFutureExpenses();
            } else if (userCmd.startsWith("check upcoming expenses")) {
                executeCheckUpcomingExpenses(expenseManager);
            } else if (userCmd.startsWith("pay")) {
                executePayFutureExpense(expenseManager, userCmd);
            } else if (userCmd.startsWith("expenses above $/")) {
                double amount = Double.parseDouble(userCmd.substring(userCmd.indexOf("$/") + 2));
                executeGetExpenseAbove(amount, expenseManager);
            } else if (userCmd.startsWith("expenses below $/")) {
                double amount = Double.parseDouble(userCmd.substring(userCmd.indexOf("$/") + 2));
                executeGetExpenseBelow(amount, expenseManager);
            } else if (userCmd.startsWith("list expenditure by category")) {
                expenseManager.printExpenditureByCategory();
            } else if (userCmd.startsWith("clear expenses")) {
                executeClearExpenses(expenseManager);
            } else if (userCmd.startsWith("clear future expenses")) {
                executeClearFutureExpenses(expenseManager);
            } else {
                Ui.printFalseInput();
            }
        } catch (NumberFormatException nfe) {
            Ui.printHorizontalLine();
            System.out.println("Invalid input. Please enter command again.");
            Ui.printHorizontalLine();
        } catch (DukeException e) {
            Ui.printHorizontalLine();
            System.out.println(e.getMessage());
            Ui.printHorizontalLine();
        }
    }

    /**
     * Executes the add expense command by extracting necessary information from user
     * input.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input
     * @param choice Stores the category choice
     * @throws DukeException to handle any exception found in user input
     */
    public static void executeAddExpense(String userCmd, ExpenseManager expenseManager, int choice)
            throws DukeException {
        double amount = extractAmount(userCmd);
        if (amount < 0) {
            throw new DukeException("Expense price cannot be negative! Adding expense failed");
        }
        if(amount > 1000000000000.0) {
            throw new DukeException("Expense price is too large! Adding expense failed");
        }
        if (amount < 0.01) {
            throw new DukeException("Expense price is too small! Adding expense failed");
        }
        try {
            LocalDate date = extractDate(userCmd);
            String name = extractName(userCmd);
            if (name.isBlank()) {
                throw new DukeException("Expense must have a name");
            }
            String category = getCategory(choice);
            if (category.equals("wrong input")) {
                Ui.printHorizontalLine();
                System.out.println("Invalid selection. Please follow the instructions and try again.");
                Ui.printHorizontalLine();
            } else {
                expenseManager.addExpense(name, amount, date, category);
            }
        } catch (DateTimeException dte) {
            throw new DukeException("Please enter a valid date (YYYYMMDD)");
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(
                    "Invalid syntax for add expense command. Please follow the instructions and try again");
        }
    }

    /**
     * Translates the number entered by the user to the corresponding category
     * it represents.
     *
     * @param choice Stores the category choice
     * @return the chosen category
     */
    private static String getCategory(int choice) {
        try {
            switch (choice) {
            case 1:
                return "Food & Drinks";
            case 2:
                return "Shopping";
            case 3:
                return "Transportation";
            case 4:
                return "Life & Entertainment";
            case 5:
                return "Investments";
            case 6:
                return "Communication & Technology";
            case 7:
                return "Others";
            default:
                return "wrong input";
            }
        } catch (NumberFormatException nfe) {
            return "wrong input";
        }
    }

    /**
     * Extracts name of the expense from the user input.
     *
     * @param userCmd Stores the User input
     * @return the name of the expense
     */
    private static String extractName(String userCmd) {
        int endIndex = userCmd.indexOf("$/");
        return userCmd.substring(12, endIndex);
    }

    /**
     * Extracts amount of the expense from the user input.
     *
     * @param userCmd Stores the User input
     * @return the amount of the expense
     * @throws DukeException if expense amount or date is empty
     */
    private static double extractAmount(String userCmd) throws DukeException {
        int startIndex = userCmd.indexOf("$/");
        int endIndex = userCmd.indexOf(" ", startIndex);
        if (startIndex < 0) {
            throw new DukeException("Expense Amount cannot be empty.");
        } else if (endIndex < 0) {
            throw new DukeException("Expense Date cannot be empty.");
        } else {
            String amountString = userCmd.substring(startIndex + 2, endIndex);
            return Double.parseDouble(amountString);
        }
    }

    /**
     * Extracts date of the expense from the user input.
     *
     * @param input Stores the User input
     * @return the date of the expense
     */
    private static LocalDate extractDate(String input) {
        int startIndex = input.indexOf("d/");
        String dateString = input.substring(startIndex + 2);
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(4, 6));
        int day = Integer.parseInt(dateString.substring(6));
        return LocalDate.of(year, month, day);
    }

    /**
     * Executes the set budget command by extracting the amount from user
     * input and sets the total balance to that amount.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @throws DukeException if budget amount is empty
     */
    public static void executeSetBudget(ExpenseManager expenseManager, String userCmd) throws DukeException {
        int startIndex = userCmd.indexOf("$/");
        if (startIndex < 0) {
            throw new DukeException("Budget amount cannot be empty.");
        } else {
            double balance = Double.parseDouble(userCmd.substring(startIndex + 2));
            Ui.printHorizontalLine();
            System.out.println(
                    String.format("Your budget has been set to %.2f %s", balance, expenseManager.getCurrency()));
            Ui.printHorizontalLine();
            expenseManager.setTotalBalance(balance / expenseManager.getRate());
        }
    }

    /**
     * Executes the edit expense command by extracting the id and field of the expense to be edited
     * from the user input and calls the required method to edit that field.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input
     * @param expenses ArrayList used to store the expenses
     * @throws DukeException if expense id is invalid or incorrect syntax is used
     */
    public static void executeEditExpense(ExpenseManager expenseManager, String userCmd, ArrayList<Expense> expenses)
            throws DukeException {
        if (!userCmd.contains("id/") || !userCmd.contains("in/")) {
            throw new DukeException(
                    "Invalid syntax for the edit expense command. Please follow the instructions and try again");
        }
        int id;
        try {
            id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid digit for the expense id");
        }
        if (id > expenseManager.getExpensesSize() || id < 0) {
            throw new DukeException("This expense id does not exist. Please provide a valid expense id.");
        } else {
            Ui.printHorizontalLine();
            Scanner in = new Scanner(System.in);
            switch (userCmd.substring(userCmd.indexOf("in/") + 3)) {
            case "amount":
                editExpenseAmount(expenseManager, id, in);
                break;
            case "date":
                editExpenseDate(expenseManager, id, in);
                break;
            case "category":
                editExpenseCategory(expenseManager, id, in);
                break;
            case "name":
                editExpenseName(expenseManager, id, in);
                break;
            default:
                Ui.printFalseInput();
            }
            Ui.printHorizontalLine();
        }
    }

    /**
     * Edits the name of the particular expense .
     *
     * @param id Stores the expense id
     * @param expenseManager instance of expenseManager to execute the command
     */
    private static void editExpenseName(ExpenseManager expenseManager, int id, Scanner in) {
        System.out.println("Enter a new name for this expense!");
        Ui.printHorizontalLine();
        String newName = in.nextLine();
        if (newName.isBlank()) {
            System.out.println("Expense name cannot be blank!");
        } else {
            String oldName = expenseManager.get(id - 1).getName();
            expenseManager.get(id - 1).setName(newName);
            Ui.printHorizontalLine();
            System.out.println("Successfully changed expense name from '" + oldName + "' to '" + newName + "'");
        }
    }

    /**
     * Edits the amount of the particular expense .
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param id Stores the expense id
     * @throws DukeException if amount is negative
     */
    private static void editExpenseAmount(ExpenseManager expenseManager, int id, Scanner in) throws DukeException {
        System.out.println("Enter a new amount spent! Just enter a number!");
        Ui.printHorizontalLine();
        Double newAmount = Double.parseDouble(in.nextLine()) / expenseManager.getRate();
        if (newAmount < 0) {
            throw new DukeException("Expense price cannot be negative");
        } else {
            Double newBalance = expenseManager.getTotalBalance() + expenseManager.get(id - 1).getAmount() - newAmount;
            expenseManager.get(id - 1).setAmount(newAmount);
            expenseManager.setTotalBalance(newBalance);
            Ui.printHorizontalLine();
            System.out.println("Change in amount successful! Balance has also been recalculated");
        }
    }

    /**
     * Edits the date of the particular expense .
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param id Stores the expense id
     * @throws DukeException if date is invalid
     */
    private static void editExpenseDate(ExpenseManager expenseManager, int id, Scanner in) throws DukeException {
        System.out.println("Enter a new date in the form of YYYYMMDD!");
        Ui.printHorizontalLine();
        String newDate = in.nextLine();
        int year = Integer.parseInt(newDate.substring(0, 4));
        int month = Integer.parseInt(newDate.substring(4, 6));
        int day = Integer.parseInt(newDate.substring(6));
        try {
            expenseManager.get(id - 1).setDate(LocalDate.of(year, month, day));
            Ui.printHorizontalLine();
            System.out.println("Change in date successful!");
        } catch (DateTimeException dte) {
            throw new DukeException("Invalid date");
        }
    }

    /**
     * Edits the category of the particular expense .
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param id Stores the expense id
     */
    private static void editExpenseCategory(ExpenseManager expenseManager, int id, Scanner in) {
        Ui.printChoice();
        int choice = Integer.parseInt(in.nextLine());
        String newCategory = getCategory(choice);
        if (newCategory.equals("wrong input")) {
            System.out.println("Wrong category choice. Please enter edit command again");
            Ui.printHorizontalLine();
        } else {
            expenseManager.get(id - 1).setCategory(newCategory);
            Ui.printHorizontalLine();
            System.out.println("Change in category successful!");
        }
    }

    /**
     * Deletes an expense from the list of expenses based on the expense id
     * and adjusts the total balance accordingly.
     * Prints the details of the deleted expense.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input.
     * @throws DukeException if the expense id is not entered or is invalid.
     */
    public static void executeDeleteExpense(ExpenseManager expenseManager, String userCmd) throws DukeException {
        int startIndex = userCmd.indexOf("id/");
        if (startIndex < 0) {
            throw new DukeException("Expense id cannot be empty.");
        } else {
            try {
                int id = Integer.parseInt(userCmd.substring(startIndex + 3));
                if (id > expenseManager.getExpensesSize() || id <= 0) {
                    throw new DukeException("This expense id does not exist. Please provide a valid expense id.");
                } else {
                    Ui.printHorizontalLine();
                    Expense deletedExpense = expenseManager.get(id - 1);
                    Double updatedBalance = expenseManager.getTotalBalance() + deletedExpense.getAmount();
                    expenseManager.setTotalBalance(updatedBalance);
                    expenseManager.remove(id - 1);
                    System.out.println("Noted. I've removed this expense:");
                    System.out.println(deletedExpense);
                    Ui.printHorizontalLine();
                }
            } catch (NumberFormatException nfe) {
                throw new DukeException("Please enter a valid expense id.");
            }
        }
    }

    /**
     * Clears list of expenses upon confirmation from the user and sets the
     * total balance to 0.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @throws DukeException if the expense list is empty
     */
    public static void executeClearExpenses(ExpenseManager expenseManager) throws DukeException {
        if (1 > expenseManager.getExpensesSize()) {
            throw new DukeException("You have no expenses to clear.");
        } else {
            Ui.printHorizontalLine();
            System.out.println("Are you sure you would like to remove all expenses? 'Y' or 'N'");
            Scanner in = new Scanner(System.in);
            Ui.printHorizontalLine();
            String confirmationClear = in.nextLine();
            Ui.printHorizontalLine();
            if (confirmationClear.toUpperCase(Locale.ROOT).equals("Y")) {
                expenseManager.removeAllExpenses();
                expenseManager.setTotalBalance(0);
                System.out.println("You have cleared all your expenses.");
            } else if (confirmationClear.equals("N")) {
                System.out.println("Okay! Expenses will not be cleared.");
            } else {
                System.out.println("Invalid confirmation");
            }
            Ui.printHorizontalLine();
        }
    }

    /**
     * Executes the add future expense command by extracting necessary information from
     * user input.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input
     * @param choice Stores the category choice
     * @throws DukeException to handle any exception found in user input
     */
    public static void executeAddFutureExpense(String userCmd, ExpenseManager expenseManager, int choice)
            throws DukeException {
        double amount = extractAmount(userCmd);
        if (amount < 0) {
            throw new DukeException("Expense price cannot be negative! Adding expense failed");
        }
        if(amount > 1000000000000.0) {
            throw new DukeException("Expense price is too large! Adding expense failed");
        }
        if (amount < 0.01) {
            throw new DukeException("Expense price is too small! Adding expense failed");
        }
        try {
            LocalDate dueDate = extractDate(userCmd);
            if (dueDate.isBefore(LocalDate.now())) {
                Ui.printHorizontalLine();
                System.out.println("Please enter a future date");
                Ui.printHorizontalLine();
            } else {
                String name = userCmd.substring(19, userCmd.indexOf("$/") - 1);
                String category = getCategory(choice);
                if (category.equals("wrong input")) {
                    Ui.printHorizontalLine();
                    System.out.println("Invalid selection. Please add expense again.");
                    Ui.printHorizontalLine();
                } else if (name.isBlank()) {
                    throw new DukeException("Future expense must have a name");
                } else {
                    expenseManager.addFutureExpense(name, amount, dueDate, category);
                }
            }
        } catch (DateTimeException dte) {
            throw new DukeException("Please enter a valid date (YYYYMMDD)");
        } catch (StringIndexOutOfBoundsException exc) {
            throw new DukeException(
                    "Invalid syntax for add future expense command. Please follow the instructions and try again");
        }
    }

    /**
     * Executes the edit future expense command by extracting the id and field of the future expense to be
     * edited from the user input and calls the required method to edit that field.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input
     * @param futureExpenses ArrayList used to store the future expenses
     * @throws DukeException if expense id is invalid or incorrect syntax is used
     */
    public static void executeEditFutureExpense(ExpenseManager expenseManager, String userCmd,
                                                ArrayList<FutureExpense> futureExpenses) throws DukeException {
        if (!userCmd.contains("id/") || !userCmd.contains("in/")) {
            throw new DukeException(
                    "Invalid syntax for the edit future expense command. Please follow the instructions and try again");
        }
        int id;
        try {
            id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
        } catch (NumberFormatException nfe) {
            throw new DukeException("Please enter a valid digit for the expense id.");
        }
        if (id > expenseManager.getFutureSize() || id < 0) {
            throw new DukeException("This expense id does not exist. Please provide a valid expense id.");
        } else {
            Ui.printHorizontalLine();
            Scanner in = new Scanner(System.in);
            switch (userCmd.substring(userCmd.indexOf("in/") + 3)) {
            case "amount":
                editFutureExpenseAmount(expenseManager, id, in);
                break;
            case "date":
                editFutureExpenseDate(expenseManager, id, in);
                break;
            case "category":
                editFutureExpenseCategory(expenseManager, id, in);
                break;
            case "name":
                editFutureExpenseName(expenseManager, id, in);
                break;
            default:
                Ui.printFalseInput();
            }
            Ui.printHorizontalLine();
        }
    }

    /**
     * Edits the name of the particular future expense .
     *
     * @param id Stores the expense id
     * @param expenseManager instance of expenseManager to execute the command
     */
    private static void editFutureExpenseName(ExpenseManager expenseManager, int id, Scanner in) {
        System.out.println("Enter a new name for this future expense!");
        Ui.printHorizontalLine();
        String newName = in.nextLine();
        if (newName.isBlank()) {
            System.out.println("Future expense name cannot be blank!");
        } else {
            String oldName = expenseManager.getFutureExpense(id - 1).getName();
            expenseManager.getFutureExpense(id - 1).setName(newName);
            Ui.printHorizontalLine();
            System.out.println("Successfully changed future expense name from '" + oldName + "' to '" + newName + "'");
        }
    }

    /**
     * Edits the amount of the particular future expense .
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param id Stores the expense id
     * @throws DukeException if amount is negative
     */
    private static void editFutureExpenseAmount(ExpenseManager expenseManager, int id, Scanner in) throws DukeException {
        System.out.println("Enter a new amount spent! Just enter a number!");
        Ui.printHorizontalLine();
        Double newAmount = Double.parseDouble(in.nextLine()) / expenseManager.getRate();
        if (newAmount < 0) {
            throw new DukeException("Expense price cannot be negative");
        }
        expenseManager.getFutureExpense(id - 1).setAmount(newAmount);
        System.out.println("Change in amount successful!");
        Ui.printHorizontalLine();
    }

    /**
     * Edits the date of the particular future expense .
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param id Stores the expense id
     * @throws DukeException if date is invalid
     */
    private static void editFutureExpenseDate(ExpenseManager expenseManager, int id, Scanner in) throws DukeException {
        System.out.println("Enter a new date in the form of YYYYMMDD!");
        Ui.printHorizontalLine();
        String newDate = in.nextLine();
        int year = Integer.parseInt(newDate.substring(0, 4));
        int month = Integer.parseInt(newDate.substring(4, 6));
        int day = Integer.parseInt(newDate.substring(6));
        try {
            LocalDate editDate = LocalDate.of(year, month, day);
            if (editDate.isBefore(LocalDate.now())) {
                System.out.println(
                        "Date entered is not a future date. Please enter the edit command again with correct date");
            } else {
                expenseManager.getFutureExpense(id - 1).setDueDate(editDate);
                System.out.println("Change in date successful!");
                Ui.printHorizontalLine();
            }
        } catch (DateTimeException dte) {
            throw new DukeException("Invalid date");
        }
    }

    /**
     * Edits the category of the particular future expense .
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param id Stores the expense id
     */
    private static void editFutureExpenseCategory(ExpenseManager expenseManager, int id, Scanner in) {
        Ui.printChoice();
        int choice = Integer.parseInt(in.nextLine());
        String newCategory = getCategory(choice);
        if (newCategory.equals("wrong input")) {
            System.out.println("Wrong category choice. Please enter edit command again");
            Ui.printHorizontalLine();
        } else {
            expenseManager.getFutureExpense(id - 1).setCategory(newCategory);
            System.out.println("Change in category successful!");
            Ui.printHorizontalLine();
        }
    }

    /**
     * Deletes an expense from the list of future expenses based on the expense id.
     * Prints the details of the deleted expense.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input.
     * @throws DukeException if the expense id is not entered or is invalid.
     */
    public static void executeDeleteFutureExpense(ExpenseManager expenseManager, String userCmd) throws DukeException {
        if (!userCmd.contains("id/")) {
            throw new DukeException("Invalid syntax for the delete future expense command. "
                                    + "Please follow the instructions and try again");
        }
        int startIndex = userCmd.indexOf("id/");
        if (startIndex < 0) {
            throw new DukeException("Expense id cannot be empty.");
        } else {
            try {
                int id = Integer.parseInt(userCmd.substring(startIndex + 3));
                if (id > expenseManager.getFutureSize() || id <= 0) {
                    throw new DukeException("This expense id does not exist. Please provide a valid expense id. " +
                                            expenseManager.getExpensesSize());
                } else {
                    Ui.printHorizontalLine();
                    Expense deletedExpense = expenseManager.getFutureExpense(id - 1);
                    expenseManager.removeFutureExpense(id - 1);
                    System.out.println("Noted. I've removed this expense:");
                    System.out.println(deletedExpense);
                    Ui.printHorizontalLine();
                }
            } catch (NumberFormatException nfe) {
                throw new DukeException("Please enter a valid expense id.");
            }
        }
    }

    /**
     * Clears list of future expenses upon confirmation from the user.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @throws DukeException if the future expense list is empty
     */
    public static void executeClearFutureExpenses(ExpenseManager expenseManager) throws DukeException {
        if (1 > expenseManager.getFutureSize()) {
            throw new DukeException("You have no future expenses to clear.");
        } else {
            Ui.printHorizontalLine();
            System.out.println("Are you sure you would like to remove all future expenses? 'Y' or 'N'");
            Scanner in = new Scanner(System.in);
            Ui.printHorizontalLine();
            String confirmationClear = in.nextLine();
            Ui.printHorizontalLine();
            if (confirmationClear.toUpperCase(Locale.ROOT).equals("Y")) {
                expenseManager.removeAllFutureExpenses();
                System.out.println("You have cleared all your future expenses.");
            } else if (confirmationClear.equals("N")) {
                System.out.println("Okay! Your future expenses will not be cleared.");
            } else {
                System.out.println("Invalid confirmation");
            }
            Ui.printHorizontalLine();
        }
    }

    /**
     * Executes the command to display the upcoming expenses for a period of time specified by the user.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @throws DukeException if an invalid number is entered.
     */
    public static void executeCheckUpcomingExpenses(ExpenseManager expenseManager) throws DukeException {
        System.out.println("Choose a period to show upcoming expenses within (type the number): ");
        System.out.println("1. 1 week");
        System.out.println("2. 1 month");
        System.out.println("3. 3 months");
        Scanner in = new Scanner(System.in);
        try {
            int period = Integer.parseInt(in.nextLine());
            while (period < 1 || period > 3) {
                System.out.println("Please choose a number from 1 to 3!");
                period = Integer.parseInt(in.nextLine());
            }
            expenseManager.checkUpcomingExpenses(period);
        } catch (NumberFormatException e) {
            throw new DukeException("Please input a valid number!");
        }
    }

    /**
     * Executes the command to pay a particular future expense by extracting the id of the
     * expense from the user input and passing it to the required method.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param userCmd Stores the User input.
     * @throws DukeException if an invalid number is entered.
     */
    public static void executePayFutureExpense(ExpenseManager expenseManager, String userCmd) throws DukeException {
        if (userCmd.split(" ").length == 2) {
            String idString = userCmd.split(" ")[1].trim();
            if (idString == null || Integer.parseInt(idString) <= 0 ||
                Integer.parseInt(idString) > expenseManager.getExpensesSize()) {
                throw new DukeException("Please input a valid number!");
            }
            try {
                int id = Integer.parseInt(idString);
                expenseManager.payFutureExpense(id - 1);
            } catch (NumberFormatException e) {
                throw new DukeException("Please input a valid number!");
            }
        } else {
            throw new DukeException(
                    "Invalid syntax for pay future expense command. Please follow the instructions and try again");
        }
    }

    /**
     * Display the expenses above a certain amount specified by the user.
     * Prints the amount entered by the user.
     * If no such expenses exist, displays the required message.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param amount Stores the amount.
     * @throws DukeException to handle any exception encountered
     */
    private static void executeGetExpenseAbove(double amount, ExpenseManager expenseManager) throws DukeException {
        System.out.println(amount);
        ArrayList<Expense> sortedExpenses = new ArrayList<>();
        sortedExpenses = expenseManager.getExpensesAbove(amount);
        if (sortedExpenses.isEmpty()) {
            System.out.println("There is no expense matching your request !");
            Ui.printHorizontalLine();
        } else {
            Ui.printLines(Ui.getFormattedList(sortedExpenses));
        }
    }

    /**
     * Displays the expenses below a certain amount specified by the user.
     * Prints the amount entered by the user.
     * If no such expenses exist, displays the required message.
     *
     * @param expenseManager instance of expenseManager to execute the command
     * @param amount Stores the amount.
     * @throws DukeException to handle any exception encountered
     */
    private static void executeGetExpenseBelow(double amount, ExpenseManager expenseManager) throws DukeException {
        System.out.println(amount);
        ArrayList<Expense> sortedExpenses = new ArrayList<>();
        sortedExpenses = expenseManager.getExpensesBelow(amount);
        if (sortedExpenses.isEmpty()) {
            System.out.println("There is no expense matching your request !");
            Ui.printHorizontalLine();
        } else {
            Ui.printLines(Ui.getFormattedList(sortedExpenses));
        }
    }
}

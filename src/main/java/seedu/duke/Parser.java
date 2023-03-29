package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    public static void handleCmd(String userCmd, ExpenseManager expenseManager) throws DukeException {
        ArrayList<Expense> expenses = expenseManager.getExpenses();
        ArrayList<FutureExpense> futureExpenses = expenseManager.getFutureExpenses();
        try {
            if (userCmd.startsWith("add expense")) {
                Ui.printChoice();
                Scanner in = new Scanner(System.in);
                String choice = in.nextLine();
                int choiceNum = Integer.parseInt(choice);
                executeAddExpense(userCmd, expenseManager, choiceNum);
            } else if (userCmd.startsWith("edit expense")) {
                System.out.println(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
                executeEditExpense(expenseManager, userCmd, expenses);
            } else if (userCmd.startsWith("delete expense")) {
                executeDeleteExpense(expenseManager, userCmd);
            } else if (userCmd.startsWith("set balance")) {
                executeSetBudget(expenseManager, userCmd);
            } else if (userCmd.startsWith("get currency")) {
                Ui.printLines(String.format("Your currency is currently set to: %s", expenseManager.getCurrency()));
            } else if (userCmd.startsWith("list expenses")) {
                expenseManager.printExpense();
            } else if (userCmd.startsWith("check balance")) {
                Ui.printHorizontalLine();
                System.out.println(String.format("Your current balance is: %.2f %s",
                        expenseManager.getTotalBalance() * expenseManager.getRate(),
                        expenseManager.getCurrency()));
                Ui.printHorizontalLine();
            } else if (userCmd.startsWith("add future expense")) {
                Ui.printChoice();
                Scanner in = new Scanner(System.in);
                String choice = in.nextLine();
                int choiceNum = Integer.parseInt(choice);
                executeAddFutureExpense(userCmd, expenseManager, choiceNum);
            } else if (userCmd.startsWith("edit future expense")) {
                executeEditFutureExpense(expenseManager, userCmd, futureExpenses);
            } else if (userCmd.startsWith("set currency")) {
                String currency = userCmd.split(" ")[2];
                if (!CurrencyLoader.getCurrencyLoader().currencyExists(currency)) {
                    Ui.printLines(String.format(
                            "The currency %s is not valid. Please try again with a valid currency symbol", currency));
                } else {
                    expenseManager.setCurrency(currency);
                    Ui.printLines(String.format("Your currency has been successfully set to: %s", currency));
                }
            } else if (userCmd.startsWith("delete future expense")) {
                executeDeleteFutureExpense(expenseManager, userCmd);
            } else if (userCmd.startsWith("list future expenses")) {
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
            } else {
                Ui.printFalseInput();
            }
        } catch (DukeException e) {
            Ui.printHorizontalLine();
            System.out.println(e.getMessage());
            Ui.printHorizontalLine();
        }
    }

    public static void executeAddExpense(String userCmd, ExpenseManager expenseManager, int choice)
            throws DukeException {
        double amount = extractAmount(userCmd);
        LocalDate date = extractDate(userCmd);
        String name = extractName(userCmd);
        String category = getCategory(choice);
        if (category.equals("wrong input")) {
            Ui.printFalseInput();
        } else {
            expenseManager.addExpense(name, amount, date, category);
        }
    }

    private static String getCategory(int choice) {
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
                Ui.printFalseInput();
                return "wrong input";
        }
    }

    private static String extractName(String userCmd) {
        int endIndex = userCmd.indexOf("$/");
        return userCmd.substring(12, endIndex);
    }

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

    private static LocalDate extractDate(String input) throws DukeException {
        int startIndex = input.indexOf("d/");
        String dateString = input.substring(startIndex + 2);
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(4, 6));
        int day = Integer.parseInt(dateString.substring(6));
        return LocalDate.of(year, month, day);
    }

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

    public static void executeEditExpense(ExpenseManager expenseManager, String userCmd, ArrayList<Expense> expenses)
            throws DukeException {
        int id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
        if (id > expenseManager.getSize() || id < 0) {
            throw new DukeException("This expense id does not exist. Please provide a valid expense id.");
        } else {
            Scanner in = new Scanner(System.in);
            switch (userCmd.substring(userCmd.indexOf("in/") + 3)) {
                case "amount":
                    System.out.println("Enter a new amount spent! Just enter a number!");
                    Double newAmount = Double.parseDouble(in.nextLine());
                    Double newBalance = expenseManager.getTotalBalance() + expenseManager.get(id - 1).getAmount()
                            - newAmount;
                    expenseManager.get(id - 1).setAmount(newAmount);
                    expenseManager.setTotalBalance(newBalance);
                    System.out.println("Change in amount successful! Balance has also been recalculated");
                    break;
                case "date":
                    System.out.println("Enter a new date in the form of YYYYMMDD!");
                    String newDate = in.nextLine();
                    int year = Integer.parseInt(newDate.substring(0, 4));
                    int month = Integer.parseInt(newDate.substring(4, 6));
                    int day = Integer.parseInt(newDate.substring(6));
                    expenseManager.get(id - 1).setDate(LocalDate.of(year, month, day));
                    System.out.println("Change in date successful!");
                    break;
                case "category":
                    Ui.printChoice();
                    int choice = Integer.parseInt(in.nextLine());
                    String newCategory = getCategory(choice);
                    expenseManager.get(id - 1).setCategory(newCategory);
                    System.out.println("Change in category successful!");
                    break;
                default:
                    Ui.printFalseInput();
            }
        }
    }

    public static void executeDeleteExpense(ExpenseManager expenseManager, String userCmd) throws DukeException {
        int startIndex = userCmd.indexOf("id/");
        if (startIndex < 0) {
            throw new DukeException("Expense id cannot be empty.");
        } else {
            int id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3));
            if (id > expenseManager.getSize()) {
                throw new DukeException("This expense id does not exist. Please provide a valid expense id.");
            } else {
                Ui.printHorizontalLine();
                Expense deletedExpense = expenseManager.get(id - 1);
                expenseManager.remove(id - 1);
                System.out.println("Noted. I've removed this expense:");
                System.out.println(deletedExpense);
                Ui.printHorizontalLine();
            }
        }
    }

    public static void executeAddFutureExpense(String userCmd, ExpenseManager expenseManager, int choice)
            throws DukeException {
        double amount = extractAmount(userCmd);
        LocalDate dueDate = extractDate(userCmd);
        String name = userCmd.substring(19, userCmd.indexOf("$/") - 1);
        String category = getCategory(choice);
        if (category.equals("wrong input")) {
            Ui.printFalseInput();
        } else {
            expenseManager.addFutureExpense(name, amount, dueDate, category);
        }
    }

    public static void executeEditFutureExpense(ExpenseManager expenseManager, String userCmd,
            ArrayList<FutureExpense> futureExpenses) throws DukeException {
        int id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
        Scanner in = new Scanner(System.in);
        switch (userCmd.substring(userCmd.indexOf("in/") + 3)) {
            case "amount":
                System.out.println("Enter a new amount spent! Just enter a number!");
                Double newAmount = Double.parseDouble(in.nextLine());
                expenseManager.getFutureExpense(id - 1).setAmount(newAmount);
                System.out.println("Change in amount successful!");
                break;
            case "date":
                System.out.println("Enter a new date in the form of YYYYMMDD!");
                String newDate = in.nextLine();
                int year = Integer.parseInt(newDate.substring(0, 4));
                int month = Integer.parseInt(newDate.substring(4, 6));
                int day = Integer.parseInt(newDate.substring(6));
                expenseManager.getFutureExpense(id - 1).setDueDate(LocalDate.of(year, month, day));
                System.out.println("Change in date successful!");
                break;
            case "category":
                Ui.printChoice();
                int choice = Integer.parseInt(in.nextLine());
                String newCategory = getCategory(choice);
                expenseManager.getFutureExpense(id - 1).setCategory(newCategory);
                System.out.println("Change in category successful!");
                break;
            default:
                Ui.printFalseInput();
        }
    }

    public static void executeDeleteFutureExpense(ExpenseManager expenseManager, String userCmd) throws DukeException {
        int startIndex = userCmd.indexOf("id/");
        if (startIndex < 0) {
            throw new DukeException("Expense id cannot be empty.");
        } else {
            int id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3));
            if (id > expenseManager.getFutureSize()) {
                throw new DukeException("This expense id does not exist. Please provide a valid expense id.");
            } else {
                Ui.printHorizontalLine();
                Expense deletedExpense = expenseManager.getFutureExpense(id - 1);
                expenseManager.removeFutureExpense(id - 1);
                System.out.println("Noted. I've removed this expense:");
                System.out.println(deletedExpense);
                Ui.printHorizontalLine();
            }
        }
    }

    public static void executeCheckUpcomingExpenses(ExpenseManager expenseManager) {
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
            System.out.println("Please input a valid number!");
        }
    }

    public static void executePayFutureExpense(ExpenseManager expenseManager, String userCmd) throws DukeException {
        if (userCmd.split(" ").length == 2) {
            String idString = userCmd.split(" ")[1].trim();
            if (idString == null) {
                throw new DukeException("Please input a valid number!");
            }
            try {
                int id = Integer.parseInt(idString);
                expenseManager.payFutureExpense(id - 1);
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid number!");
            }
        } else {
            System.out.println("Please input a valid number!");
        }
    }

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

package seedu.duke;

import java.time.LocalDate;
import java.util.Scanner;

public class Parser {
    public static void handleCmd(String userCmd, ExpenseManager expenseManager) {
        if (userCmd.startsWith("add expense")) {
            Ui.printChoice();
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            int choiceNum = Integer.parseInt(choice);
            executeAddExpense(userCmd, expenseManager, choiceNum);
        } else if (userCmd.startsWith("edit expense")) {
            System.out.println(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
            executeEditExpense(expenseManager, userCmd);
        } else if(userCmd.startsWith("delete expense")) {
            //System.out.println(userCmd.substring(userCmd.indexOf("id/") + 3));
            executeDeleteExpense(expenseManager, userCmd);
        } else if (userCmd.startsWith("set balance")) {
            executeSetBudget(expenseManager, userCmd);
        } else if (userCmd.startsWith("list expenses")) {
            expenseManager.printExpense();
        } else if (userCmd.startsWith("check balance")) {
            Ui.printHorizontalLine();
            System.out.println("Your current balance is: $" + expenseManager.getTotalBalance());
            Ui.printHorizontalLine();
        } else {
            Ui.printfalseInput();
        }
    }

    private static void executeAddExpense(String userCmd, ExpenseManager expenseManager, int choice) {
        double amount = extractAmount(userCmd);
        LocalDate date = extractDate(userCmd);
        String category = getCategory(choice);
        if (category.equals("wrong input")) {
            Ui.printfalseInput();
        } else {
            expenseManager.addExpense(amount, date, category);
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
                Ui.printfalseInput();
                return "wrong input";
        }
    }

    private static double extractAmount(String userCmd) {
        int startIndex = userCmd.indexOf("$/") + 2;
        int endIndex = userCmd.indexOf(" ", startIndex);
        String amountString = userCmd.substring(startIndex, endIndex);
        return Double.parseDouble(amountString);
    }

    private static LocalDate extractDate(String input) {
        int startIndex = input.indexOf("d/") + 2;
        String dateString = input.substring(startIndex);
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(4, 6));
        int day = Integer.parseInt(dateString.substring(6));
        return LocalDate.of(year, month, day);
    }

    private static void executeSetBudget(ExpenseManager expenseManager, String userCmd) {
        int startIndex = userCmd.indexOf("$/");
        double balance = Double.parseDouble(userCmd.substring(startIndex + 2));
        Ui.printHorizontalLine();
        System.out.println("Your budget has been set to $" + balance);
        Ui.printHorizontalLine();
        expenseManager.setTotalBalance(balance);
    }

    private static void executeEditExpense(ExpenseManager expenseManager, String userCmd) {
        int id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3, userCmd.indexOf("in/") - 1));
        Scanner in = new Scanner(System.in);
        switch (userCmd.substring(userCmd.indexOf("in/") + 3)) {
            case "amount":
                System.out.println("Enter a new amount spent! Just enter a number!");
                Double newAmount = Double.parseDouble(in.nextLine());
                Double newBalance = expenseManager.getTotalBalance() + expenseManager.get(id - 1).getAmount() - newAmount;
                expenseManager.get(id - 1).setAmount(newAmount);
                expenseManager.setTotalBalance(newBalance);
                System.out.println("Change in amount successful ! Balance has also been recalculated");
                break;
            case "date":
                System.out.println("Enter a new date in the form of YYYYMMDD!");
                String newDate = in.nextLine();
                int year = Integer.parseInt(newDate.substring(0, 4));
                int month = Integer.parseInt(newDate.substring(4, 6));
                int day = Integer.parseInt(newDate.substring(6));
                expenseManager.get(id - 1).setDate(LocalDate.of(year, month, day));
                System.out.println("Change in date successful !");
                break;
            case "category":
                Ui.printChoice();
                int choice = Integer.parseInt(in.nextLine());
                String newCategory = getCategory(choice);
                expenseManager.get(id - 1).setCategory(newCategory);
                System.out.println("Change in category successful !");
                break;
            default:
                Ui.printfalseInput();
        }
    }
    private static void executeDeleteExpense(ExpenseManager expenseManager, String userCmd) {
        int id = Integer.parseInt(userCmd.substring(userCmd.indexOf("id/") + 3));
        Ui.printHorizontalLine();
        Expense deletedExpense = expenseManager.get(id-1);
        expenseManager.remove(id-1);
        System.out.println("Noted. I've removed this expense:");
        System.out.println(deletedExpense);
        Ui.printHorizontalLine();
    }
}

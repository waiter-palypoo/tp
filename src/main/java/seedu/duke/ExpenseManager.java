package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseManager {

    public static final int SORT_BY_NAME = 0;
    public static final int SORT_BY_PRICE = 1;

    private ArrayList<Expense> expenses;
    private double totalBalance;

    public ExpenseManager(double totalBalance) {
        this.totalBalance = totalBalance;
        this.expenses = new ArrayList<Expense>();
    }

    public void addExpense(String name, double amount, LocalDate date, String category) {
        Expense toAdd = new Expense(name.strip(), amount, date, category.strip());
        Ui.printHorizontalLine();
        System.out.println("Roger, the following expense has been added!");
        System.out.println(toAdd);
        Ui.printHorizontalLine();
        expenses.add(toAdd);
        totalBalance -= amount;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    private ArrayList<Expense> getSortedExpensesByAmount() {
        ArrayList<Expense> sortedExpenses = new ArrayList<>(this.expenses);
        sortedExpenses.sort((e1, e2) -> Double.compare(e1.getAmount(), e2.getAmount()));
        return sortedExpenses;
    }

    private ArrayList<Expense> getSortedExpensesByName() {
        ArrayList<Expense> sortedExpenses = new ArrayList<>(this.expenses);
        sortedExpenses.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        return sortedExpenses;
    }

    public ArrayList<Expense> getSortedExpenses(final int sortBy) {
        switch (sortBy) {
        case SORT_BY_NAME:
            return getSortedExpensesByName();
        case SORT_BY_PRICE:
            return getSortedExpensesByAmount();
        default:
            return getSortedExpensesByName();
        }
    }

    public void printExpense() throws DukeException {
        if (expenses.size() == 0) {
            throw new DukeException("Sorry, there are no expenses in the list currently.");
        }
        Ui.printHorizontalLine();
        Scanner sc = new Scanner(System.in);
        System.out.println("How would you like your expenses to be sorted?");
        System.out.println("  1. By date added");
        System.out.println("  2. By Name");
        System.out.println("  3. By Amount");
        Ui.printHorizontalLine();
        int sortBy = sc.nextInt();
        ArrayList<Expense> toList;
        switch (sortBy) {
        case 1:
            toList = this.expenses;
            break;
        case 2:
            toList = getSortedExpenses(SORT_BY_NAME);
            break;
        case 3:
            toList = getSortedExpenses(SORT_BY_PRICE);
            break;
        default:
            toList = this.expenses;
        }
        int count = 1;
        Ui.printHorizontalLine();
        System.out.println("Here are the list of your expenses !");
        for (Expense e : toList) {
            System.out.println(count + ". " + e);
            count++;
        }
        Ui.printHorizontalLine();
    }

    public Expense get(int id) {
        return expenses.get(id);
    }

    public Expense remove(int id) {
        return expenses.remove(id);
    }

    public int getSize() {
        return expenses.size();
    }
}

package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseManager extends Expense {
    private ArrayList<Expense> expenses;
    private double totalBalance;

    public ExpenseManager(double totalBalance) {
        super(0, LocalDate.now(), "None");
        this.totalBalance = totalBalance;
        this.expenses = new ArrayList<Expense>();
    }

    public void addExpense(double amount, LocalDate date, String category) {
        System.out.println("Roger, the following expense has been added! $" + new Expense(amount, date, category).toString());
        expenses.add(new Expense(amount, date, category));
        totalBalance -= amount;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void printExpense() {
        int count = 1;
        System.out.println("Here are the list of your expenses !");
        for(Expense e : expenses) {
            System.out.println(count + ". $" + e.toString());
        }
    }

    public Expense get(int id) {
        return expenses.get(id);
    }
}

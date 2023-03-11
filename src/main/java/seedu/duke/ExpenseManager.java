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
        Ui.printHorizontalLine();
        System.out.println("Roger, the following expense has been added!");
        System.out.println("$" + new Expense(amount, date, category).toString());
        Ui.printHorizontalLine();
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
        Ui.printHorizontalLine();
        System.out.println("Here are the list of your expenses !");
        for(Expense e : expenses) {
            System.out.println(count + ". $" + e.toString());
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
}


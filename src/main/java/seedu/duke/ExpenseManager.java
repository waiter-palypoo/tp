package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseManager {

    public static final int SORT_BY_NAME = 0;
    public static final int SORT_BY_PRICE = 1;

    private ArrayList<Expense> expenses;
    private ArrayList<FutureExpense> futureExpenses;
    private double totalBalance;

    public ExpenseManager(double totalBalance, ArrayList<Expense> expenses, ArrayList<FutureExpense> futureExpenses) {
        this.totalBalance = totalBalance;
        this.expenses = expenses;
        this.futureExpenses = futureExpenses;
    }

    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }

    public ArrayList<FutureExpense> getFutureExpenses() {
        return this.futureExpenses;
    }

    public void addExpense(String name, double amount, LocalDate date, String category) {
        Expense toAdd = new Expense(name.strip(), amount, date, category.strip());
        Ui.printLines("Roger, the following expense has been added!", toAdd.toString());
        expenses.add(toAdd);
        totalBalance -= amount;
    }

    public void addFutureExpense(String name, double amount, LocalDate dueDate, String category) {
        FutureExpense toAdd = new FutureExpense(name.strip(), amount, dueDate, category);
        Ui.printLines("Roger, the following expense has been added!", toAdd.toString());
        futureExpenses.add(toAdd);
    }

    public FutureExpense getFutureExpense(int id) {
        return futureExpenses.get(id);
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

    public ArrayList<Expense> getExpensesAbove(double amount) { // return an ArrayList of expenses above a threshold
        ArrayList<Expense> sortedExpenses = new ArrayList<>();
        for (Expense expense : this.expenses) {
            if (expense.getAmount() >= amount) {
                sortedExpenses.add(expense);
            }
        }
        return sortedExpenses;
    }

    public ArrayList<Expense> getExpensesBelow(double amount) { // return an ArrayList of expenses below a threshold
        ArrayList<Expense> sortedExpenses = new ArrayList<>();
        for (Expense expense : this.expenses) {
            if (expense.getAmount() <= amount) {
                sortedExpenses.add(expense);
            }
        }
        return sortedExpenses;
    }

    private ArrayList<Expense> getSortedExpensesByName() {
        ArrayList<Expense> sortedExpenses = new ArrayList<>(this.expenses);
        sortedExpenses.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        return sortedExpenses;
    }

    public ArrayList<Expense> getSortedExpenses(final int sortBy) {
        assert sortBy == SORT_BY_NAME || sortBy == SORT_BY_PRICE : "Expenses can either be sort by named or price";
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
        Ui.printLines("How would you like your expenses to be sorted?", "  1. By date added", "  2. By Name",
                "  3. By Amount");
        Scanner sc = new Scanner(System.in);
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
        Ui.printLines(Ui.getFormattedList(toList));
    }

    private String checkSufficientBalance(FutureExpense futureExpense) {
        if (futureExpense.getAmount() <= totalBalance) {
            return "";
        }
        return "Warning: Insufficient Balance!";
    }

    public void printFutureExpenses() throws DukeException {
        if (futureExpenses.size() == 0) {
            throw new DukeException("Sorry, there are no future expenses.");
        }
        Ui.printHorizontalLine();
        System.out.println("Here is the list of your future expenses: ");
        int count = 1;
        int totalAmountDue = 0;
        for (FutureExpense futureExpense : futureExpenses) {
            System.out.println(count + ". " + futureExpense + " " + checkSufficientBalance(futureExpense));
            count++;
            totalAmountDue += futureExpense.getAmount();
        }
        Ui.printHorizontalLine();
        System.out.println("You have " + futureExpenses.size() + " future expenses in total.");
        System.out.println("Total amount due: " + totalAmountDue);
        System.out.println("Total balance: " + getTotalBalance());
        Ui.printHorizontalLine();
        if (totalAmountDue > totalBalance) {
            System.out.println("Warning: You have insufficient balance to pay for all future expenses!");
        } else {
            System.out.println("Great! You have sufficient balance to pay for all future expenses!");
        }
        Ui.printHorizontalLine();
    }

    public void checkUpcomingExpenses(int timePeriod) {
        Ui.printHorizontalLine();
        LocalDate today = LocalDate.now();
        LocalDate endDate = null;
        switch (timePeriod) {
            case 1:
                endDate = today.plusWeeks(1);
                break;
            case 2:
                endDate = today.plusMonths(1);
                break;
            case 3:
                endDate = today.plusMonths(3);
                break;
            default:
                endDate = today;
        }
        int count = 0;
        int totalAmountDue = 0;
        for (FutureExpense futureExpense : futureExpenses) {
            if (futureExpense.getDueDate().isBefore(endDate)) { // change
                System.out.println((futureExpenses.indexOf(futureExpense) + 1) + ". " +
                        checkSufficientBalance(futureExpense));
                count++;
                totalAmountDue += futureExpense.getAmount();
            }
        }
        Ui.printHorizontalLine();
        System.out.println("There are " + count + " upcoming payments in this time period.");
        System.out.println("Total amount due: " + totalAmountDue);
        System.out.println("Total balance: " + getTotalBalance());
        Ui.printHorizontalLine();
        if (totalAmountDue > totalBalance) {
            System.out.println("Warning: You have insufficient balance to pay for all upcoming expenses!");
        } else {
            System.out.println("Great! You have sufficient balance to pay for all upcoming expenses!");
        }
        Ui.printHorizontalLine();
    }

    public void payFutureExpense(int id) throws DukeException {
        Ui.printHorizontalLine();
        if (id >= futureExpenses.size()) {
            throw new DukeException("That item does not exist in the list of future expenses!");
        }
        System.out.println("This future expense has been paid for:");
        System.out.println(futureExpenses.get(id).toString());
        System.out.println("You may now find it in your expenses list.");
        String name = futureExpenses.get(id).getName();
        double amt = futureExpenses.get(id).getAmount();
        String category = futureExpenses.get(id).getCategory();
        addExpense(name, amt, LocalDate.now(), category);
        futureExpenses.remove(id);
        Ui.printHorizontalLine();
    }

    public Expense get(int id) {
        return expenses.get(id);
    }

    public void remove(int id) {
        expenses.remove(id);
    }

    public void removeFutureExpense(int id) {
        futureExpenses.remove(id);
    }

    public int getSize() {
        return expenses.size();
    }

    public int getFutureSize() {
        return futureExpenses.size();
    }
}

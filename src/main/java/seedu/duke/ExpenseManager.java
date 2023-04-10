package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ExpenseManager {

    public static final int SORT_BY_NAME = 0;
    public static final int SORT_BY_PRICE_ASC = 1;
    public static final int SORT_BY_PRICE_DESC = 2;

    private ArrayList<Expense> expenses;
    private ArrayList<FutureExpense> futureExpenses;
    private Map<String, Double> expenseByCategory;
    private double totalBalance;
    private String currency;
    private CurrencyLoader currencyLoader = CurrencyLoader.getCurrencyLoader();

    public ExpenseManager(double totalBalance, ArrayList<Expense> expenses, ArrayList<FutureExpense> futureExpenses,
                          String currency) {
        this.currency = currency;
        this.totalBalance = totalBalance;
        this.expenses = expenses;
        this.futureExpenses = futureExpenses;
        this.expenseByCategory = new HashMap<>();
    }

    public ExpenseManager(double totalBalance, ArrayList<Expense> expenses, ArrayList<FutureExpense> futureExpenses,
                          Map<String, Double> expenseByCategory, String currency) {
        this.currency = currency;
        this.totalBalance = totalBalance;
        this.expenses = expenses;
        this.futureExpenses = futureExpenses;
        this.expenseByCategory = expenseByCategory;
    }

    public ExpenseManager(double totalBalance, ArrayList<Expense> expenses, ArrayList<FutureExpense> futureExpenses,
                          Map<String, Double> expenseByCategory) {
        this.currency = "SGD";
        this.totalBalance = totalBalance;
        this.expenses = expenses;
        this.futureExpenses = futureExpenses;
        this.expenseByCategory = expenseByCategory;
    }

    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }

    public ArrayList<FutureExpense> getFutureExpenses() {
        return this.futureExpenses;
    }

    public String getCurrency() {
        return this.currency;
    }

    /**
     * Sets the currency of all the expenses to the currency chosen by the user.
     *
     * @param currency Stores the currency chosen by the user
     */
    public void setCurrency(String currency) {
        this.currency = currency;
        for (Expense expense : expenses) {
            expense.setCurrency(currency);
        }
        for (FutureExpense expense : futureExpenses) {
            expense.setCurrency(currency);
        }
    }

    /**
     * Adds the expense to the list of expenses.
     * Updates the total balance based on amount of expenses.
     * Displays a success message to the user with the expense details.
     *
     * @param name Stores the expense name
     * @param amount Stores the expense amount
     * @param date Stores the expense date
     * @param category Stores the expense category
     */
    public void addExpense(String name, double amount, LocalDate date, String category) {
        double normalizedAmount = amount / currencyLoader.getRate(this.currency);
        Expense toAdd = new Expense(name.strip(), normalizedAmount, date, category.strip(), this.currency);
        Ui.printLines("Roger, the following expense has been added!", toAdd.toString());
        expenses.add(toAdd);
        totalBalance -= normalizedAmount;
        expenseByCategory.put(category.strip(),
                              expenseByCategory.getOrDefault(category.strip(), 0.0) + normalizedAmount);
    }

    /**
     * Adds the future expense to the list of future expenses.
     * Displays a success message to the user with the expense details.
     *
     * @param name Stores the expense name
     * @param amount Stores the expense amount
     * @param dueDate Stores the due date of expense
     * @param category Stores the expense category
     */
    public void addFutureExpense(String name, double amount, LocalDate dueDate, String category) {
        double normalizedAmount = amount / currencyLoader.getRate(this.currency);
        FutureExpense toAdd = new FutureExpense(name.strip(), normalizedAmount, dueDate, category, this.currency);
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

    public double getRate() {
        return this.currencyLoader.getRate(currency);
    }

    /**
     * Sorts the expense list by the expense amount in either ascending or
     * descending order.
     *
     * @param isDesc Stores true if expenses are to be sorted in descending
     * order and false if expenses are to be sorted in ascending order
     * @returns list of expenses sorted by name
     */
    private ArrayList<Expense> getSortedExpensesByAmount(boolean isDesc) {
        ArrayList<Expense> sortedExpenses = new ArrayList<>(this.expenses);
        if (isDesc) {
            sortedExpenses.sort((e1, e2) -> Double.compare(e2.getAmount(), e1.getAmount()));
        } else {
            sortedExpenses.sort((e1, e2) -> Double.compare(e1.getAmount(), e2.getAmount()));
        }
        return sortedExpenses;
    }

    /**
     * Stores the expenses above a threshold to be displayed in an arraylist.
     *
     * @param amount Stores the amount
     * @returns list of expenses above a threshold
     */
    public ArrayList<Expense> getExpensesAbove(double amount) {
        ArrayList<Expense> sortedExpenses = new ArrayList<>();
        double convertedAmount = amount / this.getRate();
        for (Expense expense : this.expenses) {
            if (expense.getAmount() >= convertedAmount) {
                sortedExpenses.add(expense);
            }
        }
        return sortedExpenses;
    }

    /**
     * Stores the expenses below a threshold to be displayed in an arraylist.
     *
     * @param amount Stores the amount
     * @returns list of expenses below a threshold
     */
    public ArrayList<Expense> getExpensesBelow(double amount) {
        ArrayList<Expense> sortedExpenses = new ArrayList<>();
        double convertedAmount = amount / this.getRate();
        for (Expense expense : this.expenses) {
            if (expense.getAmount() <= convertedAmount) {
                sortedExpenses.add(expense);
            }
        }
        return sortedExpenses;
    }

    /**
     * Sorts the expense list by the expense name.
     *
     * @returns list of expenses sorted by name
     */
    private ArrayList<Expense> getSortedExpensesByName() {
        ArrayList<Expense> sortedExpenses = new ArrayList<>(this.expenses);
        sortedExpenses.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        return sortedExpenses;
    }

    /**
     * Sorts the expenses by name or amount in ascending or descending order.
     *
     * @param sortBy Stores the sorting choice entered by the user
     * @returns list of expenses above a threshold
     */
    public ArrayList<Expense> getSortedExpenses(final int sortBy) {
        assert sortBy == SORT_BY_NAME || sortBy == SORT_BY_PRICE_ASC ||
                sortBy == SORT_BY_PRICE_DESC : "Expenses can either be sort by name or price in asc/desc order";
        switch (sortBy) {
            case SORT_BY_NAME:
                return getSortedExpensesByName();
            case SORT_BY_PRICE_ASC:
                return getSortedExpensesByAmount(false);
            case SORT_BY_PRICE_DESC:
                return getSortedExpensesByAmount(true);
            default:
                return this.expenses;
        }
    }

    /**
     * Prints a list of all <code>Expense</code>s in <code>expenses</code>, sorted by date added, name,
     * or amount paid, according to the user's choice.
     *
     * @throws DukeException if the <code>expenses</code> list is empty.
     */
    public void printExpense() throws DukeException {
        if (expenses.size() == 0) {
            throw new DukeException("Sorry, there are no expenses in the list currently.");
        }
        Ui.printLines("How would you like your expenses to be sorted?", "  1. By date added / Expense ID",
                "  2. By Name", "  3. By Amount in Ascending Order", "  4. By Amount in Descending Order");
        Scanner sc = new Scanner(System.in);
        try {
            int sortBy = sc.nextInt();
            ArrayList<Expense> toList;
            switch (sortBy) {
                case 1:
                    Ui.printLines(Ui.getFormattedList(this.expenses));
                    break;
                case 2:
                    toList = getSortedExpenses(SORT_BY_NAME);
                    Ui.printLines(Ui.getFormattedList(toList));
                    break;
                case 3:
                    toList = getSortedExpenses(SORT_BY_PRICE_ASC);
                    Ui.printLines(Ui.getFormattedList(toList));
                    break;
                case 4:
                    toList = getSortedExpenses(SORT_BY_PRICE_DESC);
                    Ui.printLines(Ui.getFormattedList(toList));
                    break;
                default:
                    Ui.printLines("Invalid choice");
            }
        } catch (NumberFormatException | InputMismatchException exception) {
            throw new DukeException("Invalid input");
        }
    }

    private String checkSufficientBalance(FutureExpense futureExpense) {
        if (futureExpense.getAmount() <= totalBalance) {
            return "";
        }
        return "Warning: Insufficient Balance!";
    }

    /**
     * Prints a list of all <code>FutureExpense</code>s in <code>futureExpenses</code>, then informs the user if they
     * have sufficient balance for each future expense, then for all of them in total.
     *
     * @throws DukeException if <code>futureExpenses</code> is empty.
     */
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
        System.out.println(String.format("Total amount due: %.2f %s", totalAmountDue * this.getRate(), this.currency));
        System.out.println(String.format("Total balance: %.2f %s", getTotalBalance() * this.getRate(), this.currency));
        Ui.printHorizontalLine();
        if (totalAmountDue > totalBalance) {
            System.out.println("Warning: You have insufficient balance to pay for all future expenses!");
        } else {
            System.out.println("Great! You have sufficient balance to pay for all future expenses!");
        }
        Ui.printHorizontalLine();
    }

    /**
     * Prints a list of <code>FutureExpense</code>s that are due within the time period specified by the user and
     * informs the user if they have sufficient balance for each future expense, then for all of them in total.
     *
     * @param timePeriod Setting for the time period. 1 indicates 1 week, 2 indicates 1 month, 3 indicates 3 months.
     */
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
            if (futureExpense.getDueDate().isBefore(endDate)) {
                System.out.println((futureExpenses.indexOf(futureExpense) + 1) + ". " + futureExpense + " " +
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

    /**
     * Checks if any of the items in the <code>futureExpenses</code> list are overdue, and if so, prints a warning
     * to the user.
     */
    public void startupDueDateCheck() {
        LocalDate today = LocalDate.now();
        int count = 0;
        int totalAmountDue = 0;
        for (FutureExpense futureExpense : futureExpenses) {
            if (futureExpense.getDueDate().isBefore(today)) {
                System.out.println((futureExpenses.indexOf(futureExpense) + 1) + ". " + futureExpense + " " +
                        checkSufficientBalance(futureExpense));
                count++;
                totalAmountDue += futureExpense.getAmount();
            }
        }
        if (count > 0) {
            Ui.printHorizontalLine();
            System.out.println("Warning! The above " + count + " future expense(s) are overdue!");
            System.out.println("Total amount due: " + totalAmountDue);
            System.out.println("Total balance: " + getTotalBalance());
            Ui.printHorizontalLine();
            if (totalAmountDue > totalBalance) {
                System.out.println("Warning: You have insufficient balance to pay for all overdue expenses!");
            } else {
                System.out.println("You have sufficient balance to pay for all overdue expenses!");
            }
            System.out.println("Use the pay command to pay for a future expense. Use the help command for a list of "
                    + "commands");
            Ui.printHorizontalLine();
        }
    }

    /**
     * Converts a <code>FutureExpense</code> in the <code>futureExpenses</code> list to an <code>Expense</code>, thereby
     * setting it as paid.
     *
     * @param id The index of the <code>FutureExpense</code> in <code>futureExpenses</code>.
     * @throws DukeException if the index given is greater than the size of <code>futureExpenses</code>.
     */
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

    /**
     * Prints the total expenditure in different categories based on the expenses
     * entered by the user. If no expense is made in a particular category, the
     * amount shown is 0 by default.
     */
    public void printExpenditureByCategory() {
        Ui.printHorizontalLine();
        for (String i : expenseByCategory.keySet()) {
            System.out.println(i + " - "
                    + "$" + expenseByCategory.get(i));
        }
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

    public int getExpensesSize() {
        return expenses.size();
    }

    public int getFutureSize() {
        return futureExpenses.size();
    }

    public void removeAllExpenses() {
        expenses.clear();
    }

    public void removeAllFutureExpenses() {
        futureExpenses.clear();
    }
}

package seedu.duke;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    public void addExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>() {
            {
                put("Food & Drinks", 0.0);
                put("Shopping", 0.0);
                put("Transportation", 0.0);
                put("Life & Entertainment", 0.0);
                put("Investments", 0.0);
                put("Communication & Technology", 0.0);
                put("Others", 0.0);
            }
        };
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAdd = new Expense("Movie", 200, date, "Life & Entertainment");
        testExpenses.add(toAdd);
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        String expectedOutput = testExpenses.get(0).toString();
        String actualOutput = expenseManager.get(0).toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void deleteExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>() {
            {
                put("Food & Drinks", 0.0);
                put("Shopping", 0.0);
                put("Transportation", 0.0);
                put("Life & Entertainment", 0.0);
                put("Investments", 0.0);
                put("Communication & Technology", 0.0);
                put("Others", 0.0);
            }
        };
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAdd = new Expense("Movie", 200, date, "Life & Entertainment");
        testExpenses.add(toAdd);
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        testExpenses.remove(0);
        Parser.executeDeleteExpense(expenseManager, "delete expense id/1");
        assertEquals(testExpenses.size(), expenseManager.getExpensesSize());
    }

    @Test
    public void editExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>() {
            {
                put("Food & Drinks", 0.0);
                put("Shopping", 0.0);
                put("Transportation", 0.0);
                put("Life & Entertainment", 0.0);
                put("Investments", 0.0);
                put("Communication & Technology", 0.0);
                put("Others", 0.0);
            }
        };
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAdd = new Expense("Movie", 200, date, "Life & Entertainment");
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("50".getBytes());
        System.setIn(in);
        Parser.executeEditExpense(expenseManager, "edit expense Movie id/1 in/amount", testExpenses);
        System.setIn(sysInBackup);
        toAdd.setAmount(50);
        testExpenses.add(toAdd);
        String expectedOutput = testExpenses.get(0).toString();
        String actualOutput = expenseManager.get(0).toString();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void listExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>() {
            {
                put("Food & Drinks", 0.0);
                put("Shopping", 0.0);
                put("Transportation", 0.0);
                put("Life & Entertainment", 0.0);
                put("Investments", 0.0);
                put("Communication & Technology", 0.0);
                put("Others", 0.0);
            }
        };
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAddFirst = new Expense("Movie", 200, date, "Life & Entertainment");
        Expense toAddSecond = new Expense("Chicken", 10, date, "Food & Drinks");
        testExpenses.add(toAddFirst);
        testExpenses.add(toAddSecond);
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        Parser.executeAddExpense("add expense Chicken $/10 d/20220101", expenseManager, 1);
        assertEquals(testExpenses.size(), expenseManager.getExpensesSize());
    }

    @Test
    public void listExpenseByCategory_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>() {
            {
                put("Food & Drinks", 0.0);
                put("Shopping", 0.0);
                put("Transportation", 0.0);
                put("Life & Entertainment", 0.0);
                put("Investments", 0.0);
                put("Communication & Technology", 0.0);
                put("Others", 0.0);
            }
        };
        LocalDate date = LocalDate.of(2022, 01, 01);
        double amount = 200;
        String category = "Life & Entertainment";
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        assertEquals(expenseByCategory.get(category.strip()), amount);
    }

    @Test
    public void setBalance_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>();
        ExpenseManager expenseManager = new ExpenseManager(5000, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeSetBudget(expenseManager, "set balance $/5000");
        assertEquals(5000, expenseManager.getTotalBalance());
    }

    @Test
    public void addFutureExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>();
        LocalDate date = LocalDate.of(2022, 01, 01);
        FutureExpense toAdd = new FutureExpense("Movie", 200, date, "Life & Entertainment");
        testFutureExpenses.add(toAdd);
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddFutureExpense("add future expense Movie $/200 d/20220101", expenseManager, 4);
        String expectedOutput = testFutureExpenses.get(0).toString();
        String actualOutput = expenseManager.getFutureExpense(0).toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void deleteFutureExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>();
        LocalDate date = LocalDate.of(2024, 01, 01);
        FutureExpense toAdd = new FutureExpense("Movie", 200, date, "Life & Entertainment");
        testFutureExpenses.add(toAdd);
        ExpenseManager expenseManager =
                new ExpenseManager(0, testExpenses, new ArrayList<>(testFutureExpenses), expenseByCategory);
        Parser.executeAddFutureExpense("add future expense Movie $/200 d/20240101", expenseManager, 4);
        Parser.executeDeleteFutureExpense(expenseManager, "delete future expense id/1");
        assertEquals(testFutureExpenses.size(), expenseManager.getFutureSize());
    }

    @Test
    public void editFutureExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        ArrayList<FutureExpense> testFutureExpenses = new ArrayList<FutureExpense>();
        Map<String, Double> expenseByCategory = new HashMap<String, Double>();
        LocalDate date = LocalDate.of(2024, 01, 01);
        FutureExpense toAdd = new FutureExpense("Movie", 200, date, "Life & Entertainment");
        ExpenseManager expenseManager = new ExpenseManager(0, testExpenses, testFutureExpenses, expenseByCategory);
        Parser.executeAddFutureExpense("add future expense Movie $/200 d/20240101", expenseManager, 4);
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("50".getBytes());
        System.setIn(in);
        Parser.executeEditFutureExpense(expenseManager, "edit future expense Movie id/1 in/amount", testFutureExpenses);
        System.setIn(sysInBackup);
        toAdd.setAmount(50);
        testFutureExpenses.add(toAdd);
        String expectedOutput = testFutureExpenses.get(0).toString();
        String actualOutput = expenseManager.getFutureExpense(0).toString();
        assertEquals(expectedOutput, actualOutput);
    }
}

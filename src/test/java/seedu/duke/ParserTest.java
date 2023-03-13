package seedu.duke;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void addExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAdd = new Expense("Movie", 200, date, "Life & Entertainment");
        testExpenses.add(toAdd);
        ExpenseManager expenseManager = new ExpenseManager(0);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        String expectedOutput = testExpenses.get(0).toString();
        String actualOutput = expenseManager.get(0).toString();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void deleteExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAdd = new Expense("Movie", 200, date, "Life & Entertainment");
        testExpenses.add(toAdd);
        ExpenseManager expenseManager = new ExpenseManager(0);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        testExpenses.remove(0);
        Parser.executeDeleteExpense(expenseManager, "delete expense id/1");
        assertEquals(testExpenses.size(), expenseManager.getSize());
    }

    @Test
    public void editExpense_successful() throws DukeException {
        ArrayList<Expense> testExpenses = new ArrayList<Expense>();
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAdd = new Expense("Movie", 200, date, "Life & Entertainment");
        ExpenseManager expenseManager = new ExpenseManager(0);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("50".getBytes());
        System.setIn(in);
        Parser.executeEditExpense(expenseManager, "edit expense Movie id/1 in/amount");
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
        LocalDate date = LocalDate.of(2022, 01, 01);
        Expense toAddFirst = new Expense("Movie", 200, date, "Life & Entertainment");
        Expense toAddSecond = new Expense("Chicken", 10, date, "Food & Drinks");
        testExpenses.add(toAddFirst);
        testExpenses.add(toAddSecond);
        ExpenseManager expenseManager = new ExpenseManager(0);
        Parser.executeAddExpense("add expense Movie $/200 d/20220101", expenseManager, 4);
        Parser.executeAddExpense("add expense Chicken $/10 d/20220101", expenseManager, 1);
        assertEquals(testExpenses.size(), expenseManager.getSize());
    }

    @Test
    public void setBalance_successful() throws DukeException {
        ExpenseManager expenseManager = new ExpenseManager(5000);
        Parser.executeSetBudget(expenseManager, "set balance $/5000");
        assertEquals(5000, expenseManager.getTotalBalance());
    }
}
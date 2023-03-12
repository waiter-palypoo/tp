package seedu.duke;

import org.junit.jupiter.api.Test;

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
        assertEquals(expectedOutput, actualOutput );
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
}
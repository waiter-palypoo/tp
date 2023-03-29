package seedu.duke;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CurrencyTest {
    @Test
    public void setCurrencyToUSD_successful() {
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<FutureExpense> futureExpenses = new ArrayList<>();
        LocalDate date = LocalDate.of(2022, 01, 01);
        expenses.add(new Expense("foo", 100, date, "Shopping"));
        CurrencyLoader loader = CurrencyLoader.getCurrencyLoader();
        ExpenseManager manager = new ExpenseManager(0.0, expenses, futureExpenses, "USD");
        assertEquals(manager.getRate(), loader.getRate("USD"));
    }
}

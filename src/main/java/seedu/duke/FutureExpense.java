package seedu.duke;

import java.time.LocalDate;

public class FutureExpense extends Expense {
    private LocalDate dueDate;

    public FutureExpense(String name, double amount, LocalDate dueDate, String category, String currency) {
        super(name, amount, LocalDate.now(), category, currency);
        this.dueDate = dueDate;
    }

    public FutureExpense(String name, double amount, LocalDate dueDate, String category) {
        super(name, amount, LocalDate.now(), category, "SGD");
        this.dueDate = dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String serialize() {
        return String.format("%s|%s", super.serialize(), this.dueDate);
    }

    @Override
    public String toString() {
        return String.format("Upcoming payment %s in the %s category due on %s.", this.getName(), this.getCategory(),
                this.dueDate);
    }
}

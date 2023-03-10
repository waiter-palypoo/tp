package seedu.duke;

import java.time.LocalDate;

public class Expense {
    private double amount;
    private LocalDate date;
    private String category;

    public Expense(double amount, LocalDate date, String category) {
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String toString() {
        return this.amount + " spent on: " + this.category + " on the date of: " + this.date;
    }
}

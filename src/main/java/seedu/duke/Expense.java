package seedu.duke;

import java.time.LocalDate;

public class Expense {
    private double amount;
    private LocalDate date;
    private String category;
    private String name;

    public Expense(String name, double amount, LocalDate date, String category) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return this.name;
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
        return String.format("Spent $%s on %s in the %s category on %s", this.amount, this.name, this.category,
                             this.date);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Expense) {
            Expense o = (Expense)obj;
            return o.name.equals(this.name) && o.amount == this.amount && o.date.equals(this.date) &&
                    o.category.equals(this.category);
        }
        return false;
    }
}

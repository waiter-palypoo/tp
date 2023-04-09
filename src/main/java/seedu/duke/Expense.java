package seedu.duke;

import java.time.LocalDate;

public class Expense {
    private double amount;
    private LocalDate date;
    private String category;
    private String name;
    private String currency;
    private CurrencyLoader currencyLoader = CurrencyLoader.getCurrencyLoader();

    public Expense(String name, double amount, LocalDate date, String category, String currency) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.name = name;
        this.currency = currency;
    }

    public Expense(String name, double amount, LocalDate date, String category) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.name = name;
        this.currency = "SGD";
    }

    public void setName(String name) {
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return this.currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String toString() {
        return String.format("Spent %.2f %s on %s in the %s category on %s",
                this.amount * currencyLoader.getRate(this.currency), this.currency, this.name,
                this.category, this.date);
    }

    public String serialize() {
        return String.format("%s|%s|%s|%s|%s", this.getClass().getSimpleName(), this.getName(), this.getAmount(),
                this.getCategory(), this.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Expense) {
            Expense o = (Expense) obj;
            return o.name.equals(this.name) && o.amount == this.amount && o.date.equals(this.date) &&
                    o.category.equals(this.category);
        }
        return false;
    }
}

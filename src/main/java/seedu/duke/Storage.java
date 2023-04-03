package seedu.duke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {

    public static final String DATA_DIR_NAME = "data";
    public static final String DATA_FILE_NAME = "duke_data.txt";
    private Path dataFilePath;

    /**
     * Constructor for the `Storage` class, which initializes storage by first checking if the data file already
     * exists at `${CWD}/data/duke_data.txt`, else it will create the file
     */
    public Storage() {
        Path dataDirPath = Path.of("").resolve(DATA_DIR_NAME).toAbsolutePath();
        try {
            if (!Files.exists(dataDirPath)) {
                Files.createDirectory(dataDirPath);
            }
            this.dataFilePath = dataDirPath.resolve(DATA_FILE_NAME);
            if (!Files.exists(this.dataFilePath)) {
                Files.createFile(this.dataFilePath);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Parses an expense from the data file
     *
     * @param line The line containing information about an expense
     * @param expenses The list of expenses to add an expense to
     * @param futureExpenses The list of futureExpenses to add a futureExpense to
     */
    private void parseExpenses(String line, ArrayList<Expense> expenses, ArrayList<FutureExpense> futureExpenses) {
        String[] tokens = line.split("\\|");
        String type = tokens[0];
        String name = tokens[1];
        Double amount = Double.parseDouble(tokens[2]);
        String category = tokens[3];
        LocalDate date = LocalDate.parse(tokens[4]);
        if (type.equals("Expense")) {
            expenses.add(new Expense(name, amount, date, category));
        } else {
            LocalDate dueDate = LocalDate.parse(tokens[5]);
            futureExpenses.add(new FutureExpense(name, amount, dueDate, category));
        }
    }

    /**
     * Gets a map of budget spent on expenses by category
     *
     * @param expenses The list of expenses
     * @return The map of expenses by category
     */
    private HashMap<String, Double> getExpensesByCategory(ArrayList<Expense> expenses) {
        HashMap<String, Double> expenseByCategory = new HashMap<String, Double>() {
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
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            Double amount = expense.getAmount();
            expenseByCategory.put(category, expenseByCategory.get(category.strip()) + amount);
        }
        return expenseByCategory;
    }

    /**
     * Loads an `ExpenseManager` instance from storage
     *
     * @return An `ExpenseManager` instance with all the information from local storage
     */
    public ExpenseManager loadDataExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<FutureExpense> futureExpenses = new ArrayList<>();
        String data;
        double balance = 0.0;
        String currency = "SGD";
        try {
            data = Files.readString(this.dataFilePath);
            if (data.isBlank()) {
                return new ExpenseManager(balance, expenses, futureExpenses, currency);
            }
            for (String line : data.lines().toArray(String[] ::new)) {
                if (line.startsWith("currency")) {
                    currency = line.split(":")[1];
                    continue;
                }
                if (line.startsWith("balance")) {
                    balance = Double.parseDouble(line.split(":")[1]);
                    continue;
                }
                parseExpenses(line, expenses, futureExpenses);
            }
            HashMap<String, Double> expenseByCategory = getExpensesByCategory(expenses);
            ExpenseManager manager = new ExpenseManager(balance, expenses, futureExpenses, expenseByCategory, currency);
            manager.setCurrency(currency);
            return manager;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExpenseManager(balance, expenses, futureExpenses, "SGD");
    }

    /**
     * Writes all the information contained in an ExpenseManager into the data file
     *
     * @param manager The expense manager instance
     */
    public void saveExpenses(ExpenseManager manager) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("currency:%s\n", manager.getCurrency()));
        sb.append(String.format("balance:%f\n", manager.getTotalBalance()));
        for (Expense expense : manager.getExpenses()) {
            sb.append(expense.serialize() + "\n");
        }
        for (FutureExpense expense : manager.getFutureExpenses()) {
            sb.append(expense.serialize() + "\n");
        }
        try {
            Files.writeString(this.dataFilePath, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

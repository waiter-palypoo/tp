package seedu.duke;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

public class Storage {

    public static final String DATA_DIR_NAME = "data";
    public static final String DATA_FILE_NAME = "duke_data.txt";
    private Path dataFilePath;

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

    public ExpenseManager loadDataExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<FutureExpense> futureExpenses = new ArrayList<>();
        String data;
        Double balance = 0.0;
        try {
            data = Files.readString(this.dataFilePath);
            if (data.isBlank()) {
                return new ExpenseManager(balance, expenses, futureExpenses);
            }
            for (String line : data.lines().toArray(String[] ::new)) {
                if (line.startsWith("balance")) {
                    balance = Double.parseDouble(line.split(":")[1]);
                    continue;
                }
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
            return new ExpenseManager(balance, expenses, futureExpenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExpenseManager(balance, expenses, futureExpenses);
    }

    public void saveExpenses(ExpenseManager manager) {
        StringBuilder sb = new StringBuilder();
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

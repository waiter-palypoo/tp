package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH_1 = "expenses.txt";
    private static final String FILE_PATH_2 = "future_expenses.txt";
    private ArrayList<Expense> expenses;
    private ArrayList<FutureExpense> futureExpenses;

    public Storage(ArrayList<Expense> expenses, ArrayList<FutureExpense> futureExpenses) {
        this.expenses = expenses;
        this.futureExpenses = futureExpenses;
    }
    public static void loadDataExpenses(ArrayList<Expense> expenses) {
        Ui ui = new Ui();
        try {
            File file = new File(FILE_PATH_1);
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                loadExpenses(in.nextLine(), expenses);
            }
        } catch (FileNotFoundException e) {
            File file = new File(FILE_PATH_1);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ui.printHorizontalLine();
                System.out.println("New file cannot be created");
                ui.printHorizontalLine();
            }
        }
    }
    public static void loadDataFutureExpenses(ArrayList<FutureExpense> futureExpenses) {
        Ui ui = new Ui();
        try {
            File file = new File(FILE_PATH_2);
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                try {
                    loadFutureExpenses(in.nextLine(), futureExpenses);
                }
                catch(DateTimeParseException e)
                {
                    System.out.println("Error");
                }
            }
        } catch (FileNotFoundException e) {
            File file = new File(FILE_PATH_2);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ui.printHorizontalLine();
                System.out.println("New file cannot be created");
                ui.printHorizontalLine();
            }
        }
    }
    public static void loadExpenses(String input, ArrayList<Expense> expenses) {
        String[] splitInput = input.split("\\|");
        double amount = Double.parseDouble(splitInput[0].trim());
        String name = splitInput[1].trim();
        String category = splitInput[2].trim();
        LocalDate date = LocalDate.parse(splitInput[3].trim());
        expenses.add(new Expense(name, amount, date, category));
    }
    public static void loadFutureExpenses(String input, ArrayList<FutureExpense> futureExpenses) {
        String[] splitInput = input.split("\\|");
        double amount = Double.parseDouble(splitInput[0].trim());
        String name = splitInput[1].trim();
        String category = splitInput[2].trim();
        LocalDate dueDate = LocalDate.parse(splitInput[3].trim());
        futureExpenses.add(new FutureExpense(name, amount, dueDate, category));
    }
    public static void saveExpenses(ArrayList <Expense> expenses) {
        Ui ui = new Ui();
        try {
            FileWriter fw = new FileWriter(FILE_PATH_1);
            for (Expense expense : expenses) {
                String lineToAdd = "";
                lineToAdd =  expense.getAmount() + " | " +  expense.getName() + " | "
                        + expense.getCategory() + " | " + expense.getDate() + "\n";
                fw.write(lineToAdd);
            }
            fw.close();
        } catch (IOException e) {
            ui.printHorizontalLine();
            System.out.println("Unable to save data");
            ui.printHorizontalLine();
        }
    }
    public static void saveFutureExpenses(ArrayList<FutureExpense> futureExpenses) {
        Ui ui = new Ui();
        try {
            FileWriter fw = new FileWriter(FILE_PATH_2);
            for (FutureExpense futureExpense : futureExpenses) {
                String lineToAdd = "";
                lineToAdd =  futureExpense.getAmount() + " | " +  futureExpense.getName() + " | "
                        + futureExpense.getCategory() + " | " + futureExpense.getDueDate() + "\n";
                fw.write(lineToAdd);
            }
            fw.close();
        } catch (IOException e) {
            ui.printHorizontalLine();
            System.out.println("Unable to save data");
            ui.printHorizontalLine();
        }
    }
}

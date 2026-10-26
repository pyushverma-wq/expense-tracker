import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CsvStorage {

    private static final String FOLDER = "data";
    private static final String FILE_PATH = "data/expenses.csv";
    private static final String HEADER = "id,date,category,amount,note";

    // Read all expenses from the CSV file
    public static ArrayList<Expense> load() {
        ArrayList<Expense> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        // First run: no file yet, so start with an empty list
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // skip the header line
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Expense expense = Expense.fromCsv(line);
                if (expense != null) {
                    list.add(expense);
                } else {
                    System.out.println("Skipped invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    // Write all expenses to the CSV file (overwrites the old content)
    public static void save(ArrayList<Expense> list) {
        // Create the data folder if it does not exist
        File folder = new File(FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println(HEADER);
            for (Expense expense : list) {
                writer.println(expense.toCsv());
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
